package quizmanager.presenter;

import javafx.fxml.Initializable;
import javafx.stage.Stage;
import quizmanager.service.QuizService;

import java.net.URL;
import java.util.ResourceBundle;

public class QuizDetailsPresenter implements Initializable {

    private final Stage stage;
    private final QuizService service;

    public QuizDetailsPresenter(QuizService service, Stage stage) {
        this.service = service;
        this.stage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
