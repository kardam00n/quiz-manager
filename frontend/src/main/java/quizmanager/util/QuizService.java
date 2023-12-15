package quizmanager.util;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface QuizService {
    @Multipart
    @POST("something")
    Call<ResponseBody> postQuiz(
            // TODO probably fixme
            @Part("description") RequestBody description,
            @Part MultipartBody.Part file
    );


    @GET("/quizzes/names")
    Call<List<String>> getQuizTitles();

    @GET("/quizzes/get")
    Call<ResponseBody> getQuiz(     // preferably list of records, but don't know what king of object should they be, maybe some DTO?
            @Body String name
    );
}
