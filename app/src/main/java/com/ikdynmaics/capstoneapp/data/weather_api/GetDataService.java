package com.ikdynmaics.capstoneapp.data.weather_api;

import com.ikdynmaics.capstoneapp.data.weather_api.weather_models.Example;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetDataService {

    @GET("/data/2.5/forecast")
    Call<Example> getWeather(
            @Query("q") String cityName
            , @Query("appid") String appId);

}
