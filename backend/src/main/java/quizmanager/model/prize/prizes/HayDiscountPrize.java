package quizmanager.model.prize.prizes;

import quizmanager.model.prize.Prize;
import quizmanager.model.prize.PrizeType;

public class HayDiscountPrize extends Prize {

    public HayDiscountPrize() {
        super(PrizeType.GOLDEN, "Rabat na sianko", "darmowa kartkówka");
    }
}
