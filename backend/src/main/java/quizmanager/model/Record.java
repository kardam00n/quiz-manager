package quizmanager.model;

import org.springframework.format.annotation.DateTimeFormat;
import quizmanager.model.prize.Prize;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
@Entity
public class Record {
    @Id
    @GeneratedValue
    private int id;
    @ManyToOne
    @JoinColumn(name = "quiz_id", referencedColumnName = "id")
    private Quiz quiz;
    private String nickname;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
    private int score;

    @ManyToMany
    @JoinTable(
            name = "RECORD_PRIZES",
            joinColumns = @JoinColumn(name = "RECORD_ID", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "PRIZE_ID", referencedColumnName = "id")
    )
    private List<Prize> prizeList;
    @ManyToOne
    @JoinColumn(name = "prize_id")
    private Prize prize = null;

    public Record(String nickname, LocalDateTime timestamp, int score, List<Prize> prizeList) {
        this.nickname = nickname;
        this.timestamp = timestamp;
        this.score = score;
        this.prizeList = prizeList;
    }

    public Record() {
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
