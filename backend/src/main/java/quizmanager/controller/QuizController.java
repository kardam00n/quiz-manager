package quizmanager.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import quizmanager.model.Quiz;
import quizmanager.model.Record;
import quizmanager.model.RecordDto;
import quizmanager.model.prize.PrizeDto;
import quizmanager.service.QuizService;
import quizmanager.service.RewardingStrategyService;
import quizmanager.utils.FileManager;

import java.io.File;
import java.io.IOException;
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

    // TODO fix this endpoint xd
//    @GetMapping("/getQuiz/{name}")
//    public List<RecordDto> getQuizByName(@PathVariable("name") String name) {
//        return new ArrayList<>();
////        return quizService.getQuizByName(name)
////                .map(quiz -> {quiz.getRecordSet().stream()
////                        .map((record) -> new RecordDto(
////                                record.getNickname(),
////                                record.getScore(),
////                                record.getTimestamp(),
////                                record.getPrize().toString())).toList()})
////                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
//    }

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



    // TODO dodać zapisywanie i obsługę błędów (czy mamy sprawdzanie, czy są wszystkie kolumny i czy nie ma jakichś
    //  niepotrzebnych? Dodanie quizu ma być możliwe tylko, jeśli jest on poprawny w pełni!!!!
    @PostMapping("")
    public void addQuiz(@RequestBody MultipartFile file) {
        System.out.println("received file with name: " + file.getOriginalFilename());
        try {
            File transferFile = File.createTempFile("received", ".xlsx");
            file.transferTo(transferFile);
            System.out.println("Im here");
            List<Record> records = fileManager.importFile(transferFile);
            Quiz quiz = new Quiz(file.getOriginalFilename(), records, rewardingStrategyService.getFirstRewardingStrategy());
            quizService.addQuiz(quiz);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

}
