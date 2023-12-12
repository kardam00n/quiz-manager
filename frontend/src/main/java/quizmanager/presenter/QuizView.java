package quizmanager.presenter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import quizmanager.controller.QuizManagerController;
import quizmanager.model.QuizList;
import quizmanager.model.QuizListElement;
import quizmanager.util.QuizServiceCalls;

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

        // TODO nwm czy będzie to potrzebne
    }



    @FXML
    public void addQuiz(ActionEvent actionEvent) {
        var quizListElement = new QuizListElement();
        if(appController.showFormUploadDialog(quizListElement)){
            QuizServiceCalls.send(quizListElement, new QuizServiceCalls.SendCallback() {
                @Override
                public void onSuccess() {
                    System.out.println("Udało się");

                    // TODO updateControls()
                }

                @Override
                public void onError(String errorMessage) {
                    System.out.println("nie udało się");
                    // TODO display error message?
                }

                @Override
                public void onFailure(String failureMessage) {
                    System.out.println(failureMessage);
                    // TODO display error message?
                }
            });
        }
    }

    public void setAppController(QuizManagerController appController){
        this.appController = appController;
    }


}
