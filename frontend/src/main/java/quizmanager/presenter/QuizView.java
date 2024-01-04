package quizmanager.presenter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import okhttp3.ResponseBody;
import quizmanager.controller.QuizManagerController;
import quizmanager.model.RecordDto;
import quizmanager.model.QuizListElement;
import quizmanager.util.QuizServiceCalls;
import retrofit2.Response;

import java.net.URL;
import java.sql.Timestamp;
import java.util.List;
import java.util.ResourceBundle;

public class QuizView implements Initializable {

    private QuizManagerController appController;

    // Quiz table

    @FXML
    private TableView<RecordDto> quizDetailsTable;

    @FXML
    private TableColumn<RecordDto, String> nickname;

    @FXML
    private TableColumn<RecordDto, Integer> score;

    @FXML
    private TableColumn<RecordDto, Timestamp> startTimestamp;
    @FXML
    private TableColumn<RecordDto, Timestamp> endTimestamp;



    @FXML
    private TableColumn<RecordDto, String> prize; // TODO change type


    // Quiz title list

    @FXML
    private ListView<String> quizTitles;


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        nickname.setCellValueFactory(new PropertyValueFactory<>("nickname"));
        score.setCellValueFactory(new PropertyValueFactory<>("score"));
        startTimestamp.setCellValueFactory(new PropertyValueFactory<>("startTimestamp"));
        endTimestamp.setCellValueFactory(new PropertyValueFactory<>("endTimestamp"));
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
            public void onSuccess(Response<List<RecordDto>> response) {

                ObservableList<RecordDto> data = FXCollections.observableArrayList();

                assert response.body() != null;
                data.addAll(response.body());

                quizDetailsTable.setItems(data);

            }

            @Override
            public void onError(Response<List<RecordDto>> response) {
                System.out.println("Error: " + response.code());
            }

            @Override
            public void onFailure(String failureMessage) {
                System.out.println("Failure: " + failureMessage);
            }
        });
    }


    @FXML
    public void addQuiz() {
        var quizListElement = new QuizListElement();
        if (appController.showFormUploadDialog(quizListElement)) {
            QuizServiceCalls.uploadQuiz(quizListElement, new QuizServiceCalls.SendCallback<>() {
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
