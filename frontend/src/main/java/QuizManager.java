import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class QuizManager extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("QuizManager :3");
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
