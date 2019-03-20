package com.shichen.architecture.data.source.remote;

import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.shichen.architecture.data.CategoryBean;
import com.shichen.architecture.data.source.ICategoryBeanSource;
import com.shichen.architecture.data.source.IDataApi;
import com.shichen.architecture.utils.GsonUtils;
import com.shichen.architecture.utils.RetrofitHelper;

import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class CategoryBeanRemoteSource implements ICategoryBeanSource {

    private final String TAG = "CategoryRemoteSource";

    private static CategoryBeanRemoteSource INSTANCE;

    private IDataApi dataApi;

    private CategoryBeanRemoteSource() {
        dataApi = RetrofitHelper.getInstance().get().create(IDataApi.class);
    }

    public static CategoryBeanRemoteSource getInstance() {
        if (INSTANCE == null) {
            synchronized (CategoryBeanRemoteSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new CategoryBeanRemoteSource();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void getCategory(String key, final GetCategoryCallBack categoryCallBack) {
        dataApi.getCategory(key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        categoryCallBack.onDisposable(d);
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String bodyStr = responseBody.string();
                            JsonParser parser = new JsonParser();
                            JsonElement element = parser.parse(bodyStr);
                            if (element.isJsonObject()) {
                                CategoryBean categoryBean = GsonUtils.getInstance().get().fromJson(element, CategoryBean.class);
                                if (categoryBean.getRetCode().equals("200")) {
                                    categoryCallBack.onSuccess(categoryBean);
                                } else {
                                    categoryCallBack.onFailed(categoryBean.getMsg(), null);
                                }
                            } else {
                                Log.e(TAG, "getCategory - > onNext -> bodyStr = " + bodyStr);
                                categoryCallBack.onFailed("数据格式错误", null);
                            }

                        } catch (IOException e) {
                            categoryCallBack.onFailed(null, e);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        categoryCallBack.onFailed(null, e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
