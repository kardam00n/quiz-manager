package quizmanager.presenter;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import quizmanager.service.QuizService;
import rx.schedulers.Schedulers;

import java.net.URL;
import java.util.ResourceBundle;

public class PrizeTypePresenter implements Initializable {

private final QuizService service;


public PrizeTypePresenter(QuizService service) {
    this.service = service;
}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        service.getPrizeTypes()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.from(Platform::runLater))
                .subscribe(
                        System.out::println,
                        System.out::println
                );
    }
}
