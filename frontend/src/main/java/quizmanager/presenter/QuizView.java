package quizmanager.presenter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.stage.FileChooser;
import quizmanager.controller.QuizManagerController;

import java.io.File;

public class QuizView {

    private QuizManagerController appController;


    @FXML
    private ScrollPane quizes;

    @FXML
    private ScrollPane records;

    public void updateModel() {

    }

    public void updateQuizList(File file) {
        System.out.println(file);

    }

    @FXML
    public void addQuiz(ActionEvent actionEvent) {
        if(appController.showFormUploadDialog()){
            // TODO add quiz! (model)
        }




    }

    public void setAppController(QuizManagerController appController){
        this.appController = appController;
    }
}
