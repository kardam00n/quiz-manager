package quizmanager.presenter;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import quizmanager.controller.QuizManagerController;
import quizmanager.model.PrizeDto;
import quizmanager.service.QuizService;
import rx.schedulers.Schedulers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PrizePresenter implements Initializable {

    private final QuizService service;
    private final MainPresenter mainPresenter;

    public PrizePresenter(QuizService service, MainPresenter mainPresenter) {
        this.service = service;
        this.mainPresenter = mainPresenter;

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        service.getPrizes()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.from(Platform::runLater))
                .subscribe(
                        System.out::println,
                        System.out::println
                );

        PrizeDto prizeDto = new PrizeDto();
        if (showAddPrizeDialog(prizeDto)) {
            service.uploadPrize(prizeDto)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.from(Platform::runLater))
                    .subscribe(
                            next -> {

                            },
                            System.out::println
                    );


        }
    }




    private boolean showAddPrizeDialog(PrizeDto prizeDto) {
        try {
            // Load the fxml file and create a new stage for the dialog
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(QuizManagerController.class.getResource("/view/add_prize_dialog.fxml"));
            loader.setControllerFactory(controllerClass -> new AddPrizePresenter(service));

            BorderPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add new prize");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainPresenter.getStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the size of the dialog stage

            // Set the presenter for the view
            AddPrizePresenter presenter = loader.getController();
            presenter.setDialogStage(dialogStage);
            presenter.setData(prizeDto);


            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            return presenter.isApproved();

        } catch (IOException e) {
            System.out.println("Message: " + e.getMessage() + ", Cause: " + e.getCause());
            return false;
        }
    }
}
