package quizmanager.util;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface QuizService {
    @Multipart
    @POST("/quizzes/addQuiz")
    Call<ResponseBody> postQuiz(
            @Part("description") RequestBody description,
            @Part MultipartBody.Part file
    );


    @GET("/quizzes/names")
    Call<List<String>> getQuizTitles();

    @GET("/quizzes/get/{name}")
    Call<List<QuizDto>> getQuiz(
            @Path("name") String name
    );
}
