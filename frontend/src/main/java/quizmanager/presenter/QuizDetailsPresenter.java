package quizmanager.presenter;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import quizmanager.model.CorrectAnswersRewardingStrategy;
import quizmanager.model.RecordDto;
import quizmanager.model.RewardingStrategyDto;
import quizmanager.model.SpeedRewardingStrategy;
import quizmanager.service.QuizService;
import rx.schedulers.Schedulers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class QuizDetailsPresenter implements Initializable {

    private final Stage stage;
    private final QuizService service;

    private final MainPresenter mainPresenter;

    private final String quizName;


    @FXML
    private Label quizTitleLabel;

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
    private TableColumn<RecordDto, String> prize;

    @FXML
    private TableColumn<RecordDto, Boolean> prizeChangeButton;


    public QuizDetailsPresenter(QuizService service, MainPresenter presenter, String quizName) {
        this.service = service;
        this.mainPresenter = presenter;
        stage = mainPresenter.getStage();
        this.quizName = quizName;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        quizTitleLabel.setText(quizName);
        nickname.setCellValueFactory(new PropertyValueFactory<>("nickname"));
        score.setCellValueFactory(new PropertyValueFactory<>("score"));
        startTimestamp.setCellValueFactory(new PropertyValueFactory<>("startTimestamp"));
        endTimestamp.setCellValueFactory(new PropertyValueFactory<>("endTimestamp"));
        prize.setCellValueFactory(new PropertyValueFactory<>("prize"));
        prizeChangeButton.setCellFactory(recordDtoBooleanTableColumn -> new ButtonCell());

        getAndShowQuizDetails(quizName);
    }

    private void getAndShowQuizDetails(String selectedQuiz) {
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

        String quizTitle = quizName;
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

    @FXML
    private void configureStrategy(ActionEvent actionEvent) {
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


    private BorderPane loadDialogView(FXMLLoader loader) throws IOException {
        loader.setLocation(QuizManagerController.class.getResource("/view/strategy_config_dialog.fxml"));
        loader.setControllerFactory(controllerClass -> new StrategyConfigPresenter(service));

        return loader.load();
    }

    private Stage createDialogStage(BorderPane page) {
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Konfiguracja strategii [" + quizName + "]");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(stage);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        return dialogStage;
    }

    private StrategyConfigPresenter setViewPresenter(FXMLLoader loader, Stage dialogStage) {
        StrategyConfigPresenter presenter = loader.getController();
        presenter.setDialogStage(dialogStage);
        presenter.setData(quizName);
        return presenter;
    }


    private void updateStrategyForQuiz(StrategyConfigPresenter presenter) {
        RewardingStrategyDto rewardingStrategyDto = presenter.getStrategyDto();
        if (rewardingStrategyDto instanceof SpeedRewardingStrategy strategy) {
            service.updateSpeedStrategy(strategy)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.from(Platform::runLater))
                    .subscribe(
                            System.out::println,
                            System.out::println
                    );
        } else if (rewardingStrategyDto instanceof CorrectAnswersRewardingStrategy strategy) {
            service.updateCorrectStrategy(strategy)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.from(Platform::runLater))
                    .subscribe(
                            System.out::println,
                            System.out::println
                    );

        }
    }


    private class ButtonCell extends TableCell<RecordDto, Boolean> {
        final Button cellButton = new Button("Zmień nagrodę");

        ButtonCell() {

            cellButton.setOnAction(actionEvent -> {
                RecordDto recordDto = quizDetailsTable.getItems().get(getTableRow().getIndex());
                changePrize(recordDto);
            });
        }

        //Display button if the row is not empty
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {
                setGraphic(cellButton);
            }
        }
    }


    private void changePrize(RecordDto recordDto) {
        if (showChangePrizeDialog(recordDto)) {
            quizDetailsTable.refresh();
            service.updateRecord(recordDto.getId(), recordDto.getPrize().getId()).subscribe(
                    next -> System.out.println("OK"),
                    System.out::println
            );
        }
    }


    public boolean showChangePrizeDialog(RecordDto recordDto) {
        try {
            // Load the fxml file and create a new stage for the dialog
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(QuizManagerController.class.getResource("/view/change_prize_dialog.fxml"));
            BorderPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Change prize");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainPresenter.getStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the size of the dialog stage

            // Set the presenter for the view
            ChangePrizePresenter presenter = loader.getController();
            presenter.setDialogStage(dialogStage);
            presenter.setData(recordDto);


            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            return presenter.isApproved();

        } catch (IOException e) {
            System.out.println("Message: " + e.getMessage() + ", Cause: " + e.getCause());
            return false;
        }
    }


}
