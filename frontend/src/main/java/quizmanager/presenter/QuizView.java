package quizmanager.presenter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import okhttp3.ResponseBody;
import quizmanager.controller.QuizManagerController;
import quizmanager.model.QuizDto;
import quizmanager.model.QuizListElement;
import quizmanager.util.QuizServiceCalls;
import retrofit2.Response;

import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class QuizView implements Initializable {

    private QuizManagerController appController;

    // Quiz table

    @FXML
    private TableView<QuizDto> quizDetailsTable;

    @FXML
    private TableColumn<QuizDto, String> petName;

    @FXML
    private TableColumn<QuizDto, Integer> correctAnswers;

    @FXML
    private TableColumn<QuizDto, Timestamp> timestamp;

    @FXML
    private TableColumn<QuizDto, String> prize; // TODO change type


    // Quiz title list

    @FXML
    private ListView<String> quizTitles;


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        petName.setCellValueFactory(new PropertyValueFactory<>("petName"));
        correctAnswers.setCellValueFactory(new PropertyValueFactory<>("correctAnswers"));
        timestamp.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
        prize.setCellValueFactory(new PropertyValueFactory<>("prize"));
        QuizServiceCalls.loadQuizTitles(new QuizServiceCalls.SendCallback<>() {
            @Override
            public void onSuccess(Response<List<String>> response) {
                quizTitles.getSelectionModel().selectedItemProperty().addListener(
                        (observable, oldValue, newValue) -> {
                            String selectedQuiz = quizTitles
                                    .getSelectionModel()
                                    .getSelectedItem();
                            getAndShowSelectedQuiz(selectedQuiz);
                        });
                if (!quizTitles.getItems().isEmpty())
                    quizTitles.getSelectionModel().select(0);

                List<String> quizList = response.body();
                assert quizList != null;
                quizTitles.getItems().addAll(quizList);

            }

            @Override
            public void onError(Response<List<String>> response) {
                System.out.println("error " + response.code());
            }

            @Override
            public void onFailure(String failureMessage) {
                System.out.println("failure: " + failureMessage);
            }
        });


    }

    private void getAndShowSelectedQuiz(String selectedQuiz) {

        QuizServiceCalls.loadQuiz(selectedQuiz, new QuizServiceCalls.SendCallback<>() {
            @Override
            public void onSuccess(Response<List<QuizDto>> response) {

                ObservableList<QuizDto> data = FXCollections.observableArrayList();

                assert response.body() != null;
                data.addAll(response.body());

                quizDetailsTable.setItems(data);

            }

            @Override
            public void onError(Response<List<QuizDto>> response) {
                System.out.println("Error: " + response.code());
            }

            @Override
            public void onFailure(String failureMessage) {
                System.out.println("Failure: " + failureMessage);
            }
        });
    }


    @FXML
    public void addQuiz(ActionEvent actionEvent) {
        var quizListElement = new QuizListElement();
        if (appController.showFormUploadDialog(quizListElement)) {
            QuizServiceCalls.uploadQuiz(quizListElement, new QuizServiceCalls.SendCallback<ResponseBody>() {
                @Override
                public void onSuccess(Response<ResponseBody> response) {
                    quizTitles.getItems().add(quizListElement.getName());
                }

                @Override
                public void onError(Response<ResponseBody> response) {
                    System.out.println("problem while adding new quiz, error code:" + response.code());
                }

                @Override
                public void onFailure(String failureMessage) {
                    System.out.println("problem while adding new quiz, failure message:\n" + failureMessage);
                }
            });
        }
    }

    public void setAppController(QuizManagerController appController) {
        this.appController = appController;
    }


}
