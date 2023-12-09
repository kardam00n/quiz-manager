package quizmanager.model.prize.prizes;

import quizmanager.model.prize.Prize;
import quizmanager.model.prize.PrizeType;

public class CarrotProjectPrize extends Prize {

    public CarrotProjectPrize() {
        super(PrizeType.GOLDEN, "Marchewka proj", "+10% do projektu");
    }
}
