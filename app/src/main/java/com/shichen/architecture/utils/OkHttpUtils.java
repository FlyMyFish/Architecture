package com.shichen.architecture.utils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class OkHttpUtils {

    private static final long READ_TIME_OUT_VALUE=15;
    private static final long WRITE_TIME_OUT_VALUE=15;

    private OkHttpClient mOkHttpClient;

    private OkHttpUtils(){
        initClient();
    }

    private void initClient(){
        mOkHttpClient=new OkHttpClient.Builder()
                //.addInterceptor(new GZipRequestInterceptor())
                .readTimeout(READ_TIME_OUT_VALUE, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIME_OUT_VALUE,TimeUnit.SECONDS)
                .build();
    }
    public static OkHttpUtils getInstance(){
        return OkHttpHolder.sInstance;
    }

    private static class OkHttpHolder{
        private static final OkHttpUtils sInstance=new OkHttpUtils();
    }

    public OkHttpClient client(){
        return mOkHttpClient;
    }
}
