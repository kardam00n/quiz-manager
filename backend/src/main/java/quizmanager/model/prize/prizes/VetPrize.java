package quizmanager.model.prize.prizes;

import quizmanager.model.prize.Prize;
import quizmanager.model.prize.PrizeType;

public class VetPrize extends Prize {
    public VetPrize() {
        super(PrizeType.GOLDEN, "Weterynarz", "odrobienie 1 kartkówki");
    }
}
