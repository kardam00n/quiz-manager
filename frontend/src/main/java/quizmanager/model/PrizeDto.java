package quizmanager.model;

import com.google.gson.annotations.SerializedName;

public class PrizeDto {
    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("type")
    private PrizeTypeDto prizeType;

    public PrizeDto() {}

    public PrizeDto(String name, String description, PrizeTypeDto prizeType) {
        this.name = name;
        this.description = description;
        this.prizeType = prizeType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrizeType(PrizeTypeDto prizeType) {
        this.prizeType = prizeType;
    }

    @Override
    public String toString() {
        return name + "(" + description + ") [" + prizeType + "]";
    }
}