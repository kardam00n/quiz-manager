package quizmanager.presenter;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import quizmanager.model.PrizeTypeDto;

public class AddPrizeTypePresenter {
    @FXML
    private Button addButton;

    @FXML
    private TextField prizeTypeName;

    private PrizeTypeDto prizeTypeDto;

    private Stage dialogStage;

    private boolean approved;

    @FXML
    private void initialize() {
        addButton.disableProperty().bind(
                Bindings.isEmpty(prizeTypeName.textProperty())
        );
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
        prizeTypeDto.setName(prizeTypeName.getText());
    }


    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }


    public boolean isApproved() {
        return approved;
    }

    public void setData(PrizeTypeDto prizeTypeDto) {
        this.prizeTypeDto = prizeTypeDto;
    }

}
