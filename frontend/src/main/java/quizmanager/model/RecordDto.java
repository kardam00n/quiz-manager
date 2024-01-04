package quizmanager.model;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

public class RecordDto {
    @SerializedName("nickname")
    private String nickname;

    @SerializedName("score")
    private int score;

    @SerializedName("timestamp")

    // TODO change to proper Class when fixed in the backend
//    private Timestamp timestamp;
    private String timestamp;
    @SerializedName("prize")
    private String prize;


    public RecordDto(String nickname, int score, String timestamp, String prize) {
        this.nickname = nickname;
        this.score = score;
        this.timestamp = timestamp;
        this.prize = prize;
    }

    @SuppressWarnings("unused")
    public String getNickname() {
        return nickname;
    }

    @SuppressWarnings("unused")
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @SuppressWarnings("unused")
    public Integer getScore() {
        return score;
    }

    @SuppressWarnings("unused")
    public void setScore(int score) {
        this.score = score;
    }

    @SuppressWarnings("unused")
    public String getTimestamp() {
        return timestamp;
    }

    @SuppressWarnings("unused")
    public void setTimestamp(String timestamp) {
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


