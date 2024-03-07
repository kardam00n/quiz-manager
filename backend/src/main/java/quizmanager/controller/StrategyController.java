package quizmanager.controller;


import org.springframework.web.bind.annotation.*;
import quizmanager.model.strategy.CorrectAnswersRewardingStrategy;
import quizmanager.model.strategy.RewardingStrategy;
import quizmanager.model.strategy.SpeedRewardingStrategy;
import quizmanager.service.RewardingStrategyService;

@RestController
@RequestMapping("/strategies")
public class StrategyController {
    private final RewardingStrategyService rewardingStrategyService;

    public StrategyController(RewardingStrategyService rewardingStrategyService) {
        this.rewardingStrategyService = rewardingStrategyService;
    }

    @GetMapping("/speed")
    public SpeedRewardingStrategy getSpeedRewardingStrategy() {

        return rewardingStrategyService.getSpeedRewardingStrategy();
    }

    @GetMapping("/correct")
    public CorrectAnswersRewardingStrategy getCorrectAnswersStrategy () {

        return rewardingStrategyService.getCorrectAnswersRewardingStrategy();
    }

    @PutMapping("/speed")
    public void updateStrategyForQuiz (@RequestBody SpeedRewardingStrategy strategy) {
        rewardingStrategyService.updateSpeedRewardingStrategy(strategy);
    }

    @PutMapping("/correct")
    public void updateStrategyForQuiz (@RequestBody CorrectAnswersRewardingStrategy strategy) {
        rewardingStrategyService.updateCorrectAnswersRewardingStrategy(strategy);
    }








}
