package com.shichen.architecture.basic;

import android.content.Context;
import android.os.Bundle;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle2.components.support.RxFragment;

/**
 * @author shichen 754314442@qq.com
 * Created by Administrator on 2018/9/28.
 */
public interface BaseContract {
    interface View {
        void shortToast(String msg);
        void init();
        void setStatusColor(int color);
        void setSystemInvadeBlack(boolean black);
        Context getContext();
        RxAppCompatActivity getRxActivity();
        RxFragment getRxFragment();
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
