package quizmanager.model.prize.prizes;

import quizmanager.model.prize.Prize;
import quizmanager.model.prize.PrizeType;

public class MedicinePrize extends Prize {
    public MedicinePrize() {
        super(PrizeType.GOLDEN, "Lekarstwo", "odrobienie 2pkt lab");
    }
}
