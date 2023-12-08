package pl.agh.edu.siankoteam.model.prize.prizes;

import pl.agh.edu.siankoteam.model.prize.Prize;
import pl.agh.edu.siankoteam.model.prize.PrizeType;

public class MedicinePrize extends Prize {
    public MedicinePrize() {
        super(PrizeType.GOLDEN, "Lekarstwo", "odrobienie 2pkt lab");
    }
}
