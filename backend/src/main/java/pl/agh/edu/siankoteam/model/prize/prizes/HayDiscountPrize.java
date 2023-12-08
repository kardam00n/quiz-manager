package pl.agh.edu.siankoteam.model.prize.prizes;

import pl.agh.edu.siankoteam.model.prize.Prize;
import pl.agh.edu.siankoteam.model.prize.PrizeType;

public class HayDiscountPrize extends Prize {

    public HayDiscountPrize() {
        super(PrizeType.GOLDEN, "Rabat na sianko", "darmowa kartkówka");
    }
}
