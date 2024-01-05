package quizmanager.service;

import org.springframework.stereotype.Service;
import quizmanager.repository.RecordRepository;

@Service
public class RecordService {
    RecordRepository recordRepository;

    public RecordService(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    public void updatePrizeId(Integer recordId, Integer prizeId) {
        recordRepository.updatePrizeId(recordId, prizeId);
    }
}
