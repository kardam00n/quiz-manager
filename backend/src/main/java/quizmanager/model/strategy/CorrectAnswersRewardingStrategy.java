package quizmanager.model.strategy;

import quizmanager.model.Record;
import quizmanager.model.prize.PrizeType;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Entity
@DiscriminatorValue("CORR_ANS")
public class CorrectAnswersRewardingStrategy extends RewardingStrategy{

    @OneToMany
    @JoinTable(
            name = "prize_type_map",
            joinColumns = @JoinColumn(name = "strategy_id"),
            inverseJoinColumns = @JoinColumn(name = "prize_type_id")
    )
    @MapKeyColumn(name = "record_score")
    private Map<Integer, PrizeType> prizeTypeMap;

    public CorrectAnswersRewardingStrategy(String name, PrizeType prizeTypeIfPassed, PrizeType prizeTypeIfFailed, Map<Integer, PrizeType> prizeTypeMap) {
        super(name, prizeTypeIfPassed, prizeTypeIfFailed);
        this.prizeTypeMap = prizeTypeMap;
    }


    public Map<Integer, PrizeType> getPrizeTypeMap() {
        return prizeTypeMap;
    }
    public CorrectAnswersRewardingStrategy() {
        super();
    }


    @Override
    public void assignPrizes(List<Record> records) {
    }
}
