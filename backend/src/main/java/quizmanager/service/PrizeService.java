package quizmanager.service;

import org.springframework.stereotype.Service;
import quizmanager.model.prize.Prize;
import quizmanager.model.prize.PrizeType;
import quizmanager.model.prize.PrizeTypeDto;
import quizmanager.repository.PrizeRepository;
import quizmanager.repository.PrizeTypeRepository;

import java.util.List;
import java.util.Optional;

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

    public List<PrizeType> getAllPrizeTypes() {
        return prizeTypeRepository.findAll();
    }

    public List<Prize> getAllPrizes() {
        return prizeRepository.findAll();
    }

    public void addPrizeType(PrizeType newPrizeType) {
        prizeTypeRepository.save(newPrizeType);
    }
    public Optional<PrizeType> getPrizeTypeByName(String name){
        return prizeTypeRepository.findByName(name);
    }

    public void addPrize(Prize newPrize) {
        prizeRepository.save(newPrize);
    }
}
