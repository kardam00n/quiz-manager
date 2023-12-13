package quizmanager.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import quizmanager.model.Quiz;
import quizmanager.service.QuizService;

import java.util.List;

@RestController
@RequestMapping(path = "quizzes")
public class QuizController {

    QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }
    @GetMapping
    public List<Quiz> getAllQuizzes(){
        return quizService.getQuizzes();
    }
}
