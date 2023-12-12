package quizmanager.util;

import retrofit2.Retrofit;

public class RetrofitSingleton {

    private static RetrofitSingleton instance = null;
    public static final String BASE_URL = "http://localhost:8080/";

    private QuizService quizService;

    public static RetrofitSingleton getInstance() {
        if (instance == null) {
            instance = new RetrofitSingleton();
        }

        return instance;
    }

    private RetrofitSingleton() {
        buildRetrofit();
    }

    private void buildRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .build();

        this.quizService = retrofit.create(QuizService.class);

        // future services go here
    }

    public QuizService getQuizService() {
        return this.quizService;
    }

}
