package quizmanager.model.prize;


import java.util.List;

public class PrizeDto {

    private String name;


    private String description;


    private List<PrizeTypeDto> prizeTypes;

    public PrizeDto() {}

    public PrizeDto(Prize prize) {
        this.name = prize.getName();
        this.description = prize.getDescription();
        this.prizeTypes = prize.getTypes().stream()
                .map(PrizeTypeDto::new)
                .toList();
    }
    public PrizeDto(String name, String description, List<PrizeTypeDto> prizeTypes) {
        this.name = name;
        this.description = description;
        this.prizeTypes = prizeTypes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrizeType(List<PrizeTypeDto> prizeTypes) {
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

    @Override
    public String toString() {
        return name + "(" + description + ") [" + prizeTypes + "]";
    }
}