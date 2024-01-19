package quizmanager.presenter;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import quizmanager.model.PrizeDto;
import quizmanager.service.QuizService;
import rx.schedulers.Schedulers;

import java.net.URL;
import java.util.ResourceBundle;

public class PrizePresenter implements Initializable {

    private final QuizService service;
    private final MainPresenter mainPresenter;

    @FXML
    private ListView<String> prizeList;

    @FXML
    private GridPane gridPane;
    @FXML
    private Button addButton;

    @FXML
    private TextField prizeName;

    @FXML
    private TextField prizeDescription;

    private int currentRow = 0;
    private int currentCol = 0;

    private final PrizeDto prizeDto;

    private final IntegerProperty selected = new SimpleIntegerProperty(0);

    public PrizePresenter(QuizService service, MainPresenter mainPresenter) {
        this.service = service;
        this.mainPresenter = mainPresenter;
        prizeDto = new PrizeDto();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {



        getPrizesList();


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

    private void getPrizesList() {
        service.getPrizes()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.from(Platform::runLater))
                .subscribe(
                        next -> {
                            next.forEach(e -> prizeList.getItems().add(e.toString()));
                        },
                        System.out::println
                );
    }


    @FXML
    private void handleAddAction() {
        prizeDto.setName(prizeName.getText());
        prizeDto.setDescription(prizeDescription.getText());

        service.uploadPrize(prizeDto)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.from(Platform::runLater))
                .subscribe(
                        next -> {
                            prizeList.getItems().add(prizeDto.toString());
                        },
                        System.out::println
                );
    }

}
