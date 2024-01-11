package quizmanager.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PrizeDto {
    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("types")
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

    public void setPrizeTypes(List<PrizeTypeDto> prizeTypes) {
        this.prizeTypes = prizeTypes;
    }

    @Override
    public String toString() {
        return name + " (" + description + ")";
    }
}