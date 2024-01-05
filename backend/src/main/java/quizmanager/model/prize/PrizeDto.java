package quizmanager.model.prize;


import java.util.List;

public class PrizeDto {
    String name;
    String description;
    List<PrizeTypeDto> prizeTypes;

    public PrizeDto(String name, String description, List<PrizeTypeDto> prizeTypes) {
        this.name = name;
        this.description = description;
        this.prizeTypes = prizeTypes;
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
}

