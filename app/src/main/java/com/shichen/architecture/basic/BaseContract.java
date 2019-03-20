package com.shichen.architecture.basic;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * @author shichen 754314442@qq.com
 * Created by Administrator on 2018/9/28.
 */
public interface BaseContract {
    interface View {
        void showLoading();

        void disMissLoading();

        void shortToast(String msg);

        void logMsg(String TAG, String msg, Throwable e);

        void logMsg(String TAG, String msg);

        void init();

        void setStatusColor(int color);

        Context getContext();

        void subscribe(Disposable d);

        void unSubscribe();

        void checkPermissions(String... permissions);

        void allPermissionsOk(String[] permissions);

        void permissionsDenied(List<String> deniedPermissions);

        void setImmerse(boolean immerse, boolean black);

        void onFailed(@Nullable String msg, @Nullable Throwable e);
    }

    interface Presenter<V extends BaseContract.View> {
        Bundle getStateBundle();

        void attachView(V view);

        void detachView();

        V getView();

        boolean isViewAttached();

        void onPresenterDestroy();

        void onPresenterCreated();
    }
}
