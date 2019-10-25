package com.user.festivalbizerte.WebService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebService {
    private static WebService instance, instance2, instance3;
    private API api;

    public WebService() {

        OkHttpClient client = new OkHttpClient.Builder().build();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(Urls.MAIN_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        api = retrofit.create(API.class);
    }

    public WebService(String baseUrl) {

        OkHttpClient client = new OkHttpClient.Builder().build();
        Retrofit retrofit = new Retrofit.Builder().client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build();
        api = retrofit.create(API.class);
    }

    public static WebService getInstance() {
        if (instance == null)
            instance = new WebService();
        return instance;
    }

    public static WebService getInstance(String baseUrl) {
        if (instance2 == null)
            instance2 = new WebService(baseUrl);
        return instance2;
    }

    public static WebService getInstance(String baseUrl, String ip) {
        if (instance3 == null)
            instance3 = new WebService(baseUrl);
        return instance3;
    }

    public API getApi() {
        return api;
    }
}
