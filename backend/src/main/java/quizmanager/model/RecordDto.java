package quizmanager.model;

import quizmanager.model.prize.PrizeDto;

import java.sql.Timestamp;
import java.util.List;

public class RecordDto {

    private int id;

    private String nickname;

    private int score;

    private Timestamp startTimestamp;

    private Timestamp endTimestamp;

    private PrizeDto prize;

    private List<PrizeDto> prizeList;


    public RecordDto(int id, String nickname, int score, Timestamp startTimestamp, Timestamp endTimestamp, PrizeDto prize, List<PrizeDto> prizeList) {
        this.id = id;
        this.nickname = nickname;
        this.score = score;
        this.startTimestamp = startTimestamp;
        this.endTimestamp = endTimestamp;
        this.prize = prize;
        this.prizeList = prizeList;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Timestamp getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(Timestamp startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public Timestamp getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(Timestamp endTimestamp) {
        this.endTimestamp = endTimestamp;
    }


    public PrizeDto getPrize() {
        return prize;
    }

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


