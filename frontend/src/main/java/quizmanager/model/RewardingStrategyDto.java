package quizmanager.model;

public class RewardingStrategyDto {

    String name;
    PrizeTypeDto prizeTypeIfPassed;
    PrizeTypeDto prizeTypeIfFailed;
    public RewardingStrategyDto() {

    }

    public RewardingStrategyDto(String name) {
        this.name = name;
    }

    public RewardingStrategyDto(String name, PrizeTypeDto prizeTypeIfPassed, PrizeTypeDto prizeTypeIfFailed) {
        this.name = name;
        this.prizeTypeIfPassed = prizeTypeIfPassed;
        this.prizeTypeIfFailed = prizeTypeIfFailed;
    }

    public String getName() {
        return name;
    }

    public PrizeTypeDto getPrizeTypeIfPassed() {
        return prizeTypeIfPassed;
    }

    public PrizeTypeDto getPrizeTypeIfFailed() {
        return prizeTypeIfFailed;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrizeTypeIfPassed(PrizeTypeDto prizeTypeIfPassed) {
        this.prizeTypeIfPassed = prizeTypeIfPassed;
    }

    public void setPrizeTypeIfFailed(PrizeTypeDto prizeTypeIfFailed) {
        this.prizeTypeIfFailed = prizeTypeIfFailed;
    }

    @Override
    public String toString() {
        return "RewardingStrategyDto{" +
                "name='" + name + '\'' +
                ", prizeTypeIfPassed=" + prizeTypeIfPassed +
                ", prizeTypeIfFailed=" + prizeTypeIfFailed +
                '}';
    }
}
