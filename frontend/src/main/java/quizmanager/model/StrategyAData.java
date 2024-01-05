package quizmanager.model;

public class StrategyAData extends StrategyDto{

    private Integer treshold;

    public Integer getTreshold() {
        return treshold;
    }

    public void setTreshold(Integer treshold) {
        this.treshold = treshold;
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
