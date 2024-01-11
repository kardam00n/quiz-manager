package quizmanager.controller;


import org.springframework.web.bind.annotation.*;
import quizmanager.model.strategy.CorrectAnswersRewardingStrategy;
import quizmanager.model.strategy.RewardingStrategy;
import quizmanager.model.strategy.SpeedRewardingStrategy;

@RestController
@RequestMapping("/strategies")
public class StrategyController {
    @GetMapping("/speed/{quizName}")
    public SpeedRewardingStrategy getSpeedRewardingStrategy(@PathVariable("quizName") String quizName) {

        // TODO
        return null;
    }

    @GetMapping("/correct/{quizName}")
    public CorrectAnswersRewardingStrategy getCorrectAnswersStrategy (@PathVariable("quizName") String quizName) {

        // TODO
        return null;
    }

    @PutMapping("/{quizName}")
    public SpeedRewardingStrategy updateStrategyForQuiz (@PathVariable("quizName") String quizName, @RequestBody RewardingStrategy strategy) {

        // TODO https://new-spike.net/abstract-request-body/
        //  jakby się nie dało, to 2 * Put po jednym na klasę
        return null;
    }






}
