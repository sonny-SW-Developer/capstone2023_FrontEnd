package com.example.a23__project_1.retrofit;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClientJwt {
    // BASE_URL
    private static final String BASE_URL = "http://13.124.202.241:8080";
    // Timeout 시간 (20초)
    private static final long CONNECT_TIMEOUT_SEC = 20L;
    private static Retrofit retrofit = null;

    /** Retrofit instance get - 없을 경우 생성 **/
    public static Retrofit getInstance(final String token) {
        if (retrofit == null) {
            // Interceptor 생성 - Http 요청/응답의 모든 정보를 로깅할 수 있도록 설정
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            // OkHttpClient 생성 - Interceptor 설정, Timeout 시간 설정
            OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
            clientBuilder.addInterceptor(loggingInterceptor)
                    .connectTimeout(CONNECT_TIMEOUT_SEC, TimeUnit.SECONDS)
                    .addInterceptor(new Interceptor() {
                        @NonNull
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request original = chain.request();
                            Request.Builder requestBuilder = original.newBuilder()
                                    .header("Authorization", token)
                                    .method(original.method(), original.body());
                            Request request = requestBuilder.build();
                            return chain.proceed(request);
                        }
                    });

            // GsonBuilder 설정
            Gson gson = new GsonBuilder().setLenient().create();

            // Retrofit 설정
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson)) // JSON -> Java 객체
                    .addConverterFactory(ScalarsConverterFactory.create())  // String, HTML -> Java 객체
                    .client(clientBuilder.build())
                    .build();
        }
        return retrofit;
    }

    /** Retrofit instance 를 통해 RetrofitAPI interface 의 구현체 를 반환 **/
    public static RetrofitAPI getApiService(String token) {
        return getInstance(token).create(RetrofitAPI.class);
    }
}
