package quizmanager.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import quizmanager.model.Quiz;
import quizmanager.model.Record;
import quizmanager.model.prize.PrizeType;
import quizmanager.model.strategy.CorrectAnswersRewardingStrategy;
import quizmanager.service.QuizService;
import quizmanager.service.RewardingStrategyService;
import quizmanager.utils.FileManager;
import quizmanager.utils.RewardingManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "quizzes")
public class QuizController {

    private final QuizService quizService;
    private final RewardingStrategyService rewardingStrategyService;
    private final RewardingManager rewardingManager;

    private final FileManager fileManager;
    public QuizController(QuizService quizService, RewardingStrategyService rewardingStrategyService, RewardingManager rewardingManager, FileManager fileManager){
        this.quizService = quizService;
        this.rewardingStrategyService = rewardingStrategyService;
        this.rewardingManager = rewardingManager;
        this.fileManager = fileManager;
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

        Optional<Quiz> quizOptional = quizService.getQuizByName(name);

        quizOptional.ifPresent((rewardingManager::assignPrizes));
        List<RecordDto> recordDtoList = new ArrayList<>();

        quizOptional.ifPresent(quiz -> {
            List<RecordDto> records = quiz.getRecordSet().stream()
                    .map(record -> new RecordDto(
                            record.getNickname(),
                            record.getScore(),
                            record.getStartTimestamp(),
                            record.getEndTimestamp(),
                            record.getPrize().toString()))
                    .collect(Collectors.toList());
            recordDtoList.addAll(records);
        });

        if (recordDtoList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return recordDtoList;
    }



    // TODO dodać zapisywanie i obsługę błędów (czy mamy sprawdzanie, czy są wszystkie kolumny i czy nie ma jakichś
    //  niepotrzebnych? Dodanie quizu ma być możliwe tylko, jeśli jest on poprawny w pełni!!!!
    @PostMapping("/addQuiz")
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


    // TODO to jest tylko do testów "na brudno!!!!"
//    @PostMapping("/addQuiz")
//    public void addQuiz(@RequestBody MultipartFile file) {
//        System.out.println("received file with name: " + file.getName());
////        try {
////            File transferFile = new File("received.xlsx");
////            file.transferTo(transferFile);
////            List<Record> records = FileManager.importFile(transferFile);
////            Quiz quiz = new Quiz(file.getName(), records, new CorrectAnswersRewardingStrategy()); //temporarily added preset strategy
////            quizService.addQuiz(quiz);
////        } catch (IOException e) {
////            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
////        }
//        try {
//            String uploadDir = ".";
//            File directory = new File(uploadDir);
//            if (!directory.exists()) {
//                directory.mkdirs();
//            }
//
//            Path filePath = Paths.get(uploadDir + File.separator + file.getOriginalFilename());
//            Files.copy(file.getInputStream(), filePath);
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public record RecordDto(String nickname, int score, Timestamp startTimestamp,Timestamp endTimestamp, String prize) {
    }
}
