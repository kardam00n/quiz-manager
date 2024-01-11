package quizmanager.service;

import org.springframework.stereotype.Service;
import quizmanager.model.strategy.CorrectAnswersRewardingStrategy;
import quizmanager.model.strategy.RewardingStrategy;
import quizmanager.model.strategy.SpeedRewardingStrategy;
import quizmanager.repository.RewardingStrategyRepository;

@Service
public class RewardingStrategyService {
    private final RewardingStrategyRepository rewardingStrategyRepository;

    public RewardingStrategyService(RewardingStrategyRepository rewardingStrategyRepository) {
        this.rewardingStrategyRepository = rewardingStrategyRepository;
    }

    public RewardingStrategy getFirstRewardingStrategy(){
        return rewardingStrategyRepository.getReferenceById(1);
    }

    public CorrectAnswersRewardingStrategy getCorrectAnswersRewardingStrategy(){
        return rewardingStrategyRepository.findCorrectAnswersRewardingStrategy();
    }

    public SpeedRewardingStrategy getSpeedRewardingStrategy(){
        return rewardingStrategyRepository.findSpeedRewardingStrategy();
    }
    public void updateCorrectAnswersRewardingStrategy(RewardingStrategy strategy1){
        CorrectAnswersRewardingStrategy strategy = (CorrectAnswersRewardingStrategy) strategy1;
        rewardingStrategyRepository.updateCorrectAnswersRewardingStrategy(strategy.getPrizeTypeMap(), strategy.getCorrectAnswersToPass(), strategy.getId());
    }

    public void updateSpeedRewardingStrategy(RewardingStrategy strategy1){
        SpeedRewardingStrategy strategy = (SpeedRewardingStrategy) strategy1;
        rewardingStrategyRepository.updateSpeedRewardingStrategy(strategy.getTopSpeedPercentage(), strategy.getMaxAnswers(), strategy.getId());
   }

}
