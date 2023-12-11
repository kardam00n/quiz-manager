package quizmanager.presenter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import quizmanager.controller.QuizManagerController;
import quizmanager.model.Quiz;
import quizmanager.model.QuizList;

public class QuizView {

    private QuizManagerController appController;


    @FXML
    private ScrollPane quizes;

    @FXML
    private ScrollPane records;

    private QuizList quizList;


    public void setData(QuizList ql) {
        quizList = ql;
    }



    @FXML
    public void addQuiz(ActionEvent actionEvent) {
        if(appController.showFormUploadDialog()){

            // TODO get parsed xlsx and initialize quiz
            var quiz = new Quiz(); // temporary

            quizList.addQuiz(quiz); // TODO - czy pamiętamy listę całą? chyba tak?
        }
    }

    public void setAppController(QuizManagerController appController){
        this.appController = appController;
    }
}
