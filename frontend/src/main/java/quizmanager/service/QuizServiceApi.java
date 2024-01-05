package quizmanager.service;

import javafx.collections.ObservableList;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import quizmanager.model.*;
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


    @GET("/prizeTypes/all")
    Observable<List<PrizeTypeDto>> getPrizeTypes();
    @GET("/prizes/all")
    Observable<List<PrizeDto>> getPrizes();

    @POST("/prizes/add")
    Observable<ResponseBody> uploadPrize(@Body PrizeDto prizeDto);

    @POST("prizeTypes/add")
    Observable<ResponseBody> uploadPrizeType(@Body PrizeTypeDto prizeTypeDto);


    @GET("sth4/")
    Observable<List<PrizeTypeDto>> getPrizeList();

    @GET("sth/{quiz}")
    Observable<StrategyAData> getStrategyAData(@Path("quiz") String quizName);

    @GET("sth/{quiz}")
    Observable<StrategyBData> getStrategyBData(@Path("quiz") String quizName);


    @PUT("sth/{quiz}")
    Observable<ResponseBody> updateStrategyForQuiz(@Path("quiz") String quiz, @Body StrategyAData strategy);

    @PUT("sth/{quiz}")
    Observable<ResponseBody> updateStrategyForQuiz(@Path("quiz") String quiz, @Body StrategyBData strategy);

}
