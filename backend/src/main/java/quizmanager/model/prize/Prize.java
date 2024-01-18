package quizmanager.model.prize;

import javax.persistence.*;
import java.util.List;

@Entity
public class Prize {
    @Id
    @GeneratedValue
    private int id;
    @ManyToMany
    @JoinTable(
            name = "PRIZE_PRIZETYPES",
            joinColumns = @JoinColumn(name = "PRIZE_ID", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "PRIZETYPE_ID", referencedColumnName = "id")
    )
    private List<PrizeType> types;
    private String name;
    private String description;


    public Prize() {
    }


    // TODO TEST
    public Prize(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void setTypes(List<PrizeType> types) {
        this.types = types;
    }

    public Prize(List<PrizeType> type, String name, String description) {
        this.types = type;
        this.name = name;
        this.description = description;
    }


    public List<PrizeType> getTypes() {
        return types;
    }

    public int getId() {
        return id;
    }

    public boolean isTypeOf(PrizeType type){
        return types.contains(type);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder("(" + id + name + description);
        types.forEach(sb::append);
        sb.append(")");
        return sb.toString();

    }
}
