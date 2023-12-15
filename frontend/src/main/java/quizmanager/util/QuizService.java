package quizmanager.util;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

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
    Call<List<String>> getQuizTitles(
            // TODO probably fixme
    );

    @GET("/quizzes/get")
    Call<ResponseBody> getQuiz(
            // TODO probably fixme
    );
}
