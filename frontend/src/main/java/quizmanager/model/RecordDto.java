package quizmanager.model;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;
import java.util.List;

public class RecordDto {

    @SerializedName("id")
    private int id;

    @SerializedName("nickname")
    private String nickname;

    @SerializedName("score")
    private int score;

    @SerializedName("startTimestamp")
    private Timestamp startTimestamp;

    @SerializedName("endTimestamp")
    private Timestamp endTimestamp;

    @SerializedName("prize")
    private PrizeDto prize;

    @SerializedName("prizeList")
    private List<PrizeDto> prizeList;


    public RecordDto(int id, String nickname, int score, Timestamp startTimestamp,Timestamp endTimestamp, PrizeDto prize, List<PrizeDto> prizeList) {
        this.id = id;
        this.nickname = nickname;
        this.score = score;
        this.startTimestamp = startTimestamp;
        this.endTimestamp = endTimestamp;
        this.prize = prize;
        this.prizeList = prizeList;
    }

    @SuppressWarnings("unused")
    public int getId() {
        return id;
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
    public PrizeDto getPrize() {
        return prize;
    }

    @SuppressWarnings("unused")
    public void setPrize(PrizeDto prize) {
        this.prize = prize;
    }

    @SuppressWarnings("unused")
    public void setId(int id) {
        this.id = id;
    }
    public List<PrizeDto> getPrizeList() {
        return prizeList;
    }

    public void setPrizeList(List<PrizeDto> prizeList) {
        this.prizeList = prizeList;
    }
}


