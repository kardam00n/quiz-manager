package quizmanager.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import quizmanager.model.prize.Prize;
import quizmanager.model.prize.PrizeType;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
@Entity
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nickname;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp startTimestamp;
    private Timestamp endTimestamp;
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

    public Record(String nickname, Timestamp startTimestamp,Timestamp endTimestamp, int score, List<Prize> prizeList) {
        this.nickname = nickname;
        this.startTimestamp = startTimestamp;
        this.endTimestamp = endTimestamp;
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

    public Timestamp getStartTimestamp() {
        System.out.println("\n\n\n\n");
        System.out.println(startTimestamp);
        return startTimestamp;
    }

    public Timestamp getEndTimestamp() {
        System.out.println("\n\n\n\n");
        System.out.println(endTimestamp);
        return endTimestamp;
    }

    public List<Prize> getPrizeList() {
        return prizeList;
    }

    void forcePrize(Prize prize) {
        this.prize = prize;
    }

    public void setPrize(PrizeType type){
        for (Prize prize : prizeList) {
            if (prize.isTypeOf(type)){
                this.prize = prize;
            }
        }
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Record{" +
                "nickname='" + nickname + '\'' +
                ", startTimestamp=" + startTimestamp +
                ", endTimestamp=" + endTimestamp +
                ", score=" + score +
                ", prizeList=" + prizeList +
                ", prize=" + prize +
                '}';
    }
}
