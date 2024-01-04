package quizmanager.model.strategy;

import quizmanager.model.Record;
import quizmanager.model.prize.PrizeType;

import javax.persistence.*;

@Entity
@DiscriminatorColumn(name="STRAT_TYPE")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public abstract class RewardingStrategy {
    @Id
    @GeneratedValue
    private int id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "passed_id")
    PrizeType prizeTypeIfPassed;
    @ManyToOne
    @JoinColumn(name = "failed_id")
    PrizeType prizeTypeIfFailed;

    abstract PrizeType getsPrize(Record record);
}
