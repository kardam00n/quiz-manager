package quizmanager.service;

import org.apache.poi.hssf.record.DVALRecord;
import org.springframework.stereotype.Service;
import quizmanager.model.prize.PrizeType;
import quizmanager.model.strategy.CorrectAnswersRewardingStrategy;
import quizmanager.model.strategy.RewardingStrategy;
import quizmanager.model.strategy.SpeedRewardingStrategy;
import quizmanager.repository.PrizeTypeRepository;
import quizmanager.repository.RewardingStrategyRepository;

import java.util.Optional;

@Service
public class RewardingStrategyService {
    private final RewardingStrategyRepository rewardingStrategyRepository;
    private final PrizeTypeRepository prizeTypeRepository;

    public RewardingStrategyService(RewardingStrategyRepository rewardingStrategyRepository, PrizeTypeRepository prizeTypeRepository) {
        this.rewardingStrategyRepository = rewardingStrategyRepository;
        this.prizeTypeRepository = prizeTypeRepository;
    }

    public RewardingStrategy getFirstRewardingStrategy(){
        return rewardingStrategyRepository.getReferenceById(1);
    }

    public CorrectAnswersRewardingStrategy getCorrectAnswersRewardingStrategy(){
        return rewardingStrategyRepository.findCorrectAnswersRewardingStrategy();
    }

    public SpeedRewardingStrategy getSpeedRewardingStrategy(){

        SpeedRewardingStrategy speedRewardingStrategy = rewardingStrategyRepository.findSpeedRewardingStrategy();
        System.out.println(speedRewardingStrategy.getName());
        return speedRewardingStrategy;
    }
    public void updateCorrectAnswersRewardingStrategy(RewardingStrategy strategy1){
        CorrectAnswersRewardingStrategy strategy = (CorrectAnswersRewardingStrategy) strategy1;
        rewardingStrategyRepository.updateCorrectAnswersRewardingStrategy(strategy.getPrizeTypeMap(), strategy.getCorrectAnswersToPass(), "CORR_ANS");
    }

    public void updateSpeedRewardingStrategy(RewardingStrategy strategy1){
        SpeedRewardingStrategy strategy = (SpeedRewardingStrategy) strategy1;
        Optional<PrizeType> prizeIfPassed = prizeTypeRepository.findByName(strategy1.getPrizeTypeIfPassed().getName());
        Optional<PrizeType> prizeIfFailed = prizeTypeRepository.findByName(strategy1.getPrizeTypeIfFailed().getName());
        rewardingStrategyRepository.updateSpeedRewardingStrategy(strategy.getTopSpeedPercentage(), prizeIfPassed.get(), prizeIfFailed.get(), "SPEED");
   }

}
