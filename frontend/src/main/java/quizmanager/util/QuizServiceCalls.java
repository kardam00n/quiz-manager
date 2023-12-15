package quizmanager.util;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import quizmanager.model.RecordDto;
import quizmanager.model.QuizListElement;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

import java.io.File;
import java.util.List;

public class QuizServiceCalls {

    public interface SendCallback<T> {
        void onSuccess(Response<T> response);

        void onError(Response<T> response);

        void onFailure(String failureMessage);
    }


    public static void loadQuizTitles(SendCallback<List<String>> callback) {
        QuizService quizService = RetrofitSingleton.getInstance().getQuizService();

        Call<List<String>> call = quizService.getQuizTitles();
        call.enqueue(new Callback<>() {


            @Override
            @EverythingIsNonNull
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response);


                } else {
                    callback.onError(response);
                }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<List<String>> call, Throwable t) {
                callback.onFailure(t.getMessage());

            }
        });
    }


    public static void loadQuiz(String name, SendCallback<List<RecordDto>> callback) {
        QuizService quizService = RetrofitSingleton.getInstance().getQuizService();

        Call<List<RecordDto>> call = quizService.getQuiz(name);
//        sendRequest(callback, call);

        call.enqueue(new Callback<>() {


            @Override
            @EverythingIsNonNull
        public void onResponse(Call<List<RecordDto>> call, Response<List<RecordDto>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response);


                } else {
                    callback.onError(response);
                }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<List<RecordDto>> call, Throwable t) {
                callback.onFailure(t.getMessage());

            }
        });

    }

    public static void uploadQuiz(QuizListElement quizListElement, SendCallback<ResponseBody> callback) {
        QuizService quizService = RetrofitSingleton.getInstance().getQuizService();
        File file = quizListElement.getFile();

        if (file.exists()) {
            RequestBody description = RequestBody.create(MediaType.parse("text/plain"), "File upload");
            RequestBody fileBody = RequestBody.create(MediaType.parse("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"), file);

            MultipartBody.Part filePart = MultipartBody.Part.createFormData(quizListElement.getName(), file.getName(), fileBody);

            Call<ResponseBody> call = quizService.postQuiz(description, filePart);
            sendRequest(callback, call);
        } else {
            System.out.println("File" + file + " does not exist");
        }

    }


    private static void sendRequest(SendCallback<ResponseBody> callback, Call<ResponseBody> call) {
        call.enqueue(new Callback<>() {


            @Override
            @EverythingIsNonNull
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response);
                } else {
                    callback.onError(response);
                }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onFailure(t.getMessage());

            }
        });

    }


}