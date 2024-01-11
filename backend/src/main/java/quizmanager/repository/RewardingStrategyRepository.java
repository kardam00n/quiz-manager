package quizmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import quizmanager.model.prize.PrizeType;
import quizmanager.model.strategy.CorrectAnswersRewardingStrategy;
import quizmanager.model.strategy.RewardingStrategy;
import quizmanager.model.strategy.SpeedRewardingStrategy;

import java.util.Map;

@Repository
public interface RewardingStrategyRepository extends JpaRepository<RewardingStrategy, Integer> {

    @Override
    RewardingStrategy getReferenceById(Integer integer);

    @Query(value= "SELECT * FROM rewarding_strategy WHERE type = 'CORR_ANS' LIMIT 1", nativeQuery = true)
    CorrectAnswersRewardingStrategy findCorrectAnswersRewardingStrategy();

    @Modifying
    @Transactional
    @Query("update CorrectAnswersRewardingStrategy r set r.prizeTypeMap = ?1, r.correctAnswersToPass = ?2 where r.id = ?3")
    void updateCorrectAnswersRewardingStrategy(Map<Integer, PrizeType> prizeTypeMap, int correctAnswersToPass, int id);

    @Modifying
    @Transactional
    @Query("update SpeedRewardingStrategy r set r.topSpeedPercentage = ?1, r.maxAnswers = ?2 where r.id = ?3")
    void updateSpeedRewardingStrategy(float topSpeedPercentage, int maxAnswers, int id);

    @Query(value= "SELECT * FROM rewarding_strategy WHERE type = 'SPEED' LIMIT 1", nativeQuery = true)
    SpeedRewardingStrategy findSpeedRewardingStrategy();
}
