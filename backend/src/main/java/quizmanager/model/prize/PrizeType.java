package quizmanager.model.prize;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Collection;
import java.util.List;

@Entity
public class PrizeType {
    @Id
    @GeneratedValue
    private int id;
    private String name;

    @ManyToMany(mappedBy = "types")
    private List<Prize> prizes;

    public PrizeType(String name) {
        this.name = name;
    }

    public PrizeType() {
    }

    public String getName() {
        return name;
    }


    public List<Prize> getPrizes() {
        return prizes;
    }

    public void setPrizes(List<Prize> prizes) {
        this.prizes = prizes;
    }
}
