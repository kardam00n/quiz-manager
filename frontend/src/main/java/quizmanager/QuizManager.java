package quizmanager;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import quizmanager.controller.QuizManagerController;
import quizmanager.service.QuizService;

import java.util.Objects;

public class QuizManager extends Application {


    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("QuizManager");
        primaryStage
                .getIcons()
                .add(new Image(Objects.requireNonNull(QuizManager.class.getResourceAsStream("/logo.png"))));

        QuizService service = new QuizService();
        QuizManagerController quizManagerController = new QuizManagerController(primaryStage, service);


        quizManagerController.initRootLayout();


        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });

        primaryStage.setMaximized(true);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
