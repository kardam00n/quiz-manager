package model.prize.prizes;

import model.prize.Prize;
import model.prize.PrizeType;

public class MedicinePrize extends Prize {
    public MedicinePrize() {
        super(PrizeType.GOLDEN, "Lekarstwo", "odrobienie 2pkt lab");
    }
}
