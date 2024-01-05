package quizmanager.service;

import org.springframework.stereotype.Service;
import quizmanager.model.prize.Prize;
import quizmanager.model.prize.PrizeType;
import quizmanager.repository.PrizeRepository;
import quizmanager.repository.PrizeTypeRepository;

import java.util.List;

@Service
public class PrizeService {
    final private PrizeRepository prizeRepository;
    final private PrizeTypeRepository prizeTypeRepository;

    public PrizeService(PrizeRepository prizeRepository, PrizeTypeRepository prizeTypeRepository) {
        this.prizeRepository = prizeRepository;
        this.prizeTypeRepository = prizeTypeRepository;
    }
}
