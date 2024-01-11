package quizmanager.model;

import java.util.HashMap;
import java.util.Map;

public class CorrectAnswersRewardingStrategy extends RewardingStrategyDto {
    private Map<Integer, PrizeTypeDto> data = new HashMap<>();

    public Map<Integer, PrizeTypeDto> getData() {
        return data;
    }


}
