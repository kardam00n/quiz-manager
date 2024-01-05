package quizmanager.service;

import org.springframework.stereotype.Service;
import quizmanager.model.strategy.RewardingStrategy;
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
}
