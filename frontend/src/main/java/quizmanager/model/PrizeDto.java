package quizmanager.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PrizeDto {

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("type")
    private List<PrizeTypeDto> prizeTypes;

    public PrizeDto() {}

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

    public void setPrizeTypes(List<PrizeTypeDto> prizeTypes) {
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

    @Override
    public String toString() {
        return name + " (" + description + ")";
    }
}