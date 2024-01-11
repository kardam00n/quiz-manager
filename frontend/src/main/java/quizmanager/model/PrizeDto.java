package quizmanager.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PrizeDto {
    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("type")
    private List<PrizeTypeDto> prizeTypes;

    public PrizeDto() {}

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
        return name + "(" + description + ")";
    }
}