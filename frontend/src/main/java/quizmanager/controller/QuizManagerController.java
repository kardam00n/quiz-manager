package quizmanager.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import quizmanager.QuizManager;

import java.io.IOException;

public class QuizManagerController {
    private Stage primaryStage;
    public QuizManagerController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }


    public void initRootLayout() {

        try {
            // load layout from FXML file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(QuizManagerController.class.getResource("/view/main_view.fxml"));

            GridPane rootLayout = (GridPane) loader.load();

            // add layout to a scene and show them all
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }


    }
}
