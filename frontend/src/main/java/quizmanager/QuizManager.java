package quizmanager;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
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


        // TODO spring? czy wgl tutaj to dependency?

        QuizService service = new QuizService();
        QuizManagerController quizManagerController = new QuizManagerController(primaryStage, service);



        quizManagerController.initRootLayout();





        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
