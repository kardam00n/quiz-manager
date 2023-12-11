package quizmanager.presenter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.stage.FileChooser;
import quizmanager.controller.QuizManagerController;
import quizmanager.model.Quiz;
import quizmanager.model.QuizList;

import java.io.File;

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
    public void updateModel() {

    }

    public void updateQuizList(File file) {
        System.out.println(file);

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
