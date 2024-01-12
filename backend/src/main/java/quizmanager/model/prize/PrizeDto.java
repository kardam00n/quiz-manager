package quizmanager.model.prize;


import java.util.List;

public class PrizeDto {

    private int id;
    private String name;


    private String description;


    private List<PrizeTypeDto> prizeTypes;

    public PrizeDto() {}

    public PrizeDto(Prize prize) {
        this.id = prize.getId();
        this.name = prize.getName();
        this.description = prize.getDescription();
        this.prizeTypes = prize.getTypes().stream()
                .map(PrizeTypeDto::new)
                .toList();
    }
    public PrizeDto(int id, String name, String description, List<PrizeTypeDto> prizeTypes) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.prizeTypes = prizeTypes;
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

    public void setPrizeType(List<PrizeTypeDto> prizeTypes) {
        this.prizeTypes = prizeTypes;
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

    public Prize toPrize() {
        return new Prize(prizeTypes.stream().map(prizeTypeDto -> new PrizeType(prizeTypeDto.getName())).toList(), name, description);
    }

    @Override
    public String toString() {
        return id + name + "(" + description + ") [" + prizeTypes + "]";
    }
}