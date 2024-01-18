package quizmanager.service;

import javafx.application.Platform;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import quizmanager.model.*;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.schedulers.Schedulers;

import java.io.File;
import java.util.List;
import java.util.Objects;


@SuppressWarnings("unused")
public class QuizService {
    private final QuizServiceApi service;
    private final static String BASE_URL = "http://localhost:8080";


    public QuizService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();


        this.service = retrofit.create(QuizServiceApi.class);
    }


    public Observable<List<String>> loadQuizTitles() {
        return service.getQuizTitles();
    }


    public Observable<List<RecordDto>> loadQuiz(String name) {
        return service.getQuiz(name);

    }

    public Observable<ResponseBody> uploadQuiz(QuizListElement quizListElement) {
        File file = quizListElement.getFile();

        if (file.exists()) {
            MultipartBody.Part filePart = MultipartBody.Part.createFormData
                    ("file",
                            quizListElement.getName(),
                            RequestBody.create(Objects.requireNonNull(MediaType.parse("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")), file)
                    );
            return  service.postQuiz(filePart);
        } else {
            System.out.println("File" + file + " does not exist");
            return Observable.empty();
        }
    }

    public Observable<List<PrizeTypeDto>> getPrizeTypes(){
        return service.getPrizeTypes();
    }
    public Observable<List<PrizeDto>> getPrizes(){
        return service.getPrizes();
    }

    public Observable<ResponseBody> uploadPrize(PrizeDto prizeDto) {
        return service.uploadPrize(prizeDto);
    }

    public Observable<ResponseBody> uploadPrizeType(List<PrizeTypeDto> prizeTypeDto){
        return service.uploadPrizeType(prizeTypeDto);
    }



    public Observable<SpeedRewardingStrategy> getStrategyAData(String quizTitle) {
        Observable<SpeedRewardingStrategy> speedRewardingStrategy = service.getSpeedRewardingStrategy();
        speedRewardingStrategy.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.from(Platform::runLater))
                .subscribe(
                        next -> {
                            System.out.println(next.getName());
                        }
                );;
        return speedRewardingStrategy;
    }

    public Observable<CorrectAnswersRewardingStrategy> getStrategyBData(String quizTitle) {
        return service.getCorrectAnswersStrategy();
    }

    public Observable<ResponseBody> updateStrategyForQuiz(String quizTitle, SpeedRewardingStrategy speedRewardingStrategy){
        return service.updateStrategyForQuiz(quizTitle, speedRewardingStrategy);
    }

    public Observable<ResponseBody> updateStrategyForQuiz(String quizTitle, CorrectAnswersRewardingStrategy correctAnswersRewardingStrategy){
        return service.updateStrategyForQuiz(quizTitle, correctAnswersRewardingStrategy);
    }

    public Observable<ResponseBody> updateRecord(int recordId, int prizeId) {
        return service.updateRecord(recordId, prizeId);
    }

}
