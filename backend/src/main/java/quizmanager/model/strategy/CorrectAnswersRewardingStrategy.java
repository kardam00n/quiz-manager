package quizmanager.model.strategy;

import quizmanager.model.Record;
import quizmanager.model.prize.PrizeType;

public class CorrectAnswersRewardingStrategy extends RewardingStrategy{

    int correctAnswersToPass;
    @Override
    PrizeType getsPrize(Record record) {
        //TODO : Implement
        return null;
    }
}
