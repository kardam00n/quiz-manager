package quizmanager.model.strategy;

import quizmanager.model.Record;
import quizmanager.model.prize.PrizeType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("SPEED")
public class SpeedRewardingStrategy extends RewardingStrategy{

    int topSpeedPercentage;
    @Override
    PrizeType getsPrize(Record record) {
        //TODO: implement
        return null;
    }
}
