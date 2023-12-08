package pl.agh.edu.siankoteam.model;

import java.util.TreeSet;

public class Quiz {
    private RewardingStrategy rewardingStrategy;
    private TreeSet<Record> recordSet;

    public Quiz(TreeSet<Record> recordSet, RewardingStrategy rewardingStrategy) {
        this.recordSet = recordSet;
        this.rewardingStrategy = rewardingStrategy;
    }

    public TreeSet<Record> getRecordSet() {
        return recordSet;
    }
}
