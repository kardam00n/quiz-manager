package quizmanager.model;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class CorrectAnswersRewardingStrategy extends RewardingStrategyDto {
    private Map<Integer, PrizeTypeDto> prizeTypeMap = new HashMap<>();
    private int correctAnswersToPass;

    public CorrectAnswersRewardingStrategy() {
    }


    public CorrectAnswersRewardingStrategy(String name, PrizeTypeDto prizeTypeIfPassed, PrizeTypeDto prizeTypeIfFailed, Map<Integer, PrizeTypeDto> prizeTypeMap, int correctAnswersToPass) {
        super(name, prizeTypeIfPassed, prizeTypeIfFailed);
        this.prizeTypeMap = prizeTypeMap;
        this.correctAnswersToPass = correctAnswersToPass;
    }

    public Map<Integer, PrizeTypeDto> getPrizeTypeMap() {
        return prizeTypeMap;
    }


    public void setPrizeTypeMap(Map<Integer, PrizeTypeDto> prizeTypeMap) {
        this.prizeTypeMap = prizeTypeMap;
    }

    public int getCorrectAnswersToPass() {
        return correctAnswersToPass;
    }

    public void setCorrectAnswersToPass(int correctAnswersToPass) {
        this.correctAnswersToPass = correctAnswersToPass;
    }
}
