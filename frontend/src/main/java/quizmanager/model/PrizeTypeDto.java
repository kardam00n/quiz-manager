package quizmanager.model;

import com.google.gson.annotations.SerializedName;

public class PrizeTypeDto {

    @SerializedName("name")
    private String name;

    public PrizeTypeDto() {}
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