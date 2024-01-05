package quizmanager.service;

import javafx.collections.ObservableList;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import quizmanager.model.PrizeDto;
import quizmanager.model.PrizeTypeDto;
import quizmanager.model.RecordDto;
import retrofit2.http.*;
import rx.Observable;

import java.util.List;

public interface QuizServiceApi {
    @Multipart
    @POST("/quizzes/addQuiz")
    Observable<ResponseBody> postQuiz(@Part MultipartBody.Part filePart);


    @GET("/quizzes/names")
    Observable<List<String>> getQuizTitles();

    @GET("/quizzes/getQuiz/{name}")
    Observable<List<RecordDto>> getQuiz(@Path("name") String name);


    @GET("/sth")
    Observable<List<PrizeTypeDto>> getPrizeTypes();

    @POST("/sth2")
    Observable<ResponseBody> uploadPrize(@Body PrizeDto prizeDto);
}
