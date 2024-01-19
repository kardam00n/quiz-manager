package quizmanager.controller;

import com.itextpdf.text.DocumentException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import quizmanager.model.Quiz;
import quizmanager.model.Record;
import quizmanager.model.RecordDto;
import quizmanager.model.prize.PrizeDto;
import quizmanager.model.statictics.AllQuestionsStats;
import quizmanager.service.QuizService;
import quizmanager.service.RewardingStrategyService;
import quizmanager.utils.FileManager;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "quizzes")
public class QuizController {

    private final QuizService quizService;
    private final RewardingStrategyService rewardingStrategyService;

    private final FileManager fileManager;
    public QuizController(QuizService quizService, RewardingStrategyService rewardingStrategyService, FileManager fileManager){
        this.quizService = quizService;
        this.rewardingStrategyService = rewardingStrategyService;
        this.fileManager = fileManager;
    }
    @GetMapping
    public List<Quiz> getAllQuizzes(){
        return quizService.getQuizzes();
    }

    @GetMapping("/names")
    public List<String> getAllQuizzesNames() {
        return quizService.getQuizzesNames();
    }


    @GetMapping("/{name}")
    public List<RecordDto> getQuizByName(@PathVariable("name") String name) {
        Optional<Quiz> quizOptional = quizService.getQuizByName(name);
        List<RecordDto> recordDtoList = new ArrayList<>();

        quizOptional.ifPresent(quiz -> {
            List<RecordDto> records = quiz.getRecordSet().stream()
                    .map(record -> new RecordDto(
                            record.getId(),
                            record.getNickname(),
                            record.getScore(),
                            record.getStartTimestamp(),
                            record.getEndTimestamp(),
                            new PrizeDto(record.getPrize()),
                            record.getPrizeList().stream()
                                    .map(PrizeDto::new)
                                    .toList()))
                    .toList();
            recordDtoList.addAll(records);
        });

        if (recordDtoList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return recordDtoList;
    }

@GetMapping("/{name}/export/{format}") //TODO: change format to be parameter (now path variable because of Retrofit on frontend :)) )
public void getQuizFileByName(@PathVariable("name") String name, @PathVariable("format") String format, HttpServletResponse response) {
    Quiz quiz = quizService.getQuizByName(name)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    try {
        if (format.equals("pdf")) {
            File file = fileManager.exportPdf(quiz.getRecordSet());
            Files.copy(file.toPath(), response.getOutputStream());
            response.setContentType("application/pdf");
            response.setHeader("Content-disposition", "attachment;filename=" + name + ".pdf");
            response.flushBuffer();
        } else if (format.equals("xlsx")) {
            File file = fileManager.exportXlsx(quiz.getRecordSet());
            Files.copy(file.toPath(), response.getOutputStream());
            response.setContentType("application/xlsx");
            response.setHeader("Content-disposition", "attachment;filename=" + name + ".xlsx");
            response.flushBuffer();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    } catch (DocumentException | IOException e) {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}



    // TODO dodać zapisywanie i obsługę błędów (czy mamy sprawdzanie, czy są wszystkie kolumny i czy nie ma jakichś
    //  niepotrzebnych? Dodanie quizu ma być możliwe tylko, jeśli jest on poprawny w pełni!!!!
    @PostMapping("")
    public void addQuiz(@RequestBody MultipartFile file) {
        try {
            File transferFile = File.createTempFile("received", ".xlsx");
            file.transferTo(transferFile);
            List<Object> importerResult = fileManager.importFile(transferFile);
            List<Record> records = (List<Record>) importerResult.get(0);
            AllQuestionsStats allQuestionsStats = (AllQuestionsStats) importerResult.get(1);
            Quiz quiz = new Quiz(file.getOriginalFilename(), records, rewardingStrategyService.getFirstRewardingStrategy());
            quiz.setAllQuestionsStats(allQuestionsStats);
            quizService.addQuiz(quiz);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

}
