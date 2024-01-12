package quizmanager.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PrizeDto {

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("type")
    private List<PrizeTypeDto> prizeTypes = new ArrayList<>();

    public PrizeDto() {}

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

    public void removeType(String typeName){
        prizeTypes.removeIf(type -> typeName.equals(type.getName()));
    }

    @Override
    public String toString() {
        return name + " (" + description + ")";
    }
}