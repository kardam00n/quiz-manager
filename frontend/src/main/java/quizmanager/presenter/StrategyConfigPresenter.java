package quizmanager.presenter;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import quizmanager.model.StrategyDto;

public class StrategyConfigPresenter {


    @FXML
    private ChoiceBox<StrategyDto> chosenStrategy;

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private HBox lastRow;
    @FXML
    private VBox optionsPane;
    @FXML
    private Button addRowButton;

    @FXML
    private Button okButton;


    private Stage dialogStage;

    private boolean approved;

    private StrategyDto strategyDto;
    private String quizTitle;






    @FXML
    private void initialize() {
//        okButton.disableProperty().bind(
//                Bindings.isEmpty(treshold.textProperty())
//                        .or(Bindings.isEmpty(victoryPrizeType.textProperty()))
//                        .or(Bindings.isNull(strategy.valueProperty()))
//                        .or(Bindings.isEmpty(restPrizeType.textProperty()))
//        );

    }

    @FXML
    private void handleCancelAction() {
        dialogStage.close();
    }

    @FXML
    private void handleOkAction() {

        // TODO checking if data is well formatted?
        updateModel();
        approved = true;
        dialogStage.close();
    }

    private void updateModel() {
//        strategyDto.setName(prizeName.getText());
//        strategyDto.setDescription(prizeDescription.getText());
//        strategyDto.setPrizeType(prizeType.getValue());
    }


    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }


    public boolean isApproved() {
        return approved;
    }

    public void setData(StrategyDto strategyDto, String quizTitle) {
        this.strategyDto = strategyDto;
        this.quizTitle = quizTitle;
//        prizeTypeDtoList.subscribe(
//                next -> {
//                    this.prizeType.getItems().addAll(next);
//                    System.out.println("hi");
//                },
//
//                error -> {},
//                () -> {
//                    if (!prizeType.getItems().isEmpty()){
//                        prizeType.getSelectionModel().select(0);
//                    }
//                }
//        );


    }


//    public void updateControls(){
//        var selectedStrategy  = strategy.getSelectionModel().getSelectedItem();
//
//        // TODO algorithm name
//        if(selectedStrategy.getAlgorithmName().equals("A")){
//
//        }
//        else if (selectedStrategy.getAlgorithmName().equals("B")){
//
//        }
//
//    }
//
//    public void displayAStrategyControls(){
//        Label label1 = new Label("Próg w %");
//        GridPane.setMargin(label1, new Insets(5.0, 5.0, 5.0, 5.0));
//        TextField treshold = new TextField();
//        GridPane.setMargin(treshold, new Insets(5.0, 5.0, 5.0, 5.0));
//        root.addRow(1, label1, treshold);
//
//
//        Label label2 = new Label("Nagroda dla najlepszych");
//        GridPane.setMargin(label2, new Insets(5.0, 5.0, 5.0, 5.0));
//        TextField victoryPrizeType = new TextField();
//        GridPane.setMargin(victoryPrizeType, new Insets(5.0, 5.0, 5.0, 5.0));
//        root.addRow(2, label2, victoryPrizeType);
//
//
//        Label label3 = new Label("Nagroda dla pozostałych");
//        GridPane.setMargin(label3, new Insets(5.0, 5.0, 5.0, 5.0));
//        TextField restPrizeType = new TextField();
//        GridPane.setMargin(treshold, new Insets(5.0, 5.0, 5.0, 5.0));
//        root.addRow(3, label3, restPrizeType);
//
//
//        root.getChildren().removeIf(node -> GridPane.getRowIndex(node) == 3);
//
//
//
//
//
//
//
//
//
//
//    }

    public void displayBStrategyControls() {

    }

    @FXML
    private void addRow(ActionEvent actionEvent) {
        HBox hbox = new HBox();
        hbox.setAlignment(javafx.geometry.Pos.CENTER);

        hbox.setSpacing(20.0);


        Label label = new Label("Próg: ");
        label.setAlignment(Pos.CENTER);

        TextField doubleTextField = new TextField();
        doubleTextField.setAlignment(Pos.CENTER);


        hbox.getChildren().addAll(label, doubleTextField);
        HBox.setHgrow(label, javafx.scene.layout.Priority.ALWAYS);

        optionsPane.getChildren().add(optionsPane.getChildren().size() - 1, hbox );
    }
}
