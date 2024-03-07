package quizmanager.model;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class PrizeDto {

    private int id;
    private String name;

    private String description;

    private final List<PrizeTypeDto> prizeTypes = new ArrayList<>();

    public PrizeDto() {
    }

    public PrizeDto(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public String getDescription() {
        return description;
    }


    public List<PrizeTypeDto> getPrizeTypes() {
        return prizeTypes;
    }

    public void addType(String typeName) {
        prizeTypes.add(new PrizeTypeDto(typeName));
    }

    public void removeType(String typeName) {
        prizeTypes.removeIf(type -> typeName.equals(type.getName()));
    }

    @Override
    public String toString() {
        return name + " (" + description + ")";
    }
}