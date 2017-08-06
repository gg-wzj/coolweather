package com.example.coolweather.db;

import com.example.coolweather.gson.Aqi;
import com.example.coolweather.gson.Basic;
import com.example.coolweather.gson.Forecast;
import com.example.coolweather.gson.Now;
import com.example.coolweather.gson.Suggestion;

import java.util.List;

/**
 * Created by wzj on 2017/8/5.
 */

public class Weather {
    private Aqi aqi;
    private String status;
    private Basic basic;
    private Now now;
    private Suggestion suggestion;
    private List<Forecast> daily_forecasst;

    public Aqi getAqi() {
        return aqi;
    }

    public void setAqi(Aqi aqi) {
        this.aqi = aqi;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Basic getBasic() {
        return basic;
    }

    public void setBasic(Basic basic) {
        this.basic = basic;
    }

    public Now getNow() {
        return now;
    }

    public void setNow(Now now) {
        this.now = now;
    }

    public Suggestion getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(Suggestion suggestion) {
        this.suggestion = suggestion;
    }

    public List<Forecast> getDaily_forecasst() {
        return daily_forecasst;
    }

    public void setDaily_forecasst(List<Forecast> daily_forecasst) {
        this.daily_forecasst = daily_forecasst;
    }
}
