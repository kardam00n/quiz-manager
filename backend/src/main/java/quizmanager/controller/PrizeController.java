package quizmanager.controller;

import org.springframework.web.bind.annotation.*;

import quizmanager.model.prize.PrizeDto;
import quizmanager.service.PrizeService;
import quizmanager.model.prize.Prize;
import java.util.List;

@RestController
@RequestMapping("/prizes")
public class PrizeController {
    private final PrizeService  prizeService;

    public PrizeController(PrizeService prizeService) {
        this.prizeService = prizeService;
    }

    @GetMapping
    public List<PrizeDto> getAllPrizes(){
        return prizeService.getAllPrizes()
                .stream().map(PrizeDto::new).toList();
    }

    @PostMapping
    public void addPrize(@RequestBody PrizeDto prizeDto) {
        System.out.println(prizeDto);
        Prize newPrize = prizeDto.toPrize();
        prizeService.addPrize(newPrize);
    }
}
