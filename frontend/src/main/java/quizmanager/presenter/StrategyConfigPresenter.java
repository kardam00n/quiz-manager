package quizmanager.presenter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import quizmanager.model.PrizeTypeDto;
import quizmanager.model.StrategyAData;
import quizmanager.model.StrategyBData;
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


        chosenStrategy.getItems().addAll(new StrategyDto("one"), new StrategyDto("two"));
        chosenStrategy.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    StrategyDto selectedStrategy = chosenStrategy
                            .getSelectionModel()
                            .getSelectedItem();

                    if (selectedStrategy.getAlgorithmName().equals("one")) {
                        displayStrategyAControls();
                    } else if (selectedStrategy.getAlgorithmName().equals("two")) {
                        displayBStrategyControls();
                    }

                });

        chosenStrategy.getSelectionModel().select(0);

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
        StrategyDto selected  = chosenStrategy.getSelectionModel().getSelectedItem();
        if(selected.getAlgorithmName().equals("one")) {
            StrategyAData strategyA = new StrategyAData();


            var row = (HBox) optionsPane.getChildren().get(1);
            Spinner<Integer> spinner = (Spinner<Integer>) row.getChildren().get(0);
            ChoiceBox<PrizeTypeDto> choiceBox1 = (ChoiceBox<PrizeTypeDto>) row.getChildren().get(1);
            ChoiceBox<PrizeTypeDto> choiceBox2 = (ChoiceBox<PrizeTypeDto>) row.getChildren().get(2);

            strategyA.setAlgorithmName("one");
            strategyA.setTreshold(spinner.getValue());
            strategyA.setVictoryPrizeType(choiceBox1.getValue());
            strategyA.setRestPrizeType(choiceBox2.getValue());

            strategyDto = strategyA;
        }
        else if(chosenStrategy.getSelectionModel().getSelectedItem().getAlgorithmName().equals("two")) {
            StrategyBData strategyB = (StrategyBData) chosenStrategy.getSelectionModel().getSelectedItem();
        }

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


    }


    private void displayStrategyAHeader() {
        optionsPane.getChildren().remove(0, optionsPane.getChildren().size() - 1);


        HBox title = new HBox();
        title.setAlignment(Pos.CENTER);
        title.setSpacing(20.0);

        Label label = new Label("Procent najlepszych:");
        label.setAlignment(Pos.CENTER);

        Label label2 = new Label("Nagroda dla najlepszych:");
        label2.setAlignment(Pos.CENTER);

        Label label3 = new Label("Nagroda dla pozostałych:");
        label3.setAlignment(Pos.CENTER);

        title.getChildren().addAll(label, label2, label3);

        optionsPane.getChildren().add(optionsPane.getChildren().size() - 1, title);

    }

    private void displayStrategyARow() {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(20.0);

        Spinner<Integer> spinner = new Spinner<>(0, Integer.MAX_VALUE, 0);
        spinner.setMaxWidth(130.0);
        spinner.setMinWidth(130.0);
        ChoiceBox<PrizeTypeDto> victoryPrizeCategory = new ChoiceBox<>();
        victoryPrizeCategory.setMinWidth(150.0);
        victoryPrizeCategory.setMaxWidth(150.0);
        ChoiceBox<PrizeTypeDto> restPrizeCategory = new ChoiceBox<>();
        restPrizeCategory.setMinWidth(150.0);
        restPrizeCategory.setMaxWidth(150.0);

        hBox.getChildren().addAll(spinner, victoryPrizeCategory, restPrizeCategory);

        optionsPane.getChildren().add(optionsPane.getChildren().size() - 1, hBox);
    }

    private void displayStrategyAControls(){
        addRowButton.setVisible(false);
        displayStrategyAHeader();
        displayStrategyARow();
    }


    private void displayStrategyBHeader() {
        optionsPane.getChildren().remove(0, optionsPane.getChildren().size() - 1);


        HBox title = new HBox();
        title.setAlignment(Pos.CENTER);
        title.setSpacing(20.0);

        Label label = new Label("Poprawne odpowiedzi: ");
        label.setAlignment(Pos.CENTER);

        Label label2 = new Label("Kategoria nagrody: ");
        label2.setAlignment(Pos.CENTER);

        title.getChildren().addAll(label, label2);
        HBox.setHgrow(label, javafx.scene.layout.Priority.ALWAYS);

        optionsPane.getChildren().add(optionsPane.getChildren().size() - 1, title);

    }

    private void displayStrategyBRow() {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(20.0);

        Spinner<Integer> spinner = new Spinner<>(0, Integer.MAX_VALUE, 0);
        spinner.setMaxWidth(140.0);
        spinner.setMinWidth(140.0);
        ChoiceBox<PrizeTypeDto> prizeCategory = new ChoiceBox<>();
        prizeCategory.setMinWidth(120.0);
        prizeCategory.setMaxWidth(120.0);

        hBox.getChildren().addAll(spinner, prizeCategory);

        optionsPane.getChildren().add(optionsPane.getChildren().size() - 1, hBox);

    }

    private void displayBStrategyControls() {
        addRowButton.setVisible(true);
        displayStrategyBHeader();
        displayStrategyBRow();
    }

    @FXML
    private void addRow(ActionEvent actionEvent) {
        displayStrategyBRow();
    }
}
