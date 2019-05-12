package com.ikdynmaics.capstoneapp.data.cash;

import android.content.Context;
import android.content.SharedPreferences;

import com.ikdynmaics.capstoneapp.R;

public class PreferenceUtils {

    private static String sharedName = "my_shared";

    public static void setCityName(Context context, String city) {
        SharedPreferences sp = context.getSharedPreferences(sharedName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("city", city);
        editor.apply();
    }

    public static String getCityName(Context context) {
        SharedPreferences sp = context.getSharedPreferences(sharedName, Context.MODE_PRIVATE);
        return sp.getString("city", null);
    }

    public static void setCountryName(Context context, String country) {
        SharedPreferences sp = context.getSharedPreferences(sharedName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("country", country);
        editor.apply();
    }

    public static String getCountryName(Context context) {
        SharedPreferences sp = context.getSharedPreferences(sharedName, Context.MODE_PRIVATE);
        return sp.getString("country", null);
    }

    public static void setPlacesList(Context context, String placeName, String country) {
        SharedPreferences sp = context.getSharedPreferences(sharedName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(placeName, country);
        editor.apply();
    }

    public static String getPlacesList(Context context, String placeName) {
        SharedPreferences sp = context.getSharedPreferences(sharedName, Context.MODE_PRIVATE);
        return sp.getString(placeName, null);
    }

    public static void write(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(sharedName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String read(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(sharedName, Context.MODE_PRIVATE);
        return sp.getString(key, null);
    }
}
