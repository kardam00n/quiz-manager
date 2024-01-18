package quizmanager.presenter;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import quizmanager.model.PrizeTypeDto;
import quizmanager.service.QuizService;
import rx.schedulers.Schedulers;

import java.util.List;

public class AddPrizeTypePresenter {

    // top
    @FXML
    private TextField prizeTypeName;

    @FXML
    private Button addPrizeTypeButton;


    // center
    @FXML
    private ListView<String> currentCategories;

    // bottom
    @FXML
    private Button acceptButton;


    private List<PrizeTypeDto> prizeTypeDto;

    private Stage dialogStage;

    private boolean approved;

    private final QuizService service;

    private int pulledPositionCount = 0;


    private final IntegerProperty newPositionCount = new SimpleIntegerProperty(0);


    public AddPrizeTypePresenter(QuizService service) {
        this.service = service;
    }

    @FXML
    private void initialize() {
        acceptButton.disableProperty().bind(
                Bindings.lessThanOrEqual(newPositionCount, 0));


        addPrizeTypeButton.disableProperty().bind(
                Bindings.isEmpty(prizeTypeName.textProperty())
        );

        prizeTypeName.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");


        service.getPrizeTypes()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.from(Platform::runLater))
                .subscribe(
                        next -> {
                            next.forEach(e -> currentCategories.getItems().add(e.getName()));
                            pulledPositionCount = next.size();
                        },
                        System.out::println
                );


        prizeTypeName.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                addPrizeType();

            }
        });


    }

    @FXML
    private void handleCancelAction() {
        dialogStage.close();
    }

    @FXML
    private void handleAcceptAction() {
        updateModel();
        approved = true;
        dialogStage.close();
    }

    private void updateModel() {
        var sth = (currentCategories.getItems().subList(pulledPositionCount, pulledPositionCount + newPositionCount.getValue()));
        sth.forEach(e -> prizeTypeDto.add(new PrizeTypeDto(e)));
    }


    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }


    public boolean isApproved() {
        return approved;
    }

    public void setData(List<PrizeTypeDto> prizeTypeDto) {
        this.prizeTypeDto = prizeTypeDto;
    }

    public void addPrizeType() {
        newPositionCount.set(newPositionCount.getValue() + 1);
        currentCategories.getItems().add(prizeTypeName.getText());
        prizeTypeName.clear();
    }
}
