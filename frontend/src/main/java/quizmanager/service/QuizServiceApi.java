package quizmanager.service;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import quizmanager.model.*;
import retrofit2.http.*;
import rx.Observable;

import javax.management.ConstructorParameters;
import java.util.List;

public interface QuizServiceApi {
    @Multipart
    @POST("/quizzes")
    Observable<ResponseBody> postQuiz(@Part MultipartBody.Part filePart);


    @GET("/quizzes/names")
    Observable<List<String>> getQuizTitles();

    @GET("/quizzes/{name}")
    Observable<List<RecordDto>> getQuiz(@Path("name") String name);


    @GET("/prizeTypes")
    Observable<List<PrizeTypeDto>> getPrizeTypes();
    @GET("/prizes")
    Observable<List<PrizeDto>> getPrizes();

    @POST("/prizes")
    Observable<ResponseBody> uploadPrize(@Body PrizeDto prizeDto);

    @POST("/prizeTypes")
    Observable<ResponseBody> uploadPrizeType(@Body List<PrizeTypeDto> prizeTypeDto);


    // TODO co ja tu mialem na myśli ...
    @GET("/strategies")
    Observable<List<PrizeTypeDto>> getPrizeList();

    @GET("/strategies/speed")
    Observable<SpeedRewardingStrategy> getSpeedRewardingStrategy();

    @GET("/strategies/correct")
    Observable<CorrectAnswersRewardingStrategy> getCorrectAnswersStrategy();


    @PUT("/strategies/{quizName}")
    Observable<ResponseBody> updateStrategyForQuiz(@Path("quizName") String quizName, @Body SpeedRewardingStrategy strategy);

    @PUT("/strategies/{quizName}")
    Observable<ResponseBody> updateStrategyForQuiz(@Path("quizName") String quizName, @Body CorrectAnswersRewardingStrategy strategy);

    @PUT("/records")
    Observable<ResponseBody> updateRecord(@Query(value = "recordId") int recordId, @Query(value = "prizeId") int prizeId);

}
