package pl.agh.edu.siankoteam.model.prize.prizes;

import pl.agh.edu.siankoteam.model.prize.Prize;
import pl.agh.edu.siankoteam.model.prize.PrizeType;

public class CarrotLabPrize extends Prize {

    public CarrotLabPrize() {
        super(PrizeType.GOLDEN, "Marchewka lab", "+10% do lab");
    }
}
