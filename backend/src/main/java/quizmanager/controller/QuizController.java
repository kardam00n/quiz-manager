package quizmanager.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import quizmanager.model.Quiz;
import quizmanager.model.Record;
import quizmanager.model.strategy.CorrectAnswersRewardingStrategy;
import quizmanager.service.QuizService;
import quizmanager.utils.FileManager;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "quizzes")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }
    @GetMapping("/all")
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

    @GetMapping("/getQuiz/{name}")
    public List<RecordDto> getQuizByName(@PathVariable("name") String name) {

        Optional<Quiz> quiz = quizService.getQuizByName(name);
        quiz.ifPresent((e) -> System.out.println(e));


        return Collections.emptyList();
//        return quizService.getQuizByName(name)
//                .map(quiz -> quiz.getRecordSet().stream()
//                        .map(record -> new RecordDto(
//                                record.getNickname(),
//                                record.getScore(),
//                                record.getTimestamp(),
//                                record.getPrize().toString()))
//                        .toList())
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


    @PostMapping("/addQuiz")
    public void addQuiz(@RequestBody MultipartFile file) {
        System.out.println("received file with name: " + file.getName());
        try {
            File transferFile = new File("received.xlsx");
            file.transferTo(transferFile);
            List<Record> records = FileManager.importFile(transferFile);
            Quiz quiz = new Quiz(file.getName(), records, new CorrectAnswersRewardingStrategy()); //temporarily added preset strategy
            quizService.addQuiz(quiz);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public record RecordDto(String nickname, int score, Timestamp timestamp, String prize) {
    }
}
