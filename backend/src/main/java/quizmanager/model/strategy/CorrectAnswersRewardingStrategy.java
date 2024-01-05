package quizmanager.model.strategy;

import quizmanager.model.Record;
import quizmanager.model.prize.PrizeType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.List;

@Entity
@DiscriminatorValue("CORR_ANS")
public class CorrectAnswersRewardingStrategy extends RewardingStrategy{

    private int correctAnswersToPass;

    public CorrectAnswersRewardingStrategy(String name, PrizeType prizeTypeIfPassed, PrizeType prizeTypeIfFailed, int correctAnswersToPass) {
        super(name, prizeTypeIfPassed, prizeTypeIfFailed);
        this.correctAnswersToPass = correctAnswersToPass;
    }

    public CorrectAnswersRewardingStrategy() {
        super();
    }

    public int getCorrectAnswersToPass() {
        return correctAnswersToPass;
    }

    @Override
    public void assignPrizes(List<Record> records) {
        for (Record record : records) {
            if (record.getScore() >= correctAnswersToPass) {
                record.setPrize(prizeTypeIfPassed);
            } else {
                record.setPrize(prizeTypeIfFailed);
            }
        }
    }
}
