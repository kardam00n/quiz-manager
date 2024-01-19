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

//    @Query(value= "SELECT * FROM rewarding_strategy WHERE strat_type = 'CORR_ANS' LIMIT 1", nativeQuery = true)
//    CorrectAnswersRewardingStrategy findCorrectAnswersRewardingStrategy();

    @Modifying
    @Transactional
    @Query("update CorrectAnswersRewardingStrategy r set r.prizeTypeMap = ?1, r.correctAnswersToPass = ?2 where r.name = ?3")
    void updateCorrectAnswersRewardingStrategy(Map<Integer, PrizeType> prizeTypeMap, int correctAnswersToPass, String name);

    @Modifying
    @Transactional
    @Query("update SpeedRewardingStrategy r set r.topSpeedPercentage = ?1, r.prizeTypeIfPassed = ?2, r.prizeTypeIfFailed = ?3 where r.name = ?4")
    void updateSpeedRewardingStrategy(double topSpeedPercentage, PrizeType prizeTypeifPassed,PrizeType prizeTypeifFaied, String name);

//    @Query(value= "SELECT * FROM rewarding_strategy WHERE strat_type = 'SPEED' LIMIT 1", nativeQuery = true)
//    SpeedRewardingStrategy findSpeedRewardingStrategy();

    @Query("select r from CorrectAnswersRewardingStrategy r where r.name = 'CORR_ANS'")
    CorrectAnswersRewardingStrategy findCorrectAnswersRewardingStrategy();

    @Query("select r from SpeedRewardingStrategy r where r.name = 'SPEED'")
    SpeedRewardingStrategy findSpeedRewardingStrategy();
}
