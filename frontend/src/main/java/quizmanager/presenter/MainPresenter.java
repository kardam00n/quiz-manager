package quizmanager.presenter;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import quizmanager.service.QuizService;

import java.io.IOException;

public class MainPresenter {

    private final Stage primaryStage;
    private final QuizService service;


    public MainPresenter(Stage primaryStage, QuizService service) {
        this.primaryStage = primaryStage;
        this.service = service;
    }

    @FXML
    private Button quizListButton;

    @FXML
    private Button prizeButton;

    @FXML
    private Button prizeTypeButton;

    @FXML
    private Label darkModeLabel;

    private Button checkedButton;

    @FXML
    private FontAwesomeIconView darkModeIcon;

    private boolean darkMode = false;


    @FXML
    private void showQuizList(ActionEvent actionEvent) {
//        if (checkedButton == quizListButton)
//            return;

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/view/quiz_list.fxml"));
        loader.setControllerFactory(controllerClass -> new QuizListPresenter(service, this));
        try {
            BorderPane quizList = loader.load();
            switchMainContentTo(quizList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        checkCurrentButton(quizListButton);
    }


    @FXML
    private void showPrizes(ActionEvent actionEvent) {
//        if (checkedButton == prizeButton)
//            return;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/view/prize.fxml"));
        loader.setControllerFactory(controllerClass -> new PrizePresenter(service, this));
        try {
            BorderPane prizes = loader.load();
            switchMainContentTo(prizes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        checkCurrentButton(prizeButton);

    }

    @FXML
    private void showPrizeCategories(ActionEvent actionEvent) {
//        if (checkedButton == prizeTypeButton)
//            return;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/view/prize_type.fxml"));
        loader.setControllerFactory(controllerClass -> new PrizeTypePresenter(service, this));
        try {
            BorderPane prizeTypes = loader.load();
            switchMainContentTo(prizeTypes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        checkCurrentButton(prizeTypeButton);

    }

    @FXML
    private void toggleDarkMode(ActionEvent actionEvent) {
        if (darkMode) {
            primaryStage.getScene().getRoot().getStylesheets().clear();
            primaryStage.getScene().getRoot().getStylesheets().add("/view/styles/main_light.css");
            darkModeLabel.setText("Tryb ciemny");
            darkModeIcon.setGlyphName("MOON_ALT");

        } else {

            primaryStage.getScene().getRoot().getStylesheets().clear();
            primaryStage.getScene().getRoot().getStylesheets().add("/view/styles/main_dark.css");
            darkModeLabel.setText("Tryb jasny");
            darkModeIcon.setGlyphName("SUN_ALT");

        }

        darkMode = !darkMode;

    }


    public void switchMainContentTo(Node quizList) {

        HBox hBox = (HBox) primaryStage.getScene().getRoot();
        StackPane stackPane = (StackPane) hBox.getChildren().get(1);

        stackPane.getChildren().clear();
        stackPane.getChildren().add(quizList);
    }


    private void checkCurrentButton(Button button) {
        checkedButton = button;
        quizListButton.getStyleClass().remove("nav_checked");
        prizeButton.getStyleClass().remove("nav_checked");
        prizeTypeButton.getStyleClass().remove("nav_checked");
        button.getStyleClass().add("nav_checked");
    }


    public Stage getStage() {
        return primaryStage;
    }

    public boolean isDarkMode() {
        return darkMode;
    }
}
