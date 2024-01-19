package quizmanager.presenter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import quizmanager.service.QuizService;

public class MainPresenter {

    private final Stage primaryStage;
    private final QuizService restService;

    public MainPresenter(Stage primaryStage, QuizService restService) {
        this.primaryStage = primaryStage;
        this.restService= restService;
    }


    @FXML
    private void showQuizList(ActionEvent actionEvent) {
        System.out.println("Show");

    }

    @FXML
    private void showPrizes(ActionEvent actionEvent) {
    }

    @FXML
    private void showPrizeCategories(ActionEvent actionEvent) {
    }

    @FXML
    private void toggleDarkMode(ActionEvent actionEvent) {
    }
}
