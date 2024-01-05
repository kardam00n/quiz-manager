package quizmanager.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import quizmanager.model.PrizeTypeDto;
import quizmanager.model.QuizListElement;
import quizmanager.presenter.AddPrizeTypePresenter;
import quizmanager.presenter.FormUploadPresenter;
import quizmanager.presenter.QuizView;

import java.io.IOException;

public class QuizManagerController {
    private final Stage primaryStage;


    public QuizManagerController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }


    public void initRootLayout() {

        try {
            // load layout from FXML file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(QuizManagerController.class.getResource("/view/main_view.fxml"));
            BorderPane rootLayout = loader.load();


            // set presenter for the view
            QuizView presenter = loader.getController();
            presenter.setAppController(this);

            // add layout to a scene and show them all
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }


    }



    public boolean showFormUploadDialog(QuizListElement quizListElement) {
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

            // Set the size of the dialog stage

            // Set the presenter for the view
            FormUploadPresenter presenter = loader.getController();
            presenter.setDialogStage(dialogStage);
            presenter.setData(quizListElement);


            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            return presenter.isApproved();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showNewPrizeTypeDialog(PrizeTypeDto prizeTypeDto) {
        try {
            // Load the fxml file and create a new stage for the dialog
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(QuizManagerController.class.getResource("/view/new_prize_type_dialog.fxml"));
            BorderPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add new prize type");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the size of the dialog stage

            // Set the presenter for the view
            AddPrizeTypePresenter presenter = loader.getController();
            presenter.setDialogStage(dialogStage);
            presenter.setData(prizeTypeDto);


            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            return presenter.isApproved();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
