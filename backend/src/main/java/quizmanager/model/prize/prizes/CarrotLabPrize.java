package quizmanager.model.prize.prizes;

import quizmanager.model.prize.Prize;
import quizmanager.model.prize.PrizeType;

public class CarrotLabPrize extends Prize {

    public CarrotLabPrize() {
        super(PrizeType.GOLDEN, "Marchewka lab", "+10% do lab");
    }
}
