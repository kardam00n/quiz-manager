package quizmanager.model;

public class SpeedRewardingStrategy extends RewardingStrategyDto {

    private float topSpeedPercentage;

    private int maxAnswers;

    public SpeedRewardingStrategy() {
    }

    public SpeedRewardingStrategy(String name, PrizeTypeDto prizeTypeIfPassed, PrizeTypeDto prizeTypeIfFailed, float topSpeedPercentage, int maxAnswers) {
        super(name, prizeTypeIfPassed, prizeTypeIfFailed);
        this.topSpeedPercentage = topSpeedPercentage;
        this.maxAnswers = maxAnswers;
    }

    public float getTopSpeedPercentage() {
        return topSpeedPercentage;
    }

    public void setTopSpeedPercentage(float topSpeedPercentage) {
        this.topSpeedPercentage = topSpeedPercentage;
    }

    public int getMaxAnswers() {
        return maxAnswers;
    }

    public void setMaxAnswers(int maxAnswers) {
        this.maxAnswers = maxAnswers;
    }
}
