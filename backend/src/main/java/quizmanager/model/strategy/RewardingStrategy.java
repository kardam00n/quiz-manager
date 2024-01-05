package quizmanager.model.strategy;

import quizmanager.model.Record;
import quizmanager.model.prize.PrizeType;

import javax.persistence.*;
import java.util.List;

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

    public RewardingStrategy(String name, PrizeType prizeTypeIfPassed, PrizeType prizeTypeIfFailed) {
        this.name = name;
        this.prizeTypeIfPassed = prizeTypeIfPassed;
        this.prizeTypeIfFailed = prizeTypeIfFailed;
    }

    public RewardingStrategy() {

    }

<<<<<<< Updated upstream
    public abstract void assignPrizes(List<Record> records);
=======
    public String getName() {
        return name;
    }

    public PrizeType getPrizeTypeIfPassed() {
        return prizeTypeIfPassed;
    }

    public PrizeType getPrizeTypeIfFailed() {
        return prizeTypeIfFailed;
    }

    public abstract void assignPrizes(List<Record> records, Prize nonePrize);
>>>>>>> Stashed changes
}
