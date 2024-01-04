package quizmanager.model;

import quizmanager.model.strategy.RewardingStrategy;

import javax.persistence.*;
import java.util.List;

@Entity
public class Quiz {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "rewarding_strategy_id")
    private RewardingStrategy rewardingStrategy;
    @OneToMany(mappedBy = "quiz")
    private List<Record> recordSet;

    public Quiz() {
    }

    public Quiz(String name, List<Record> recordSet, RewardingStrategy rewardingStrategy) {
        this.name = name;
        this.recordSet = recordSet;
        this.rewardingStrategy = rewardingStrategy;
    }

    public List<Record> getRecordSet() {
        return recordSet;
    }

    @Override
    public String toString(){
        return name + " " + recordSet.get(0).getNickname();
    }
}
