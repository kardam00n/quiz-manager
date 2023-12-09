package quizmanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class QuizManager extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("view.fxml")));

        Scene scene = new Scene(root, 300, 275);





        primaryStage.setTitle("siankoteam.QuizManager :3");
        primaryStage.setResizable(false);
        primaryStage
                .getIcons()
                .add(new Image(Objects.requireNonNull(QuizManager.class.getResourceAsStream("logo.png"))));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
