package quizmanager.model;

import quizmanager.model.prize.PrizeType;

public interface RewardingStrategy {
    PrizeType getsPrize(Record record);
}
