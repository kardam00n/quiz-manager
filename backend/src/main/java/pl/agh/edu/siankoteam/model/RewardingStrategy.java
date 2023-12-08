package pl.agh.edu.siankoteam.model;

import pl.agh.edu.siankoteam.model.prize.PrizeType;

public interface RewardingStrategy {
    PrizeType getsPrize(Record record);
}
