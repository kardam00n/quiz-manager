package quizmanager.controller;

import org.springframework.web.bind.annotation.*;

import quizmanager.model.prize.PrizeDto;
import quizmanager.model.prize.PrizeTypeDto;
import quizmanager.service.PrizeService;
import quizmanager.model.prize.Prize;
import quizmanager.model.prize.PrizeType;

import java.util.List;

@RestController
@RequestMapping("/prizes")
public class PrizeController {
    private final PrizeService  prizeService;

    public PrizeController(PrizeService prizeService) {
        this.prizeService = prizeService;
    }

    @GetMapping("/")
    public List<PrizeDto> getAllPrizes(){
        return prizeService.getAllPrizes()
                .stream().map(prize -> new PrizeDto(prize.getName(), prize.getDescription(), prize.getTypes().stream().map(prizeType -> new PrizeTypeDto(prizeType.getName())).toList())).toList();
    }

    @PostMapping("/")
    public void addPrize(@RequestBody PrizeDto prizeDto) {
        Prize newPrize = new Prize(prizeDto.getPrizeTypes().stream().map(prizeTypeDto -> new PrizeType(prizeTypeDto.getName())).toList(), prizeDto.getName(), prizeDto.getDescription());
        prizeService.addPrize(newPrize);
    }
}
