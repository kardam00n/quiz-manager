package model;

import model.prize.PrizeType;

public interface RewardingStrategy {
    PrizeType getsPrize(Record record);
}
