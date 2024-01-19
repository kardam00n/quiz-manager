package quizmanager.presenter;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import quizmanager.model.PrizeDto;
import quizmanager.model.RecordDto;


public class ChangePrizePresenter {
    @FXML
    ChoiceBox<PrizeDto> prizeSelector;

    @FXML
    private Button changeButton;

    private Stage dialogStage;

    private boolean approved;

    private RecordDto recordDto;

    @FXML
    private void initialize() {
        changeButton.disableProperty().bind(
                Bindings.isNull(prizeSelector.valueProperty()));

    }

    @FXML
    private void handleCancelAction() {
        dialogStage.close();
    }

    @FXML
    private void handleChangeAction() {
        updateModel();
        approved = true;
        dialogStage.close();
    }

    private void updateModel() {
        recordDto.setPrize(prizeSelector.getValue());
    }


    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }


    public boolean isApproved() {
        return approved;
    }

    public void setData(RecordDto recordDto) {
        this.recordDto = recordDto;
        prizeSelector.getItems().addAll(recordDto.getPrizeList());
    }
}
