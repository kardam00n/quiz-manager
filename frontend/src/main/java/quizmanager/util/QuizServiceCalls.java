package quizmanager.util;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import quizmanager.model.QuizListElement;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

import java.io.File;

public class QuizServiceCalls {

    public interface SendCallback {
        void onSuccess();
        void onError(String errorMessage);

        void onFailure(String failureMessage);
    }


    public static void send(QuizListElement quizListElement, SendCallback callback) {
        QuizService quizService = RetrofitSingleton.getInstance().getQuizService();
        File file = quizListElement.getFile();

        if (file.exists()) {
            RequestBody description = RequestBody.create(MediaType.parse("text/plain"), "File upload");
            RequestBody fileBody = RequestBody.create(MediaType.parse("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"), file);

            MultipartBody.Part filePart = MultipartBody.Part.createFormData(quizListElement.getName(), file.getName(), fileBody);

            Call<ResponseBody> call = quizService.upload(description, filePart);
            call.enqueue(new Callback<>() {


                @Override
                @EverythingIsNonNull
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        callback.onSuccess();


                    } else {
                        callback.onError(response.message());
                    }
                }

                @Override
                @EverythingIsNonNull
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    callback.onFailure(t.getMessage());

                }
            });
        } else {
            System.out.println("Plik nie istnieje.");
        }

    }


}