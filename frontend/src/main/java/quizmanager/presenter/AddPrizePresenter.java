package quizmanager.presenter;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import quizmanager.model.PrizeDto;
import quizmanager.model.PrizeTypeDto;

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
        prizeDto.setPrizeType(prizeType.getValue());
    }


    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }


    public boolean isApproved() {
        return approved;
    }

    public void setData(PrizeDto prizeDto, List<PrizeTypeDto> prizeTypeDtoList) {
        this.prizeDto = prizeDto;
        this.prizeType.getItems().addAll(prizeTypeDtoList);
    }

}
