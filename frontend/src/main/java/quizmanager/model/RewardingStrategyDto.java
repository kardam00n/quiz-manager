package quizmanager.model;

public class RewardingStrategyDto {
    public RewardingStrategyDto() {

    }

    public RewardingStrategyDto(String n) {
        algorithmName= n;
    }

    String algorithmName;

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    @Override
    public String toString() {
        return "RewardingStrategyDto{" +
                "algorithmName='" + algorithmName + '\'' +
                '}';
    }

    public String getAlgorithmName() {
        return algorithmName;
    }
}
