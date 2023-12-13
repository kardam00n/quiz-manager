package quizmanager.presenter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import okhttp3.ResponseBody;
import quizmanager.controller.QuizManagerController;
import quizmanager.model.QuizListElement;
import quizmanager.util.QuizServiceCalls;
import retrofit2.Response;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class QuizView implements Initializable {

    private QuizManagerController appController;

    // Quiz table

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


    // Quiz title list

    @FXML
    private ListView<String> quizTitles;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        QuizServiceCalls.loadQuizTitles(new QuizServiceCalls.SendCallback() {
            @Override
            public void onSuccess(Response<ResponseBody> response) {
                // TODO parse response to List<String>

            }

            @Override
            public void onError(Response<ResponseBody> response) {
                // TODO parse response to List<String>
            }

            @Override
            public void onFailure(String failureMessage) {
                // TODO display error message
                quizTitles.getItems().addAll(List.of("Quiz1", "Quiz2"));
                if (!quizTitles.getItems().isEmpty())
                    quizTitles.getSelectionModel().select(0);
            }
        });

        quizTitles.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    String currentQuiz = quizTitles
                            .getSelectionModel()
                            .getSelectedItem();

                });
    }


    @FXML
    public void addQuiz(ActionEvent actionEvent) {
        var quizListElement = new QuizListElement();
        if (appController.showFormUploadDialog(quizListElement)) {
            QuizServiceCalls.uploadQuiz(quizListElement, new QuizServiceCalls.SendCallback() {
                @Override
                public void onSuccess(Response<ResponseBody> response) {
                    System.out.println("Udało się");

                    // TODO updateControls()
                }

                @Override
                public void onError(Response<ResponseBody> response) {
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

    public void setAppController(QuizManagerController appController) {
        this.appController = appController;
    }


}
