package quizmanager.presenter;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import quizmanager.controller.QuizManagerController;
import quizmanager.model.QuizListElement;
import quizmanager.service.QuizService;
import rx.schedulers.Schedulers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

@SuppressWarnings("all")
public class QuizListPresenter implements Initializable {
    private final QuizService service;

    private final MainPresenter mainPresenter;
    private final Stage primaryStage;

    @FXML
    private ScrollPane scrollPane;


    public QuizListPresenter(QuizService service, MainPresenter presenter) {
        this.service = service;
        this.mainPresenter = presenter;
        this.primaryStage = presenter.getStage();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        service.loadQuizTitles()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.from(Platform::runLater))
                .subscribe(
                        this::createGridPane,
                        System.out::println


                );
    }

    @FXML
    private void importQuiz(ActionEvent actionEvent) {
        var quizListElement = new QuizListElement();
        if (showFormUploadDialog(quizListElement)) {
            service.uploadQuiz(quizListElement)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.from(Platform::runLater))
                    .subscribe(
                            System.out::println,
                            System.out::println
                    );

        }

    }


    // TODO w ostateczności
    private boolean showFormUploadDialog(QuizListElement quizListElement) {
        try {
            // Load the fxml file and create a new stage for the dialog
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(QuizManagerController.class.getResource("/view/form_upload_dialog.fxml"));
            BorderPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Upload Excel File");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the size of the dialog stage

            // Set the presenter for the view
            FormUploadPresenter presenter = loader.getController();
            presenter.setDialogStage(dialogStage);
            presenter.setData(quizListElement);


            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            return presenter.isApproved();

        } catch (IOException e) {
            System.out.println("Message: " + e.getMessage() + ", Cause: " + e.getCause());
            return false;
        }
    }


    private void createGridPane(List<String> stringList) {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);


        int columnIndex = 0;
        int rowIndex = 0;

        Random random = new Random();

        for (String text : stringList) {

            // tworzymy vBoxa o rozmiarach 150x170
            VBox quizElement = new VBox();
            quizElement.setStyle("-fx-cursor: hand");
            quizElement.setSpacing(10);
            quizElement.setMaxWidth(150);
            quizElement.setMinWidth(150);
            quizElement.setMaxHeight(170);
            quizElement.setMinHeight(170);

            quizElement.setAlignment(Pos.CENTER);



            // tworzymy tło naszego quizu
            Rectangle rectangle = new Rectangle(150, 150, Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
            quizElement.getChildren().add(rectangle);

            // tworzymy label wraz z tooltipem dla długich nazw
            Label quizName = new Label(text);
            quizElement.getChildren().add(quizName);

            Tooltip tooltip = new Tooltip(text);
            Tooltip.install(quizName, tooltip);


            // dodajemy element do grida
            gridPane.add(quizElement, columnIndex, rowIndex);

            GridPane.setHgrow(quizElement, Priority.ALWAYS);
            GridPane.setHalignment(quizElement, HPos.CENTER);


            quizElement.setOnMouseClicked(
                    e -> showQuizDetails(text)
            );

            columnIndex++;

            if (columnIndex == 3) {
                columnIndex = 0;
                rowIndex++;
            }
        }
        scrollPane.setContent(gridPane);
    }


    private void showQuizDetails(String quizName) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/view/quiz_details_light.fxml"));
        loader.setControllerFactory(controllerClass -> new QuizDetailsPresenter(service, mainPresenter, quizName));
        try {
            BorderPane quizDetails = loader.load();
            mainPresenter.switchMainContentTo(quizDetails);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
