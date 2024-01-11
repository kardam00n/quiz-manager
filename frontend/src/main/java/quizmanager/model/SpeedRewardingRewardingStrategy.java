package quizmanager.model;

public class SpeedRewardingRewardingStrategy extends RewardingStrategyDto {

    private Integer threshold;

    public Integer getThreshold() {
        return threshold;
    }

    public void setThreshold(Integer threshold) {
        this.threshold = threshold;
    }

    public PrizeTypeDto getVictoryPrizeType() {
        return victoryPrizeType;
    }

    public void setVictoryPrizeType(PrizeTypeDto victoryPrizeType) {
        this.victoryPrizeType = victoryPrizeType;
    }

    public PrizeTypeDto getRestPrizeType() {
        return restPrizeType;
    }

    public void setRestPrizeType(PrizeTypeDto restPrizeType) {
        this.restPrizeType = restPrizeType;
    }

    PrizeTypeDto victoryPrizeType;
    PrizeTypeDto restPrizeType;
}
