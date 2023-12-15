package quizmanager.model;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

public class RecordDto {
    @SerializedName("petName")
    private String petName;

    @SerializedName("correctAnswers")
    private Integer correctAnswers;

    @SerializedName("timestamp")

    private Timestamp timestamp;
    @SerializedName("prize")
    private String prize;


    public RecordDto(String petName, Integer correctAnswers, Timestamp timestamp, String prize) {
        this.petName = petName;
        this.correctAnswers = correctAnswers;
        this.timestamp = timestamp;
        this.prize = prize;
    }

    @SuppressWarnings("unused")
    public String getPetName() {
        return petName;
    }

    @SuppressWarnings("unused")
    public void setPetName(String petName) {
        this.petName = petName;
    }

    @SuppressWarnings("unused")
    public Integer getCorrectAnswers() {
        return correctAnswers;
    }

    @SuppressWarnings("unused")
    public void setCorrectAnswers(Integer correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    @SuppressWarnings("unused")
    public Timestamp getTimestamp() {
        return timestamp;
    }

    @SuppressWarnings("unused")
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @SuppressWarnings("unused")
    public String getPrize() {
        return prize;
    }

    @SuppressWarnings("unused")
    public void setPrize(String prize) {
        this.prize = prize;
    }
}


