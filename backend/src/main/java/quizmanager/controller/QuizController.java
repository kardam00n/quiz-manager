package quizmanager.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import quizmanager.model.Quiz;
import quizmanager.model.Record;
import quizmanager.model.strategy.CorrectAnswersRewardingStrategy;
import quizmanager.service.QuizService;
import quizmanager.utils.FileManager;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "quizzes")
public class QuizController {

    QuizService quizService;

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

    @PostMapping("/addQuiz")
    public void addQuiz(@RequestBody File file, @RequestBody String name) {
        try {
            List<Record> records = FileManager.importFile(file);
            Quiz quiz = new Quiz(name, records, new CorrectAnswersRewardingStrategy()); //temporarily added preset strategy
            quizService.addQuiz(quiz);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
