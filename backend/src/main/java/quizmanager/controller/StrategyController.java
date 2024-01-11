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

    @PutMapping("/{quizName}")
    public void updateStrategyForQuiz (@PathVariable("quizName") String quizName, @RequestBody RewardingStrategy strategy) {

        if(quizName.equals("CORR_ANS")){
            rewardingStrategyService.updateCorrectAnswersRewardingStrategy(strategy);
        }
        else if(quizName.equals("SPEED")){
            rewardingStrategyService.updateSpeedRewardingStrategy(strategy);
        }
        // TODO https://new-spike.net/abstract-request-body/
        //  jakby się nie dało, to 2 * Put po jednym na klasę
    }






}
