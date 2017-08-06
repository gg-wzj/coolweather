package com.example.coolweather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.Log;

import com.example.coolweather.util.HttpUtil;
import com.example.coolweather.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private  String key = "5ebc06a0e92b4b4e88ba0e40859d7a8b";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*new Thread(){
            @Override
            public void run() {
                super.run();
                HttpUtil.sendHttpRequest("http://guolin.tech/api/weather?cityid=CN101090102&key=5ebc06a0e92b4b4e88ba0e40859d7a8b", new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.i("wzj","failure");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String result = response.body().string();
                        Utility.handleWeatherRespone(result);
                    }
                });
            }
        }.start();*/

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString  =preferences.getString("weather",null);
        if(weatherString!= null){
            Intent intent = new Intent(this,WeatherActivity.class);
            startActivity(intent);
            finish();
        }

    }
}
