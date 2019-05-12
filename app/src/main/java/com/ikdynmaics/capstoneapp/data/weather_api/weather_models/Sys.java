package com.ikdynmaics.capstoneapp.data.weather_api.weather_models;

/**
 * Created by mona.mahdi on 8/15/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sys {

    @SerializedName("pod")
    @Expose
    private String pod;

    public String getPod() {
        return pod;
    }

    public void setPod(String pod) {
        this.pod = pod;
    }

}