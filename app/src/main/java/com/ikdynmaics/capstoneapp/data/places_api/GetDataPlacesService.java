package com.ikdynmaics.capstoneapp.data.places_api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetDataPlacesService {

    @GET("/maps/api/place/textsearch/json")
    Call<com.ikdynmaics.capstoneapp.data.places_api.places_models.Example> getPlaces(
            @Query("query") String parameters
            , @Query("key") String appId);

}
