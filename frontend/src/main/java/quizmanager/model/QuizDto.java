package quizmanager.model;

import java.time.LocalDate;

public class QuizDto {
    private String petName;
    private Integer correctAnswers;
    private LocalDate timestamp;
    private String prize;

    public QuizDto(String petName, Integer correctAnswers, LocalDate timestamp, String prize) {
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

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDate timestamp) {
        this.timestamp = timestamp;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }
}
