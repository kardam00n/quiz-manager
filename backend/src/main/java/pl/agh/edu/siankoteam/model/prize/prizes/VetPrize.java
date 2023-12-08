package pl.agh.edu.siankoteam.model.prize.prizes;

import pl.agh.edu.siankoteam.model.prize.Prize;
import pl.agh.edu.siankoteam.model.prize.PrizeType;

public class VetPrize extends Prize {
    public VetPrize() {
        super(PrizeType.GOLDEN, "Weterynarz", "odrobienie 1 kartkówki");
    }
}
