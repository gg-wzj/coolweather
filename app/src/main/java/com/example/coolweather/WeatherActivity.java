package com.example.coolweather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.coolweather.gson.Forecast;
import com.example.coolweather.gson.Weather;
import com.example.coolweather.util.HttpUtil;
import com.example.coolweather.util.Utility;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by wzj on 2017/8/5.
 */

public class WeatherActivity extends AppCompatActivity {

    private TextView tv_title;
    private TextView tv_time;
    private TextView tv_tempeture;
    private TextView tv_weather;
    private TextView tv_aqi;
    private TextView tv_pm;
    private TextView tv_wear;
    private TextView tv_sport;
    private TextView tv_comfort;
    private LinearLayout layout;
    private LinearLayout layout_aqi;
    private ImageView iv;
    private SharedPreferences sharedPreferences;
    private Weather mWeather;
    public SwipeRefreshLayout swipeRefreshLayout;
    public DrawerLayout drawerLayout;
    private Button btn_dr_back;
    private Button btn_bg;
    private int NOW_PICTURE;
    private int INTER = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        intitView();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = sharedPreferences.getString("weather", null);
//        在没有设置drawerlayout之前，如果要重新选择城市，按照书上会有bug，就是显示的依然是第一次选择的
//        通过fragment传入选择的cityId，来判断是否与缓存的城市相同，不像同则也是向网络请求
        String pic = sharedPreferences.getString("pic", null);
        if (pic != null) {
            Glide.with(this).load(pic).into(iv);
        } else {
            loadpic();
        }


        if (weatherString != null) {
            Weather weather = Utility.handleWeatherRespone(weatherString);
            mWeather = weather;
            showInfo(weather);
        } else {
            String weatherId = getIntent().getStringExtra("weatherId");
            requestWeather(weatherId);
        }
    }

    private void loadpic() {
        String picUrl = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendHttpRequest(picUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String pic = response.body().string();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("pic", pic);
                editor.apply();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(WeatherActivity.this).load(pic).into(iv);
                    }
                });
            }
        });
    }

    public void requestWeather(String weatherId) {
        String weatherUrl = "http://guolin.tech/api/weather?cityid=" + weatherId + "&key=5ebc06a0e92b4b4e88ba0e40859d7a8b";
        HttpUtil.sendHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final Weather weather = Utility.handleWeatherRespone(responseText);
                mWeather = weather;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weather != null && weather.getStatus().equals("ok")) {
                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                            editor.putString("weather", responseText);
                            editor.apply();
                            showInfo(weather);

                        }
                    }
                });
            }
        });
        swipeRefreshLayout.setRefreshing(false);
    }

    private void showInfo(Weather weather) {
        tv_title.setText(weather.getBasic().getCity());
        tv_time.setText(weather.getBasic().getUpdate().getLoc());
        tv_tempeture.setText(weather.getNow().getTmp());
        tv_weather.setText(weather.getNow().getCond().getTxt());
        int j = weather.getDaily_forecast().size();
        layout.removeAllViews();
        for (int i = 0; i < j; i++) {
            View view = this.getLayoutInflater().inflate(R.layout.forecast_style, layout, false);
            TextView tv_date = view.findViewById(R.id.tv_date);
            TextView tv_foreweather = view.findViewById(R.id.tv_foreweather);
            TextView tv_max = view.findViewById(R.id.tv_max);
            TextView tv_min = view.findViewById(R.id.tv_min);
            Forecast ob = weather.getDaily_forecast().get(i);
            tv_date.setText(ob.getDate());
            tv_foreweather.setText(ob.getCond().getTxt_d());
            tv_max.setText(ob.getTmp().getMax());
            tv_min.setText(ob.getTmp().getMin());
            layout.addView(view);
        }
//在这里会包空指针，因为有的城市的返回数据没有aqi!!!!!!!搞得我以为是我代码的问题，竟然是服务器返回的数据的问题
        if (weather.getAqi() == null) {
            layout_aqi.setVisibility(View.GONE);
        } else {
            layout_aqi.setVisibility(View.VISIBLE);
            tv_aqi.setText(weather.getAqi().getCity().getAqi());
            tv_pm.setText(weather.getAqi().getCity().getPm25());
        }
        tv_comfort.setText(weather.getSuggestion().getComf().getBrf() +
                weather.getSuggestion().getComf().getTxt());
        tv_wear.setText(weather.getSuggestion().getCw().getBrf() +
                weather.getSuggestion().getCw().getTxt());
        tv_sport.setText(weather.getSuggestion().getSport().getBrf() +
                weather.getSuggestion().getSport().getTxt());

    }

    private void intitView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_tempeture = (TextView) findViewById(R.id.tv_tempeture);
        tv_weather = (TextView) findViewById(R.id.tv_weather);
        tv_aqi = (TextView) findViewById(R.id.tv_aqi);
        tv_pm = (TextView) findViewById(R.id.tv_pm);
        tv_wear = (TextView) findViewById(R.id.tv_wear);
        tv_sport = (TextView) findViewById(R.id.tv_sport);
        tv_comfort = (TextView) findViewById(R.id.tv_comfort);

        layout = (LinearLayout) findViewById(R.id.liner_forecast);
        layout_aqi = (LinearLayout) findViewById(R.id.layout_aqi);

        iv = (ImageView) findViewById(R.id.iv);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swip);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestWeather(mWeather.getBasic().getId());
            }
        });

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        btn_dr_back = (Button) findViewById(R.id.btn_dr_back);
        btn_dr_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        btn_bg = (Button) findViewById(R.id.bg);
        btn_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NOW_PICTURE == INTER) {
                    iv.setImageResource(R.drawable.timg);
                    NOW_PICTURE = 0;
                }else {
                    loadpic();
                    NOW_PICTURE = INTER;
                }
            }
        });

        Intent intent = new Intent(WeatherActivity.this,AutoUpdateService.class);
        startService(intent);
    }
}
