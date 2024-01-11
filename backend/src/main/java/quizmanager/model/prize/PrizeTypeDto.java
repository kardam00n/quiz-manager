package quizmanager.model.prize;

public class PrizeTypeDto {


    private String name;

    public PrizeTypeDto() {}
    public PrizeTypeDto(PrizeType prizeType) {
        this.name = prizeType.getName();
    }
    public PrizeTypeDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}