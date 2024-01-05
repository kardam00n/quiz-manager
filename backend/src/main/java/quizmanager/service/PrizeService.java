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
    public Prize getNonePrize(){
        Prize prize = prizeRepository.getPrizeByName("None");
        return prize;
    }
    public Prize getPrizeByName(String name){
        return prizeRepository.getPrizeByName(name);
    }

    public PrizeRepository getPrizeRepository() {
        return prizeRepository;
    }

}
