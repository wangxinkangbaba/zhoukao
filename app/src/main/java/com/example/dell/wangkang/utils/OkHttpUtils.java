package com.example.dell.wangkang.utils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;



public class OkHttpUtils {
    private static OkHttpClient client = null;

    public static OkHttpClient getInstance(){
        if(client==null){
            synchronized (OkHttpUtils.class){
                if(client==null){
                    client = new OkHttpClient.Builder()
                            .connectTimeout(2000, TimeUnit.SECONDS)
                            .writeTimeout(2000,TimeUnit.SECONDS)
                            .readTimeout(2000,TimeUnit.SECONDS)
                            .build();
                }
            }
        }
        return client;
    }
}
