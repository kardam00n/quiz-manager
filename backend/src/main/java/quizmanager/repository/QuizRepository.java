package quizmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import quizmanager.model.Quiz;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer> {

    @Query("select quiz.name from Quiz quiz")
    List<String> getQuizzesNames();

    Optional<Quiz> getQuizByName(String name);
}
