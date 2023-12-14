package quizmanager.util;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface QuizService {

    // TODO
    //  - tutaj trzeba zmienić something na jakiś endpoint poprawny
    //  - poprawnie wysłać xlsx, sprawdzić, czy "description" jest poprawne i potrzebne,
    //    czy nie brakuje jakichś danych w zapytaniu oraz ewentualnie zmienić ResponseBody na
    //    odp. typ
    @Multipart
    @POST("something")
    Call<ResponseBody> postQuiz(
            // TODO probably fixme
            @Part("description") RequestBody description,
            @Part MultipartBody.Part file
    );


    // TODO
    //  - zmienić sth na sensowny endpoint, nwm czy tu jest potrzebne jakieś sprecyzowanie zapytania?
    @GET("sth")
    Call<ResponseBody> getQuizTitles(
            // TODO probably fixme
    );


    // TODO
    //  - sth_else -> endpoint
    //  - dodać @PATH no i znowu upewnić się że tu niczego nie brakuje
    @GET("sth_else")
    Call<ResponseBody> getQuiz(
            // TODO probably fixme
    );
}
