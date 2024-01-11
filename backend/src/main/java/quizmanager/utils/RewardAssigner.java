package quizmanager.utils;


import org.springframework.stereotype.Component;
import quizmanager.model.Quiz;
import quizmanager.model.Record;
import quizmanager.model.prize.Prize;
import quizmanager.model.prize.PrizeType;
import quizmanager.model.strategy.CorrectAnswersRewardingStrategy;
import quizmanager.model.strategy.RewardingStrategy;
import quizmanager.model.strategy.SpeedRewardingStrategy;
import quizmanager.model.strategy.Visitor;
import quizmanager.service.PrizeService;
import quizmanager.service.RecordService;

import java.util.List;
import java.util.Map;

@Component
public class RewardAssigner implements Visitor {

    private final PrizeService prizeService;
    private final RecordService recordService;

    public RewardAssigner(PrizeService prizeService, RecordService recordService) {
        this.prizeService = prizeService;
        this.recordService = recordService;
    }

    public void assignPrizes(Quiz quiz) {
        RewardingStrategy rewardingStrategy = quiz.getRewardingStrategy();
        Prize nonePrize = prizeService.getNonePrize();

        rewardingStrategy.accept(this, quiz, nonePrize);
    }

    public void assignPrizesCorrectAnswers(CorrectAnswersRewardingStrategy rewardingStrategy, Quiz quiz, Prize nonePrize) {
        List<Record> records = quiz.getRecordSet();
        Map<Integer, PrizeType> prizeTypeMap = rewardingStrategy.getPrizeTypeMap();
//        int correctAnswerstoPass = rewardingStrategy.getCorrectAnswersToPass();
        for (Record record : records) {
            setPrize(record, prizeTypeMap.get(record.getScore()));
//            if(record.getScore() >= correctAnswerstoPass){
//                setPrize(record, rewardingStrategy.getPrizeTypeIfPassed());
//            }
//            else{
//                setPrize(record, rewardingStrategy.getPrizeTypeIfFailed());
//            }
            if(record.getPrize() == null){
                setPrize(record, nonePrize);
            }
        }
    }

    public void assignPrizesSpeed(SpeedRewardingStrategy rewardingStrategy, Quiz quiz, Prize nonePrize){
        List<Record> records = quiz.getRecordSet();
        float topSpeedPercentage = rewardingStrategy.getTopSpeedPercentage();
        int maxAnswers = rewardingStrategy.getMaxAnswers();

        int howManytoPass = (int) Math.floor((topSpeedPercentage * records.size()));
        int counter = 0;
        for(Record record: records){
            if(record.getScore() == maxAnswers && counter < howManytoPass){
                setPrize(record, rewardingStrategy.getPrizeTypeIfPassed());
                counter += 1;
            }
            else{
                setPrize(record, rewardingStrategy.getPrizeTypeIfFailed());
            }
            if(record.getPrize() == null){
                setPrize(record, nonePrize);
            }
        }
    }

    public void setPrize(Record record, PrizeType prizeType){
        for (Prize prize : record.getPrizeList()) {
            if (prize.isTypeOf(prizeType)){
                setPrize(record, prize);
            }
        }
    }

    public void setPrize(Record record, Prize prize){

        recordService.updatePrizeId(record.getId(), prize.getId());
        record.forcePrize(prize);
    }
}
