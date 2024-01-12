package quizmanager.presenter;


import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.*;
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

//    @FXML
//    private ChoiceBox<PrizeTypeDto> prizeType;

    private Stage dialogStage;

    private boolean approved;

    private PrizeDto prizeDto;

    private final QuizService service;

    private int currentRow = 0;
    private int currentCol = 0;

    private IntegerProperty selected = new SimpleIntegerProperty(0);

    public AddPrizePresenter(QuizService service) {
        this.service = service;
    }

    @FXML
    private void initialize() {

        service.getPrizeTypes()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.from(Platform::runLater))
                .subscribe(
                        next -> {
                            next.forEach(e -> {
                                CheckBox checkBox = new CheckBox(e.getName());
                                GridPane.setHalignment(checkBox, HPos.LEFT);
                                gridPane.add(checkBox, currentCol, currentRow);
                                currentCol = currentCol == 1 ? 0 : 1;
                                currentRow = currentCol == 0 ? currentRow + 1 : currentRow;

                                checkBox.setOnAction(event -> {
                                    if(checkBox.isSelected()) {
                                        prizeDto.addType(checkBox.getText());
                                        selected.set(selected.getValue() + 1);
                                    }
                                    else{
                                        prizeDto.removeType(checkBox.getText());
                                        selected.set(selected.getValue() - 1);
                                    }
                                });

                            });
                        },
                        error -> {

                            CheckBox checkBox = new CheckBox("one");

                            CheckBox checkBox2 = new CheckBox("three");
                            CheckBox checkBox3 = new CheckBox("two");
                            GridPane.setHalignment(checkBox, HPos.CENTER);
                            GridPane.setHalignment(checkBox2, HPos.CENTER);
                            GridPane.setHalignment(checkBox3, HPos.CENTER);
                            checkBox.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    if(checkBox.isSelected()) {
                                        prizeDto.addType(checkBox.getText());
                                        selected.set(selected.getValue() + 1);
                                    }
                                    else{
                                        prizeDto.removeType(checkBox.getText());
                                        selected.set(selected.getValue() - 1);
                                    }
                                }
                            });

                            checkBox3.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    if(checkBox3.isSelected()) {
                                        prizeDto.addType(checkBox3.getText());
                                        selected.set(selected.getValue() + 1);                                    }
                                    else{
                                        prizeDto.removeType(checkBox3.getText());
                                        selected.set(selected.getValue() - 1);
                                    }
                                }


                            });

                            checkBox2.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    if(checkBox2.isSelected()) {
                                        prizeDto.addType(checkBox2.getText());
                                        selected.set(selected.getValue() + 1);
                                    }
                                    else{
                                        prizeDto.removeType(checkBox2.getText());
                                        selected.set(selected.getValue() - 1);
                                    }
                                }
                            });

                            gridPane.add(checkBox, currentCol, currentRow);
                            currentCol = currentCol == 1 ? 0 : 1;
                            currentRow = currentCol == 0 ? currentRow + 1 : currentRow;

                            gridPane.add(checkBox2, currentCol, currentRow);

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
