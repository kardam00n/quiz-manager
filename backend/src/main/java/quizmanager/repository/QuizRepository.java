package quizmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import quizmanager.model.Quiz;
@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer> {
}
