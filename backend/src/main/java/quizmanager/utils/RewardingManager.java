package quizmanager.utils;

import quizmanager.model.Quiz;
import quizmanager.service.PrizeService;

public class RewardingManager {
    final private PrizeService prizeService;

    public RewardingManager(PrizeService prizeService) {
        this.prizeService = prizeService;
    }

    public void assignPrizesForQuiz(Quiz quiz){

    }
}
