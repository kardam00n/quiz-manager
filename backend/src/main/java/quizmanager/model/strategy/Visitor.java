package quizmanager.model.strategy;

import quizmanager.model.Quiz;
import quizmanager.model.prize.Prize;

public interface Visitor {
    void assignPrizesCorrectAnswers(CorrectAnswersRewardingStrategy rewardingStrategy, Quiz quiz, Prize nonePrize);
    void assignPrizesSpeed(SpeedRewardingStrategy rewardingStrategy, Quiz quiz, Prize nonePrize);
}
