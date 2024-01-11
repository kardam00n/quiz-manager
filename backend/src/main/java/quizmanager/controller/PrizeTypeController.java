package quizmanager.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import quizmanager.model.Quiz;
import quizmanager.model.Record;
import quizmanager.model.prize.PrizeType;
import quizmanager.model.prize.PrizeTypeDto;
import quizmanager.service.PrizeService;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
    public void addPrizeType(@RequestBody PrizeTypeDto prizeTypeDto) {
            PrizeType newPrizeType = new PrizeType(prizeTypeDto.name());
            prizeService.addPrizeType(newPrizeType);
    }
}
