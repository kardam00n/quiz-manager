package quizmanager.model.strategy;

import quizmanager.model.Record;
import quizmanager.model.prize.PrizeType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CORR_ANS")
public class CorrectAnswersRewardingStrategy extends RewardingStrategy{

    int correctAnswersToPass;
    @Override
    PrizeType getsPrize(Record record) {
        //TODO : Implement
        return null;
    }
}
