package quizmanager.model;

import java.util.ArrayList;
import java.util.List;

public class QuizList {

    // TODO jak to chcemy robić?
    private final List<Quiz> quizList = new ArrayList<>();


    public void addQuiz(Quiz q) {
        quizList.add(q);
    }

}
