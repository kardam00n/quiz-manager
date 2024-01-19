package quizmanager.presenter;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import okhttp3.ResponseBody;
import quizmanager.controller.QuizManagerController;
import quizmanager.model.*;
import quizmanager.service.QuizService;
import rx.schedulers.Schedulers;

import java.io.*;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
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


        service.loadQuizTitles()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.from(Platform::runLater))
                .subscribe(
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
        final Button cellButton = new Button("Zmień nagrodę");

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


        service.loadQuiz(selectedQuiz)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.from(Platform::runLater))
                .subscribe(
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

            service.uploadQuiz(quizListElement)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.from(Platform::runLater))
                    .subscribe(
                    next -> quizTitles.getItems().add(quizListElement.getName()),
                    System.out::println
            );

        }
    }

    private void changePrize(RecordDto recordDto) {
        if(appController.showChangePrizeDialog(recordDto)) {
            quizDetailsTable.refresh();
            service.updateRecord(recordDto.getId(), recordDto.getPrize().getId()).subscribe(
                    next -> System.out.println("OK"),
                    System.out::println
            );
        }
    }
    @FXML
    public void addPrize() {
        PrizeDto prizeDto = new PrizeDto();

//        if (appController.showAddPrizeDialog(prizeDto, service.getPrizeTypes())) {
        if (appController.showAddPrizeDialog(prizeDto)) {
            service.uploadPrize(prizeDto)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.from(Platform::runLater))
                    .subscribe(
                    next -> {

                    },
                            System.out::println
            );


        }
    }


    @FXML
    public void addPrizeType() {
        var prizeTypeDto = new ArrayList<PrizeTypeDto>();
        if (appController.showNewPrizeTypeDialog(prizeTypeDto)) {
            service.uploadPrizeType(prizeTypeDto)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.from(Platform::runLater))
                    .subscribe(
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
            System.out.println("Message: " + e.getMessage() + ", Cause: " + e.getCause());
        }
    }

    @FXML
    private void exportPdf() {
        exportQuiz("pdf");
    }

    @FXML
    private void exportXlsx() {
        exportQuiz("xlsx");
    }

    private void exportQuiz(String format) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(stage);

        String quizTitle = quizTitles.getSelectionModel().getSelectedItem();
        service.getExportedFile(quizTitle, format)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.from(Platform::runLater))
                .subscribe(
                        responseBody -> saveExportedFile(responseBody, selectedDirectory.getAbsolutePath() + "/" + quizTitle + "-results." + format)
                );
    }

    private void saveExportedFile(ResponseBody responseBody, String filePath) {
        try (InputStream inputStream = responseBody.byteStream(); FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
            byte[] fileReader = new byte[4096];
            while (true) {
                int read = inputStream.read(fileReader);

                if (read == -1) {
                    break;
                }

                fileOutputStream.write(fileReader, 0, read);
            }
            fileOutputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateStrategyForQuiz(StrategyConfigPresenter presenter) {
        RewardingStrategyDto rewardingStrategyDto = presenter.getStrategyDto();
        if(rewardingStrategyDto instanceof SpeedRewardingStrategy strategy) {
            service.updateStrategyForQuiz(quizTitles.getSelectionModel().getSelectedItem(), strategy)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.from(Platform::runLater))
                    .subscribe(
                    System.out::println,
                    System.out::println
            );
        }
        else if (rewardingStrategyDto instanceof CorrectAnswersRewardingStrategy strategy) {
            service.updateStrategyForQuiz(quizTitles.getSelectionModel().getSelectedItem(), strategy)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.from(Platform::runLater))
                    .subscribe(
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

        return loader.load();
    }


    public void setStage(Stage primaryStage) {
        this.stage = primaryStage;
    }




}
