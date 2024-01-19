package quizmanager;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import quizmanager.presenter.MainPresenter;
import quizmanager.service.QuizService;

import java.io.IOException;
import java.util.Objects;

public class QuizManager extends Application {


    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("QuizManager");
        primaryStage
                .getIcons()
                .add(new Image(Objects.requireNonNull(QuizManager.class.getResourceAsStream("/logo.png"))));

        QuizService service = new QuizService();


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(QuizManager.class.getResource("/view/main.fxml"));
        loader.setControllerFactory(controllerClass -> new MainPresenter(primaryStage, service));

        try {
            HBox page = loader.load();
            Scene scene = new Scene(page);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
