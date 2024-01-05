package quizmanager.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import quizmanager.model.PrizeDto;
import quizmanager.model.PrizeTypeDto;
import quizmanager.model.QuizListElement;
import quizmanager.model.StrategyDto;
import quizmanager.presenter.*;
import quizmanager.service.QuizService;
import rx.Observable;

import java.io.IOException;
import java.util.List;

public class QuizManagerController {
    private final Stage primaryStage;
    private final QuizService service;


    public QuizManagerController(Stage primaryStage, QuizService service) {
        this.primaryStage = primaryStage;
        this.service = service;
    }


    public void initRootLayout() {

        try {
            // load layout from FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main_view.fxml"));
//            loader.setLocation(QuizManagerController.class.getResource("/view/main_view.fxml"));

            loader.setControllerFactory(controllerClass -> new QuizView(service));


            BorderPane rootLayout = loader.load();


            // set presenter for the view
            QuizView presenter = loader.getController();
            presenter.setAppController(this);
            presenter.setStage(primaryStage);


            // add layout to a scene and show them all
            Scene scene = new Scene(rootLayout);

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
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

    public boolean showAddPrizeDialog(PrizeDto prizeDto, Observable<List<PrizeTypeDto>> prizeTypeDtos) {
        try {
            // Load the fxml file and create a new stage for the dialog
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(QuizManagerController.class.getResource("/view/add_prize_dialog.fxml"));
            BorderPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add new prize");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the size of the dialog stage

            // Set the presenter for the view
            AddPrizePresenter presenter = loader.getController();
            presenter.setDialogStage(dialogStage);
            presenter.setData(prizeDto, prizeTypeDtos);


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


//    public boolean showStrategyConfigDialog(StrategyDto strategyDto, String quizTitle) {
//        try {
//            // Load the fxml file and create a new stage for the dialog
//            FXMLLoader loader = new FXMLLoader();
//
//            loader.setLocation(QuizManagerController.class.getResource("/view/strategy_config_dialog.fxml"));
//
//
//            BorderPane page = loader.load();
//
//            // Create the dialog Stage.
//            Stage dialogStage = new Stage();
//            dialogStage.setTitle("Konfiguracja strategii [" +quizTitle + "]" );
//            dialogStage.initModality(Modality.WINDOW_MODAL);
//            dialogStage.initOwner(primaryStage);
//            Scene scene = new Scene(page);
//            dialogStage.setScene(scene);
//
//            // Set the size of the dialog stage
//
//            // Set the presenter for the view
//            StrategyConfigPresenter presenter = loader.getController();
//            presenter.setDialogStage(dialogStage);
//            presenter.setData(strategyDto, quizTitle);
//
//
//            // Show the dialog and wait until the user closes it
//            dialogStage.showAndWait();
//
//
//            return presenter.isApproved();
//
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }

}
