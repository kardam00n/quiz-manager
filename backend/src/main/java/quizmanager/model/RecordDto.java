package quizmanager.model;

import quizmanager.model.prize.PrizeDto;

import java.sql.Timestamp;
import java.util.List;

public class RecordDto {

    private String nickname;


    private int score;


    private Timestamp startTimestamp;


    private Timestamp endTimestamp;


    private PrizeDto prize;


    private List<PrizeDto> prizeList;


    public RecordDto(String nickname, int score, Timestamp startTimestamp, Timestamp endTimestamp, PrizeDto prize, List<PrizeDto> prizeList) {
        this.nickname = nickname;
        this.score = score;
        this.startTimestamp = startTimestamp;
        this.endTimestamp = endTimestamp;
        this.prize = prize;
        this.prizeList = prizeList;
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

    public List<PrizeDto> getPrizeList() {
        return prizeList;
    }

    public void setPrizeList(List<PrizeDto> prizeList) {
        this.prizeList = prizeList;
    }
}


