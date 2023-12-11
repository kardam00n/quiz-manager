package quizmanager;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import quizmanager.controller.QuizManagerController;

import java.util.Objects;

public class QuizManager extends Application {


    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("QuizManager");
        primaryStage
                .getIcons()
                .add(new Image(Objects.requireNonNull(QuizManager.class.getResourceAsStream("../logo.png"))));
        QuizManagerController quizManagerController = new QuizManagerController(primaryStage);
        quizManagerController.initRootLayout();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
