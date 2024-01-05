package quizmanager.model;

public class StrategyDto {
    public StrategyDto() {

    }

    public StrategyDto(String n) {
        algorithmName= n;
    }

    String algorithmName;

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    @Override
    public String toString() {
        return "StrategyDto{" +
                "algorithmName='" + algorithmName + '\'' +
                '}';
    }

    public String getAlgorithmName() {
        return algorithmName;
    }
}
