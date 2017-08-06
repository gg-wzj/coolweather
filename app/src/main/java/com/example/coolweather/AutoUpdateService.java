package com.example.coolweather;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.app.AlarmManagerCompat;

import com.example.coolweather.gson.Weather;
import com.example.coolweather.util.HttpUtil;
import com.example.coolweather.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by wzj on 2017/8/6.
 */

public class AutoUpdateService extends Service {

    private SharedPreferences pref;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        updateWeather();
        updatePic();
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        long triggTime = SystemClock.elapsedRealtime() + 1000 * 60 * 2;
        Intent intent1 = new Intent(this, AutoUpdateService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent1, 0);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME, triggTime, pendingIntent);

        return super.onStartCommand(intent, flags, startId);
    }

    private void updatePic() {
        final String pic_url = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendHttpRequest(pic_url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String pic = response.body().string();
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("pic", pic);
                editor.apply();
            }
        });
    }

    private void updateWeather() {
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        final String weatherString = pref.getString("weather", null);
        if (weatherString != null) {
            Weather weather = Utility.handleWeatherRespone(weatherString);
            String cityId = weather.getBasic().getId();
            final String weatherUrl = "http://guolin.tech/api/weather?cityid=" + cityId + "&key=5ebc06a0e92b4b4e88ba0e40859d7a8b";
            HttpUtil.sendHttpRequest(weatherUrl, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String update_weather = response.body().string();
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("weather", update_weather);
                    editor.apply();
                }
            });
        }
    }
}
