package quizmanager.model;

import java.util.HashMap;
import java.util.Map;

public class StrategyBData extends StrategyDto{
    private Map<Integer, PrizeTypeDto> data = new HashMap<>();

    public Map<Integer, PrizeTypeDto> getData() {
        return data;
    }


}
