package quizmanager.presenter;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import quizmanager.controller.QuizManagerController;
import quizmanager.model.PrizeTypeDto;
import quizmanager.service.QuizService;
import rx.schedulers.Schedulers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PrizeTypePresenter implements Initializable {

    private final QuizService service;
    private final MainPresenter mainPresenter;


    public PrizeTypePresenter(QuizService service, MainPresenter presenter) {
        this.service = service;
        this.mainPresenter = presenter;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        service.getPrizeTypes()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.from(Platform::runLater))
                .subscribe(
                        System.out::println,
                        System.out::println
                );


        var prizeTypeDto = new ArrayList<PrizeTypeDto>();
        if (showNewPrizeTypeDialog(prizeTypeDto)) {
            service.uploadPrizeType(prizeTypeDto)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.from(Platform::runLater))
                    .subscribe(
                            next -> {

                            },
                            System.out::println
                    );
        }
    }


    public boolean showNewPrizeTypeDialog(List<PrizeTypeDto> prizeTypeDto) {
        try {


            // Load the fxml file and create a new stage for the dialog
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(QuizManagerController.class.getResource("/view/new_prize_type_dialog.fxml"));
            loader.setControllerFactory(controllerClass -> new AddPrizeTypePresenter(service));

            BorderPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add new prize type");
            dialogStage.setResizable(false);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainPresenter.getStage());
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
            System.out.println("Message: " + e.getMessage() + ", Cause: " + e.getCause());
            return false;
        }
    }
}
