package com.ikdynmaics.capstoneapp.weather;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ikdynmaics.capstoneapp.R;
import com.ikdynmaics.capstoneapp.WeatherAppWidget;
import com.ikdynmaics.capstoneapp.data.cash.PreferenceUtils;
import com.ikdynmaics.capstoneapp.data.weather_api.GetDataService;
import com.ikdynmaics.capstoneapp.data.weather_api.RetrofitClientInstance;
import com.ikdynmaics.capstoneapp.data.weather_api.weather_models.Example;
import com.ikdynmaics.capstoneapp.data.weather_api.weather_models.ListResponse;
import com.ikdynmaics.capstoneapp.utills.ConnectionDetector;
import com.ikdynmaics.capstoneapp.utills.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WeatherFragment extends Fragment {

    private FrameLayout progressFrame;
    private ProgressBar progressBar;
    private ImageView currentWeatherIcon;
    private TextView currentTempTextView, cityTextView,
            weatherDescriptionTextView, minTempTextView, maxTempTextView, minTempTextView1, maxTempTextView1,
            minTempTextView2, maxTempTextView2;

    private GetDataService service;
    private List<ListResponse> responseList;
    private String cityNameResponse;


    public WeatherFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_weather, container, false);
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        initViews(rootView);
        ConnectionDetector connectionDetector = new ConnectionDetector(getActivity());
        if (connectionDetector.isConnectingToInternet()) {
            getWeather();
//            new WeatherAsyncTask().execute();
        } else {
            Toast.makeText(getActivity(), getString(R.string.network_fail), Toast.LENGTH_SHORT).show();
        }
        return rootView;
    }

    private void initViews(View rootView) {
        progressFrame = rootView.findViewById(R.id.progress_frame);
        progressBar = rootView.findViewById(R.id.progress_bar);
        currentWeatherIcon = rootView.findViewById(R.id.iv_weather_icon);
        currentTempTextView = rootView.findViewById(R.id.tv_weather_temp);
        cityTextView = rootView.findViewById(R.id.tv_city_name);
        weatherDescriptionTextView = rootView.findViewById(R.id.tv_weather_description);
        minTempTextView = rootView.findViewById(R.id.tv_min_temp);
        maxTempTextView = rootView.findViewById(R.id.tv_max_temp);
        minTempTextView1 = rootView.findViewById(R.id.tv_min_temp_1);
        maxTempTextView1 = rootView.findViewById(R.id.tv_max_temp_1);
        minTempTextView2 = rootView.findViewById(R.id.tv_min_temp_2);
        maxTempTextView2 = rootView.findViewById(R.id.tv_max_temp_2);

    }

    private void populateUI(List<ListResponse> listResponses, String cityNameResponse) {
        String icon = "http://openweathermap.org/img/w/" +
                listResponses.get(0).getWeather().get(0).getIcon() + ".png";
        Picasso.get().load(icon).into(currentWeatherIcon);
        currentTempTextView.setText(Utils.convertTempFromKTOC(listResponses.get(0).getMain().getTemp()));
        cityTextView.setText(cityNameResponse);
        weatherDescriptionTextView.setText(listResponses.get(0).getWeather().get(0).getDescription());
        minTempTextView.setText(Utils.convertTempFromKTOC(listResponses.get(1).getMain().getTempMin()));
        maxTempTextView.setText(Utils.convertTempFromKTOC(listResponses.get(1).getMain().getTempMax()));
        minTempTextView1.setText(Utils.convertTempFromKTOC(listResponses.get(2).getMain().getTempMin()));
        maxTempTextView1.setText(Utils.convertTempFromKTOC(listResponses.get(2).getMain().getTempMax()));
        minTempTextView2.setText(Utils.convertTempFromKTOC(listResponses.get(3).getMain().getTempMin()));
        maxTempTextView2.setText(Utils.convertTempFromKTOC(listResponses.get(3).getMain().getTempMax()));
    }

//    private class WeatherAsyncTask extends AsyncTask<Void, Void, Void> {
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            getWeather();
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            populateUI(responseList, cityNameResponse);
//        }
//    }

    private void getWeather() {
        progressFrame.setVisibility(View.VISIBLE);

        final String cityCountryName = PreferenceUtils.getCityName(getActivity()) + "," + PreferenceUtils.getCountryName(getActivity());
        Call<Example> call = service.getWeather(cityCountryName, getString(R.string.weather_key));
        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                if (response.code() == 404) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), getString(R.string.error_404), Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), getString(R.string.error_401), Toast.LENGTH_SHORT).show();
                } else {
                    responseList = response.body().getList();
                    String cityName = response.body().getCity().getName();
                    PreferenceUtils.setCityName(getActivity(), cityName);
                    cityNameResponse = cityName + ", " + response.body().getCity().getCountry();
                    progressFrame.setVisibility(View.GONE);
                    populateUI(responseList, cityNameResponse);

//                    Double temp = responseList.get(0).getMain().getTemp();
                    saveWidgetDetails(responseList.get(0), cityNameResponse);
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Log.e("fail", t.getMessage());
            }
        });
    }

    private void saveWidgetDetails(ListResponse item, String cityName) {
        String icon = "http://openweathermap.org/img/w/" + item.getWeather().get(0).getIcon() + ".png";

        PreferenceUtils.write(getActivity(), "icon", icon);
        PreferenceUtils.write(getActivity(), "temp", Utils.convertTempFromKTOC(item.getMain().getTemp()));
        PreferenceUtils.write(getActivity(), "city", cityName);
        PreferenceUtils.write(getActivity(), "desc", item.getWeather().get(0).getDescription());
        PreferenceUtils.write(getActivity(), "min_temp", Utils.convertTempFromKTOC(item.getMain().getTempMin()));
        PreferenceUtils.write(getActivity(), "max_temp", Utils.convertTempFromKTOC(item.getMain().getTempMax()));


        int[] ids = AppWidgetManager.getInstance(getActivity()).getAppWidgetIds(new ComponentName(getActivity(), WeatherAppWidget.class));
        WeatherAppWidget mWidget = new WeatherAppWidget();
        mWidget.onUpdate(getActivity(), AppWidgetManager.getInstance(getActivity()), ids);
    }


}
