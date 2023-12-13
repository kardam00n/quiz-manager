package quizmanager.service;

import org.springframework.stereotype.Service;
import quizmanager.model.Quiz;
import quizmanager.repository.QuizRepository;

import java.util.List;

@Service
public class QuizService {
    QuizRepository quizRepository;

    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public List<Quiz> getQuizzes(){
        return quizRepository.findAll();
    }

    public Quiz getQuizById(Integer id){
        return quizRepository.findById(id).orElse(null);
    }
}
