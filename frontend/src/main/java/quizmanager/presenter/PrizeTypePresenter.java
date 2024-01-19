package quizmanager.presenter;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import quizmanager.model.PrizeTypeDto;
import quizmanager.service.QuizService;
import rx.schedulers.Schedulers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PrizeTypePresenter implements Initializable {

    private final QuizService service;
    private final MainPresenter mainPresenter;
    public TextField prizeTypeName;
    public Button addPrizeTypeButton;









    @FXML
    private ListView<String> currentCategories;




    private final List<PrizeTypeDto> prizeTypeDto;



    public PrizeTypePresenter(QuizService service, MainPresenter presenter) {
        this.service = service;
        this.mainPresenter = presenter;
        prizeTypeDto = new ArrayList<>();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {




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
                        },
                        System.out::println
                );


        prizeTypeName.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                addPrizeType();

            }
        });
    }




    public void addPrizeType() {
        prizeTypeDto.add(new PrizeTypeDto(prizeTypeName.getText()));


        service.uploadPrizeType(prizeTypeDto)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.from(Platform::runLater))
                .subscribe(
                        next -> {

                            currentCategories.getItems().add(prizeTypeName.getText());
                            prizeTypeName.clear();
                        },
                        System.out::println
                );
    }
}
