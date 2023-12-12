package quizmanager.presenter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import quizmanager.controller.QuizManagerController;
import quizmanager.model.QuizListElement;
import quizmanager.model.QuizList;

import java.time.LocalDate;

public class QuizView {

    @FXML
    private TableView<QuizListElement> quizDetailsTable;

    @FXML
    private TableColumn<QuizListElement, String> petName;

    @FXML
    private TableColumn<QuizListElement, Integer> correctAnswers;

    @FXML
    private TableColumn<QuizListElement, LocalDate> timestamp;

    @FXML
    private TableColumn<QuizListElement, String> prize; // TODO change type

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
        var quizListElement = new QuizListElement();
        if(appController.showFormUploadDialog(quizListElement)){

            System.out.println("Przesyłam: " + quizListElement + " na serwer");


//            quizList.addQuiz(quiz); // TODO - czy pamiętamy listę całą? chyba tak?
        }
    }

    public void setAppController(QuizManagerController appController){
        this.appController = appController;
    }
}
