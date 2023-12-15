package quizmanager.util;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface QuizService {

    // TODO
    //  - tutaj trzeba zmienić something na jakiś endpoint poprawny
    //  - poprawnie wysłać xlsx, sprawdzić, czy "description" jest poprawne i potrzebne,
    //    czy nie brakuje jakichś danych w zapytaniu oraz ewentualnie zmienić ResponseBody na
    //    odp. typ
    @Multipart
    @POST("/quizzes/addQuiz")
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
