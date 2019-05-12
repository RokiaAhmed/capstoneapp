package com.ikdynmaics.capstoneapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ikdynmaics.capstoneapp.data.cash.PreferenceUtils;
import com.ikdynmaics.capstoneapp.utills.ConnectionDetector;

public class MainActivity extends AppCompatActivity {


    private EditText cityNameEditText, countryNameEditText;
    private Button goButton;
    private ConnectionDetector connectionDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        connectionDetector = new ConnectionDetector(this);


    }

    private void initViews() {
        cityNameEditText = findViewById(R.id.edit_text_enter_city_name);
        countryNameEditText = findViewById(R.id.edit_text_enter_country_name);
        goButton = findViewById(R.id.button_go);
    }

    public void readInputData(View view) {
        if (connectionDetector.isConnectingToInternet()) {
            String cityName = cityNameEditText.getText().toString();
            String countryName = countryNameEditText.getText().toString();
            if (cityName.isEmpty()) {
                Toast.makeText(getApplicationContext(), R.string.error_enter_city, Toast.LENGTH_SHORT).show();
            } else if (countryName.isEmpty()) {
                Toast.makeText(getApplicationContext(), R.string.error_enter_country, Toast.LENGTH_SHORT).show();
            } else if (cityName.isEmpty() && countryName.isEmpty()) {
                Toast.makeText(getApplicationContext(), R.string.error_enter_city_and_country, Toast.LENGTH_SHORT).show();
            } else {
                PreferenceUtils.setCityName(this, cityName);
                PreferenceUtils.setCountryName(this, countryName);
                Intent intent = new Intent(MainActivity.this,DetailsActivity.class);
                startActivity(intent);
            }
        } else {
            Toast.makeText(MainActivity.this, R.string.network_fail, Toast.LENGTH_SHORT).show();
        }
    }

}
