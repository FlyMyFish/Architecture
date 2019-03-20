package com.shichen.architecture.data.source;

import com.shichen.architecture.data.CategoryBean;

import io.reactivex.disposables.Disposable;

public interface ICategoryBeanSource {
    interface GetCategoryCallBack{
        void onSuccess(CategoryBean response);

        void onFailed(String msg,Throwable e);

        void onDisposable(Disposable disposable);
    }

    void getCategory(String key,GetCategoryCallBack categoryCallBack);
}
