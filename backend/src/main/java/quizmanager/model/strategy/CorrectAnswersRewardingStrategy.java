package quizmanager.model.strategy;

import quizmanager.model.Quiz;
import quizmanager.model.Record;
import quizmanager.model.prize.Prize;
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
    int correctAnswersToPass;

    public CorrectAnswersRewardingStrategy(String name, PrizeType prizeTypeIfPassed, PrizeType prizeTypeIfFailed, Map<Integer, PrizeType> prizeTypeMap, int correctAnswersToPass) {
        super(name, prizeTypeIfPassed, prizeTypeIfFailed);
        this.prizeTypeMap = prizeTypeMap;
        this.correctAnswersToPass = correctAnswersToPass;
    }

    public int getCorrectAnswersToPass() {
        return correctAnswersToPass;
    }

    @Override
    public void accept(Visitor visitor, Quiz quiz, Prize nonePrize) {
        visitor.assignPrizesCorrectAnswers(this, quiz, nonePrize);
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
