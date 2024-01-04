package quizmanager;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import quizmanager.controller.QuizManagerController;

import java.util.Objects;

public class QuizManager extends Application {


    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("QuizManager");
        primaryStage
                .getIcons()
                .add(new Image(Objects.requireNonNull(QuizManager.class.getResourceAsStream("/logo.png"))));
        QuizManagerController quizManagerController = new QuizManagerController(primaryStage);
        quizManagerController.initRootLayout();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.exit();
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
