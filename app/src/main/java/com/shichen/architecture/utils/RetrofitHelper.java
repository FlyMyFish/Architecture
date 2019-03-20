package com.shichen.architecture.utils;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {
    private Retrofit mRetrofit;
    private RetrofitHelper(){
        initClient();
    }

    private void initClient(){
        mRetrofit=new Retrofit.Builder()
                .baseUrl("http://apicloud.mob.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(OkHttpUtils.getInstance().client())
                .build();
    }
    public static RetrofitHelper getInstance(){
        return RetrofitHolder.sInstance;
    }

    private static class RetrofitHolder{
        private static final RetrofitHelper sInstance=new RetrofitHelper();
    }

    public Retrofit get(){
        return mRetrofit;
    }
}
