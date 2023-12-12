package quizmanager.model;

import java.util.ArrayList;
import java.util.List;

public class QuizList {

    // TODO jak to chcemy robić?
    private final List<QuizListElement> quizListElementList = new ArrayList<>();


    public void addQuiz(QuizListElement q) {
        quizListElementList.add(q);
    }

}
