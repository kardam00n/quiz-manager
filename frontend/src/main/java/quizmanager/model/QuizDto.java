package quizmanager.model;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;
import java.time.LocalDate;

public class QuizDto {
    @SerializedName("petName")
    private String petName;

    @SerializedName("correctAnswers")
    private Integer correctAnswers;

    @SerializedName("timestamp")

    private Timestamp timestamp;
    @SerializedName("prize")
    private String prize;

    public QuizDto(String petName, Integer correctAnswers, Timestamp timestamp, String prize) {
        this.petName = petName;
        this.correctAnswers = correctAnswers;
        this.timestamp = timestamp;
        this.prize = prize;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public Integer getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(Integer correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }
}


