package quizmanager.model;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

public class RecordDto {
    @SerializedName("nickname")
    private String nickname;

    @SerializedName("score")
    private int score;

    @SerializedName("startTimestamp")
    private Timestamp startTimestamp;

    @SerializedName("endTimestamp")
    private Timestamp endTimestamp;

    @SerializedName("prize")
    private String prize;


    public RecordDto(String nickname, int score, Timestamp startTimestamp,Timestamp endTimestamp, String prize) {
        this.nickname = nickname;
        this.score = score;
        this.startTimestamp = startTimestamp;
        this.endTimestamp = endTimestamp;
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
    public Timestamp getStartTimestamp() {
        return startTimestamp;
    }
    @SuppressWarnings("unused")
    public void setStartTimestamp(Timestamp startTimestamp) {
        this.startTimestamp = startTimestamp;
    }
    @SuppressWarnings("unused")
    public Timestamp getEndTimestamp() {
        return endTimestamp;
    }
    @SuppressWarnings("unused")
    public void setEndTimestamp(Timestamp endTimestamp) {
        this.endTimestamp = endTimestamp;
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


