package quizmanager.controller;

import org.springframework.web.bind.annotation.*;

import quizmanager.model.prize.PrizeType;
import quizmanager.model.prize.PrizeTypeDto;
import quizmanager.service.PrizeService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/prizeTypes")
public class PrizeTypeController {
    private final PrizeService prizeService;

    public PrizeTypeController(PrizeService prizeService) {
        this.prizeService = prizeService;
    }

    @GetMapping
    public List<PrizeTypeDto> getAllPrizeTypes(){
        return prizeService.getAllPrizeTypes().stream().map(prizeType -> new PrizeTypeDto(prizeType.getName())).toList();
    }

    @PostMapping
    public void addPrizeType(@RequestBody List<PrizeTypeDto> prizeTypeDto) {
        for (PrizeTypeDto prizeType : prizeTypeDto) {
            Optional<PrizeType> found = prizeService.getPrizeTypeByName(prizeType.getName());
            if (found.isPresent()) {
                continue;
            }
            PrizeType newPrizeType = new PrizeType(prizeType.getName());
            prizeService.addPrizeType(newPrizeType);
        }
    }
}
