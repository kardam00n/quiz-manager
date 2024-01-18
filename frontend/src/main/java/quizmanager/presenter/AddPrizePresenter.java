package quizmanager.presenter;


import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import quizmanager.model.PrizeDto;
import quizmanager.service.QuizService;
import rx.schedulers.Schedulers;

public class AddPrizePresenter {
    @FXML
    private GridPane gridPane;
    @FXML
    private Button addButton;

    @FXML
    private TextField prizeName;

    @FXML
    private TextField prizeDescription;


    private Stage dialogStage;

    private boolean approved;

    private PrizeDto prizeDto;

    private final QuizService service;

    private int currentRow = 0;
    private int currentCol = 0;

    private final IntegerProperty selected = new SimpleIntegerProperty(0);

    public AddPrizePresenter(QuizService service) {
        this.service = service;
    }

    @FXML
    private void initialize() {

        service.getPrizeTypes()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.from(Platform::runLater))
                .subscribe(
                        next -> next.forEach(e -> {
                            CheckBox checkBox = new CheckBox(e.getName());
                            GridPane.setHalignment(checkBox, HPos.LEFT);
                            gridPane.add(checkBox, currentCol, currentRow);
                            currentCol = currentCol == 1 ? 0 : 1;
                            currentRow = currentCol == 0 ? currentRow + 1 : currentRow;

                            checkBox.setOnAction(event -> {
                                if (checkBox.isSelected()) {
                                    prizeDto.addType(checkBox.getText());
                                    selected.set(selected.getValue() + 1);
                                } else {
                                    prizeDto.removeType(checkBox.getText());
                                    selected.set(selected.getValue() - 1);
                                }
                            });

                        }),
                        error -> {


                        }

                );


        addButton.disableProperty().bind(
                Bindings.isEmpty(prizeName.textProperty())
                        .or(Bindings.isEmpty(prizeDescription.textProperty()))
                        .or(Bindings.lessThanOrEqual(selected, 0))
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
//        prizeDto.setPrizeTypes((List<PrizeTypeDto>) prizeType.getValue());
    }


    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }


    public boolean isApproved() {
        return approved;
    }

    public void setData(PrizeDto prizeDto) {
        this.prizeDto = prizeDto;
    }

}
