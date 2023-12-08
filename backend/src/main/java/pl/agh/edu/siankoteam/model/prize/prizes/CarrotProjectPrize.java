package pl.agh.edu.siankoteam.model.prize.prizes;

import pl.agh.edu.siankoteam.model.prize.Prize;
import pl.agh.edu.siankoteam.model.prize.PrizeType;

public class CarrotProjectPrize extends Prize {

    public CarrotProjectPrize() {
        super(PrizeType.GOLDEN, "Marchewka proj", "+10% do projektu");
    }
}
