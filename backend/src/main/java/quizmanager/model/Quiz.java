package quizmanager.model;

import quizmanager.model.prize.Prize;
import quizmanager.model.strategy.RewardingStrategy;

import javax.persistence.*;
import java.util.List;

@Entity
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "rewarding_strategy_id")
    private RewardingStrategy rewardingStrategy;
    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "quiz_id")
    private List<Record> recordSet;

    private int maxAnswers;

    public Quiz() {
    }

    public Quiz(String name, List<Record> recordSet, RewardingStrategy rewardingStrategy) {
        this.name = name;
        this.recordSet = recordSet;
        this.rewardingStrategy = rewardingStrategy;
    }

    public void assignPrizes(Prize nonePrize) {
        rewardingStrategy.assignPrizes(recordSet, nonePrize);
    }

    public List<Record> getRecordSet() {
        return recordSet;
    }

    @Override
    public String toString(){
        return name + " " + recordSet.get(0).getNickname();
    }
}
