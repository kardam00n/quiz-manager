package quizmanager.presenter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import quizmanager.controller.QuizManagerController;
import quizmanager.model.*;
import quizmanager.service.QuizService;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class QuizView implements Initializable {


    private Stage stage;

    public QuizView(QuizService service) {
        this.service = service;
    }


    private QuizManagerController appController;

    // Quiz table

    @FXML
    private TableView<RecordDto> quizDetailsTable;

    @FXML
    private TableColumn<RecordDto, String> nickname;

    @FXML
    private TableColumn<RecordDto, Integer> score;

    @FXML
    private TableColumn<RecordDto, Timestamp> startTimestamp;
    @FXML
    private TableColumn<RecordDto, Timestamp> endTimestamp;

    @FXML
    private TableColumn<RecordDto, String> prize; // TODO change type

    @FXML
    private Button configButton;

    @FXML
    private TableColumn<RecordDto, Boolean> prizeChangeButton;

    // Quiz title list

    @FXML
    private ListView<String> quizTitles;
    private final QuizService service;


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        nickname.setCellValueFactory(new PropertyValueFactory<>("nickname"));
        score.setCellValueFactory(new PropertyValueFactory<>("score"));
        startTimestamp.setCellValueFactory(new PropertyValueFactory<>("startTimestamp"));
        endTimestamp.setCellValueFactory(new PropertyValueFactory<>("endTimestamp"));
        prize.setCellValueFactory(new PropertyValueFactory<>("prize"));
        prizeChangeButton.setCellFactory(recordDtoBooleanTableColumn -> new ButtonCell());

        // listener working well
        quizTitles.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    String selectedQuiz = quizTitles
                            .getSelectionModel()
                            .getSelectedItem();
                    getAndShowSelectedQuiz(selectedQuiz);
                });


        service.loadQuizTitles().subscribe(
                next -> quizTitles.getItems().addAll(next),
                System.out::println,
                () -> {
                    if (!quizTitles.getItems().isEmpty()) {
                        quizTitles.getSelectionModel().select(0);
                    }
                }


        );


        configButton.disableProperty().bind(quizTitles.getSelectionModel().selectedItemProperty().isNull());



    }

    private class ButtonCell extends TableCell<RecordDto, Boolean> {
        final Button cellButton = new Button("Zmien nagrode");

        ButtonCell(){

            cellButton.setOnAction(actionEvent -> {
                RecordDto recordDto = quizDetailsTable.getItems().get(getTableRow().getIndex());
                changePrize(recordDto);
            });
        }

        //Display button if the row is not empty
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if(!empty){
                setGraphic(cellButton);
            }
        }
    }

    private void getAndShowSelectedQuiz(String selectedQuiz) {


        service.loadQuiz(selectedQuiz).subscribe(
                next -> {
                    ObservableList<RecordDto> data = FXCollections.observableArrayList();
                    data.addAll(next);
                    quizDetailsTable.setItems(data);

                },
                System.out::println);


    }


    @FXML
    public void addQuiz() {
        var quizListElement = new QuizListElement();
        if (appController.showFormUploadDialog(quizListElement)) {

            service.uploadQuiz(quizListElement).subscribe(
                    next -> quizTitles.getItems().add(quizListElement.getName()),
                    System.out::println
            );

        }
    }

    private void changePrize(RecordDto recordDto) {
        if(appController.showChangePrizeDialog(recordDto)) {
            quizDetailsTable.refresh();
            service.updateRecord(recordDto.getId(), recordDto.getPrize().getId());
        }
    }
    @FXML
    public void addPrize() {
        PrizeDto prizeDto = new PrizeDto();

        if (appController.showAddPrizeDialog(prizeDto, service.getPrizeTypes())) {
            service.uploadPrize(prizeDto).subscribe(
                    next -> {
                    },
                    System.out::println
            );
        }
    }


    @FXML
    public void addPrizeType() {
        var prizeTypeDto = new PrizeTypeDto();
        if (appController.showNewPrizeTypeDialog(prizeTypeDto)) {
            service.uploadPrizeType(prizeTypeDto).subscribe(
                    next -> {
                    },
                    System.out::println
            );
        }
    }


    public void setAppController(QuizManagerController appController) {
        this.appController = appController;
    }

    @FXML
    private void configureStrategy() {
        try {

            FXMLLoader loader = new FXMLLoader();
            BorderPane page = loadDialogView(loader);

            Stage dialogStage = createDialogStage(page);
            StrategyConfigPresenter presenter = setViewPresenter(loader, dialogStage);
            dialogStage.showAndWait();

            updateStrategyForQuiz(presenter);

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private void updateStrategyForQuiz(StrategyConfigPresenter presenter) {
        RewardingStrategyDto rewardingStrategyDto = presenter.getStrategyDto();
        if(rewardingStrategyDto instanceof SpeedRewardingStrategy strategy) {
            service.updateStrategyForQuiz(quizTitles.getSelectionModel().getSelectedItem(), strategy).subscribe(
                    System.out::println,
                    System.out::println
            );
        }
        else if (rewardingStrategyDto instanceof CorrectAnswersRewardingStrategy strategy) {
            service.updateStrategyForQuiz(quizTitles.getSelectionModel().getSelectedItem(), strategy).subscribe(
                    System.out::println,
                    System.out::println
            );

        }
    }

    private StrategyConfigPresenter setViewPresenter(FXMLLoader loader, Stage dialogStage) {
        StrategyConfigPresenter presenter = loader.getController();
        presenter.setDialogStage(dialogStage);
        presenter.setData(quizTitles.getSelectionModel().getSelectedItem());
        return presenter;
    }

    private Stage createDialogStage(BorderPane page) {
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Konfiguracja strategii [" + quizTitles.getSelectionModel().getSelectedItem() + "]" );
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(stage);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        return dialogStage;
    }

    private BorderPane loadDialogView(FXMLLoader loader) throws IOException {
        loader.setLocation(QuizManagerController.class.getResource("/view/strategy_config_dialog.fxml"));
        loader.setControllerFactory(controllerClass -> new StrategyConfigPresenter(service));
        BorderPane page = loader.load();
        return page;
    }


    public void setStage(Stage primaryStage) {
        this.stage = primaryStage;
    }




}
