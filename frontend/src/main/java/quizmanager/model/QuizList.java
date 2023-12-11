package quizmanager.model;

import java.util.ArrayList;
import java.util.List;

public class QuizList {
    private List<Quiz> quizList = new ArrayList<>();


    public void addQuiz(Quiz q) {
        quizList.add(q);
    }

}
