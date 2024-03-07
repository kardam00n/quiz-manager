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

    @GET("/quizzes/{name}/export/{format}")
    @Streaming
    Observable<ResponseBody> getExportedFile(@Path("name") String name, @Path("format") String format);

    @GET("/prizeTypes")
    Observable<List<PrizeTypeDto>> getPrizeTypes();

    @GET("/prizes")
    Observable<List<PrizeDto>> getPrizes();

    @POST("/prizes")
    Observable<ResponseBody> uploadPrize(@Body PrizeDto prizeDto);

    @POST("/prizeTypes")
    Observable<ResponseBody> uploadPrizeType(@Body List<PrizeTypeDto> prizeTypeDto);


    @GET("/strategies/speed")
    Observable<SpeedRewardingStrategy> getSpeedRewardingStrategy();

    @GET("/strategies/correct")
    Observable<CorrectAnswersRewardingStrategy> getCorrectAnswersStrategy();


    @PUT("/strategies/speed")
    Observable<ResponseBody> updateSpeedStrategy(@Body SpeedRewardingStrategy strategy);

    @PUT("/strategies/correct")
    Observable<ResponseBody> updateCorrectStrategy(@Body CorrectAnswersRewardingStrategy strategy);

    @PUT("/records")
    Observable<ResponseBody> updateRecord(@Query(value = "recordId") int recordId, @Query(value = "prizeId") int prizeId);

}
