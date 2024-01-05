package quizmanager.model.strategy;

import quizmanager.model.Record;
import quizmanager.model.prize.PrizeType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.List;

@Entity
@DiscriminatorValue("SPEED")
public class SpeedRewardingStrategy extends RewardingStrategy{

    private float topSpeedPercentage;
    private int maxAnswers;

    public SpeedRewardingStrategy(String name, PrizeType prizeTypeIfPassed, PrizeType prizeTypeIfFailed, int topSpeedPercentage, int maxAnswers) {
        super(name, prizeTypeIfPassed, prizeTypeIfFailed);
        this.topSpeedPercentage = topSpeedPercentage;
        this.maxAnswers = maxAnswers;
    }

    public SpeedRewardingStrategy() {
        super();
    }

    public float getTopSpeedPercentage() {
        return topSpeedPercentage;
    }

    public int getMaxAnswers() {
        return maxAnswers;
    }

    //PASS HERE RECORD SORTED BY SPEED
    @Override
    public void assignPrizes(List<Record> records) {
        int howManytoPass = (int) Math.floor((topSpeedPercentage * records.size()));
        int counter = 0;
        for(Record record: records){
            if(record.getScore() == maxAnswers && counter < howManytoPass){
                record.setPrize(prizeTypeIfPassed);
                counter += 1;
            }
            else{
                record.setPrize(prizeTypeIfFailed);
            }
        }


    }
}
