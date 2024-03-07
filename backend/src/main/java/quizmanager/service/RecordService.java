package quizmanager.service;

import org.springframework.stereotype.Service;
import quizmanager.repository.RecordRepository;
@Service
public class RecordService {
    private final RecordRepository recordRepository;

    public RecordService(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    public void updatePrizeId(int recordId, int prizeId){
        recordRepository.updatePrizeId(recordId, prizeId);
    }
}
