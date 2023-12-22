package quizmanager.service;

import org.springframework.stereotype.Service;
import quizmanager.model.Quiz;
import quizmanager.repository.QuizRepository;

import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    private final QuizRepository quizRepository;

    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public List<Quiz> getQuizzes(){
        return quizRepository.findAll();
    }

    public Quiz getQuizById(Integer id){
        return quizRepository.findById(id).orElse(null);
    }

    public void addQuiz(Quiz quiz) {
        quizRepository.save(quiz);
    }

    public List<String> getQuizzesNames() {
        return quizRepository.getQuizzesNames();
    }
    public Optional<Quiz> getQuizByName(String name) {
        return quizRepository.getQuizByName(name);
    }
}
