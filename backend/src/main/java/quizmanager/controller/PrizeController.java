package quizmanager.controller;

import org.springframework.web.bind.annotation.*;

import quizmanager.model.prize.PrizeDto;
import quizmanager.model.prize.PrizeType;
import quizmanager.model.prize.PrizeTypeDto;
import quizmanager.repository.PrizeTypeRepository;
import quizmanager.service.PrizeService;
import quizmanager.model.prize.Prize;
import java.util.List;

@RestController
@RequestMapping("/prizes")
public class PrizeController {
    private final PrizeService  prizeService;
    private final PrizeTypeRepository prizeTypeRepository;

    public PrizeController(PrizeService prizeService, PrizeTypeRepository repository) {
        this.prizeTypeRepository = repository;
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
        Prize newPrize = new Prize(prizeDto.getName(), prizeDto.getDescription());

        List<PrizeType> prizeTypes = prizeTypeRepository
                .findPrizeTypesByNameIsIn(
                        prizeDto
                                .getPrizeTypes()
                                .stream()
                                .map(PrizeTypeDto::getName)
                                .toList()
                );
        newPrize.setTypes(prizeTypes);
        prizeService.addPrize(newPrize);
    }
}
