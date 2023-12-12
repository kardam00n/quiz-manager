package quizmanager.presenter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import quizmanager.controller.QuizManagerController;
import quizmanager.model.Quiz;
import quizmanager.model.QuizList;

import java.time.LocalDate;

public class QuizView {

    @FXML
    private TableView<Quiz> quizDetailsTable;

    @FXML
    private TableColumn<Quiz, String> petName;

    @FXML
    private TableColumn<Quiz, Integer> correctAnswers;

    @FXML
    private TableColumn<Quiz, LocalDate> timestamp;

    @FXML
    private TableColumn<Quiz, String> prize; // TODO change type

    private QuizManagerController appController;


    @FXML
    private ScrollPane quizes;

    @FXML
    private ScrollPane records;

    private QuizList quizList;


    public void setData(QuizList ql) {
        quizList = ql;

        // TODO will be more complicated...
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
