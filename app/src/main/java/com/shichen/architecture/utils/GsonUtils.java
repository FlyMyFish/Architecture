package com.shichen.architecture.utils;

import com.google.gson.Gson;

public class GsonUtils {
    Gson gson;

    private GsonUtils() {
        gson = new Gson();
    }

    public static GsonUtils getInstance() {
        return GsonUtilsHolder.sInstance;
    }

    private static class GsonUtilsHolder {
        private static final GsonUtils sInstance = new GsonUtils();
    }

    public Gson get() {
        return gson;
    }
}
