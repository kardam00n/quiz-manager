package quizmanager.controller;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import quizmanager.service.RecordService;

@RestController
@RequestMapping("/records")
public class RecordController {

    private final RecordService recordService;

    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    @PutMapping()
    public void updateRecord(@RequestParam(name = "recordId") int recordId, @RequestParam(name = "prizeId") int prizeId) {
        recordService.updatePrizeId(recordId, prizeId);
    }
}
