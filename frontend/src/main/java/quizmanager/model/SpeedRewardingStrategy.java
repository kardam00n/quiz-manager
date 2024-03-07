package quizmanager.model;


@SuppressWarnings("unused")

public class SpeedRewardingStrategy extends RewardingStrategyDto {

    private double topSpeedPercentage;

    private int maxAnswers;

    public SpeedRewardingStrategy() {
    }

    public SpeedRewardingStrategy(String name, PrizeTypeDto prizeTypeIfPassed, PrizeTypeDto prizeTypeIfFailed, double topSpeedPercentage, int maxAnswers) {
        super(name, prizeTypeIfPassed, prizeTypeIfFailed);
        this.topSpeedPercentage = topSpeedPercentage;
        this.maxAnswers = maxAnswers;
    }

    public double getTopSpeedPercentage() {
        return topSpeedPercentage;
    }

    public void setTopSpeedPercentage(double topSpeedPercentage) {
        this.topSpeedPercentage = topSpeedPercentage;
    }

    public int getMaxAnswers() {
        return maxAnswers;
    }

    public void setMaxAnswers(int maxAnswers) {
        this.maxAnswers = maxAnswers;
    }
}
