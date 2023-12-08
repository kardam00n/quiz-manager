package pl.agh.edu.siankoteam.model;

import pl.agh.edu.siankoteam.model.prize.Prize;

import java.time.LocalDateTime;
import java.util.List;

public class Record implements Comparable<Record> {
    private String nickname;
    private LocalDateTime timestamp;
    private int score;

    private List<Prize> prizeList;
    private Prize prize = null;

    public Record(String nickname, LocalDateTime timestamp, int score, List<Prize> prizeList) {
        this.nickname = nickname;
        this.timestamp = timestamp;
        this.score = score;
        this.prizeList = prizeList;
    }

    public Prize getPrize() {
        return prize;
    }

    public String getNickname() {
        return nickname;
    }

    public int getScore() {
        return score;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public List<Prize> getPrizeList() {
        return prizeList;
    }

    void forcePrize(Prize prize) {
        this.prize = prize;
    }

    @Override
    public int compareTo(Record record) {
        int result = Integer.compare(score, record.score);
        if (result == 0) {
            result = timestamp.compareTo(record.timestamp);
            if (result == 0) {
                result = nickname.compareTo(record.nickname);
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "Record{" +
                "nickname='" + nickname + '\'' +
                ", timestamp=" + timestamp +
                ", score=" + score +
                ", prizeList=" + prizeList +
                ", prize=" + prize +
                '}';
    }
}
