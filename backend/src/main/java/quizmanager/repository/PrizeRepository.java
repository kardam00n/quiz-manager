package quizmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import quizmanager.model.prize.Prize;

import java.util.List;

public interface PrizeRepository extends JpaRepository<Prize, Integer> {

    @Query("select prize.name from Prize prize")
    List<String> getPrizesNames();
    Prize getPrizeByName(String name);
}
