package quizmanager.service;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import quizmanager.model.QuizListElement;
import quizmanager.model.RecordDto;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

import java.io.File;
import java.util.List;

public class QuizService {
    private final QuizServiceApi service;
    private final static String BASE_URL = "http://localhost:8080";

    public QuizService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();


        this.service = retrofit.create(QuizServiceApi.class);
    }




    public Observable<List<String>> loadQuizTitles() {
        return service.getQuizTitles();
    }


    public Observable<List<RecordDto>> loadQuiz(String name) {
        return service.getQuiz(name);

    }

    public Observable<ResponseBody> uploadQuiz(QuizListElement quizListElement) {
        File file = quizListElement.getFile();

        if (file.exists()) {
            MultipartBody.Part filePart = MultipartBody.Part.createFormData
                    ("file",
                            file.getName(),
                            RequestBody.create(
                                    MediaType.parse("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
                                    file
                            )
                    );
            return  service.postQuiz(filePart);
        } else {
            // TODO cos madrzejszego xd
            System.out.println("File" + file + " does not exist");
            return Observable.empty();
        }
    }
}
