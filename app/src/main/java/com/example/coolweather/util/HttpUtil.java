package com.example.coolweather.util;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by wzj on 2017/8/5.
 */

public class HttpUtil {
    public static void sendHttpRequest(String address, Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }

}
