package quizmanager.model.prize;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@SuppressWarnings("unused")
@Entity
public class PrizeType {
    @Id
    @GeneratedValue
    private int id;
    private String name;

    public PrizeType(String name) {
        this.name = name;
    }

    public PrizeType() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
