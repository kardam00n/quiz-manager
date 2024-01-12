package quizmanager.presenter;


import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import quizmanager.model.PrizeDto;
import quizmanager.model.PrizeTypeDto;
import rx.Observable;
import rx.schedulers.Schedulers;

import java.util.List;

public class AddPrizePresenter {
    @FXML
    private Button addButton;

    @FXML
    private TextField prizeName;

    @FXML
    private TextField prizeDescription;

    @FXML
    private ChoiceBox<PrizeTypeDto> prizeType;

    private Stage dialogStage;

    private boolean approved;

    private PrizeDto prizeDto;

    @FXML
    private void initialize() {
        addButton.disableProperty().bind(
                Bindings.isEmpty(prizeName.textProperty())
                        .or(Bindings.isEmpty(prizeDescription.textProperty()))
                        .or(Bindings.isNull(prizeType.valueProperty()))
        );

        prizeType.getSelectionModel().select(0);
    }

    @FXML
    private void handleCancelAction() {
        dialogStage.close();
    }

    @FXML
    private void handleAddAction() {
        updateModel();
        approved = true;
        dialogStage.close();
    }

    private void updateModel() {
        prizeDto.setName(prizeName.getText());
        prizeDto.setDescription(prizeDescription.getText());
        prizeDto.setPrizeTypes((List<PrizeTypeDto>) prizeType.getValue());
    }


    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }


    public boolean isApproved() {
        return approved;
    }

    public void setData(PrizeDto prizeDto, Observable<List<PrizeTypeDto>> prizeTypeDtoList) {
        this.prizeDto = prizeDto;
        prizeTypeDtoList
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.from(Platform::runLater))
                .subscribe(
                next -> {
                    this.prizeType.getItems().addAll(next);
                    System.out.println("hi");
                },

                error -> {},
                () -> {
                    if (!prizeType.getItems().isEmpty()){
                        prizeType.getSelectionModel().select(0);
                    }
                }
        );


    }

}
