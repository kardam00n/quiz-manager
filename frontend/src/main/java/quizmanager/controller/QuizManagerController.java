package quizmanager.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import quizmanager.model.QuizList;
import quizmanager.presenter.FormUploadPresenter;
import quizmanager.presenter.QuizView;

import java.io.IOException;

public class QuizManagerController {
    private final Stage primaryStage;

    private final QuizList quizList;

    public QuizManagerController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.quizList = new QuizList();
    }


    public void initRootLayout() {

        try {
            // load layout from FXML file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(QuizManagerController.class.getResource("/view/main_view.fxml"));
            GridPane rootLayout = loader.load();


            QuizView controller = loader.getController();
            controller.setAppController(this);
            controller.setData(quizList);

            // add layout to a scene and show them all
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }


    }



    public boolean showFormUploadDialog() {
        try {
            // Load the fxml file and create a new stage for the dialog
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(QuizManagerController.class.getResource("/view/form_upload_dialog.fxml"));
            BorderPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Upload Excel File");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the transaction into the presenter.
            FormUploadPresenter presenter = loader.getController();
            presenter.setDialogStage(dialogStage);


            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            return presenter.isApproved();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
