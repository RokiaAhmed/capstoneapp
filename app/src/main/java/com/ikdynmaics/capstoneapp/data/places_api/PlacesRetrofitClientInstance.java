package com.ikdynmaics.capstoneapp.data.places_api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlacesRetrofitClientInstance {

    public static final String baseUrl = "https://maps.googleapis.com";
    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        // 1)- to log urls
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    // 2)- add instance here
                    .client(client)
                    .build();
        }
        return retrofit;
    }
}
