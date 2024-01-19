package quizmanager.presenter;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import quizmanager.model.CorrectAnswersRewardingStrategy;
import quizmanager.model.PrizeTypeDto;
import quizmanager.model.RewardingStrategyDto;
import quizmanager.model.SpeedRewardingStrategy;
import quizmanager.service.QuizService;
import rx.schedulers.Schedulers;

import java.util.List;

@SuppressWarnings("unused")
public class StrategyConfigPresenter {

    public StrategyConfigPresenter(QuizService service) {
        this.service = service;
    }


    @FXML
    private ChoiceBox<RewardingStrategyDto> chosenStrategy;


    @FXML
    private VBox optionsPane;
    @FXML
    private Button addRowButton;


    private Stage dialogStage;

    private boolean approved;

    private RewardingStrategyDto rewardingStrategyDto = new RewardingStrategyDto();
    private String quizTitle;
    private final QuizService service;

    private static final String SpeedStrategyName = "SPEED";
    private static final String CorrectAnswersStrategyName = "CORR_ANS";

    private List<PrizeTypeDto> availablePrizeTypes;

    @FXML
    private void initialize() {

        service.getPrizeTypes()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.from(Platform::runLater))
                .subscribe(
                        next -> availablePrizeTypes = next,
                        error -> System.out.println("sth went wrong")
                );
        chosenStrategy.getItems().addAll(new RewardingStrategyDto(SpeedStrategyName), new RewardingStrategyDto(CorrectAnswersStrategyName));
        chosenStrategy.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    RewardingStrategyDto selectedStrategy = chosenStrategy
                            .getSelectionModel()
                            .getSelectedItem();

                    if (selectedStrategy.getName().equals(SpeedStrategyName)) {
                        displayStrategyAControls();
                    } else if (selectedStrategy.getName().equals(CorrectAnswersStrategyName)) {
                        displayBStrategyControls();
                    }

                });

        chosenStrategy.getSelectionModel().select(0);

        // TODO
//        okButton.disableProperty().bind(
//                Bindings.isEmpty(threshold.textProperty())
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
        updateModel();
        approved = true;
        dialogStage.close();
    }

    private void updateModel() {
        RewardingStrategyDto selected = chosenStrategy.getSelectionModel().getSelectedItem();
        if (selected.getName().equals(SpeedStrategyName)) {
            rewardingStrategyDto = getSpeedRewardingStrategy();
        } else if (selected.getName().equals(CorrectAnswersStrategyName)) {
            CorrectAnswersRewardingStrategy strategyB = new CorrectAnswersRewardingStrategy();

            strategyB.setName(CorrectAnswersStrategyName);

            int index = 0;
            for (var node : optionsPane.getChildren()) {
                if (node instanceof HBox hBox) {
                    if (index > 0 && index < optionsPane.getChildren().size() - 1) {

                        Node first = hBox.getChildren().get(0);
                        Node second = hBox.getChildren().get(1);
                        if ((first instanceof Spinner<?> spinner) && second instanceof ChoiceBox<?> choiceBox) {
                            Integer spinnerValue = (Integer) (spinner.getValue());
                            PrizeTypeDto choiceBoxValue = (PrizeTypeDto) (choiceBox.getValue());

                            strategyB.getPrizeTypeMap().put(spinnerValue, choiceBoxValue);

                        }

                    }

                }
                index++;
            }

            rewardingStrategyDto = strategyB;
        }

    }

    private SpeedRewardingStrategy getSpeedRewardingStrategy() {
        SpeedRewardingStrategy strategyA = new SpeedRewardingStrategy();


        var row = (HBox) optionsPane.getChildren().get(1);


        Node spinnerNode = row.getChildren().get(0);
        Node choiceBox1Node = row.getChildren().get(1);
        Node choiceBox2Node = row.getChildren().get(2);

        if (
                (
                        spinnerNode instanceof Spinner<?> spinner)
                        && (choiceBox1Node instanceof ChoiceBox<?> choiceBox1)
                        && (choiceBox2Node instanceof ChoiceBox<?> choiceBox2)
        ) {
            double spinnerValue = (double) (spinner.getValue());
            PrizeTypeDto choiceBox1Value = (PrizeTypeDto) (choiceBox1.getValue());
            PrizeTypeDto choiceBox2Value = (PrizeTypeDto) (choiceBox2.getValue());


            strategyA.setName(SpeedStrategyName);
            strategyA.setTopSpeedPercentage(spinnerValue);
            strategyA.setPrizeTypeIfPassed(choiceBox1Value);
            strategyA.setPrizeTypeIfFailed(choiceBox2Value);

        }
        return strategyA;
    }


    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }


    public boolean isApproved() {
        return approved;
    }

    public void setData(String quizTitle) {
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

        Spinner<Double> spinner = new Spinner<>(0.0, 1.0, 0.5, 0.05);
        spinner.setMaxWidth(130.0);
        spinner.setMinWidth(130.0);
        ChoiceBox<PrizeTypeDto> victoryPrizeCategory = new ChoiceBox<>();
        victoryPrizeCategory.setMinWidth(150.0);
        victoryPrizeCategory.setMaxWidth(150.0);
        ChoiceBox<PrizeTypeDto> restPrizeCategory = new ChoiceBox<>();
        restPrizeCategory.setMinWidth(150.0);
        restPrizeCategory.setMaxWidth(150.0);

        hBox.getChildren().addAll(spinner, victoryPrizeCategory, restPrizeCategory);


        service.getStrategyAData(quizTitle)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.from(Platform::runLater))
                .subscribe(
                        next ->
                        {
                            spinner.getValueFactory().setValue(next.getTopSpeedPercentage());
                            victoryPrizeCategory.setValue(next.getPrizeTypeIfPassed());
                            victoryPrizeCategory.getItems().addAll(availablePrizeTypes);
                            restPrizeCategory.setValue(next.getPrizeTypeIfFailed());
                            restPrizeCategory.getItems().addAll(availablePrizeTypes);
                        },
                        error -> System.out.println("sth went wrong... (poprawimy na kolejne laby :>)")
                );

        optionsPane.getChildren().add(optionsPane.getChildren().size() - 1, hBox);
    }

    private void displayStrategyAControls() {
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

        service.getStrategyBData(quizTitle)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.from(Platform::runLater))
                .subscribe(
                        next -> next.getPrizeTypeMap().forEach(this::displayStrategyBRow),

                        error -> System.out.println("sth went wrong")
                );

    }

    private void displayStrategyBRow(Integer i, PrizeTypeDto prizeTypeDto) {
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
        if (i != null && prizeTypeDto != null) {
            spinner.getValueFactory().setValue(i);
            prizeCategory.setValue(prizeTypeDto);
            prizeCategory.getItems().addAll(availablePrizeTypes);
        }
    }

    private void displayStrategyBRow() {
        displayStrategyBRow(null, null);
    }

    private void displayBStrategyControls() {
        addRowButton.setVisible(true);
        displayStrategyBHeader();
//        displayStrategyBRow();

    }

    @FXML
    private void addRow(ActionEvent actionEvent) {
        displayStrategyBRow();
    }

    public RewardingStrategyDto getStrategyDto() {
        return rewardingStrategyDto;
    }

}
