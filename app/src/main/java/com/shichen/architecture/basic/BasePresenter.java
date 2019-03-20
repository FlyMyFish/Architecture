package com.shichen.architecture.basic;

import android.os.Bundle;
import android.support.annotation.CallSuper;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * @author shichen 754314442@qq.com
 * Created by Administrator on 2018/9/28.
 */
public abstract class BasePresenter<V extends BaseContract.View> implements BaseContract.Presenter<V> {
    private Bundle stateBundle;
    protected int STATUS_OK=1;
    protected V view;

    @Override
    public Bundle getStateBundle() {
        return stateBundle == null
                ? stateBundle = new Bundle()
                : stateBundle;
    }

    @Override
    final public void attachView(V view) {
        this.view = view;
    }

    @Override
    final public void detachView() {
        if (view != null) {
            view = null;
        }
    }

    @Override
    final public V getView() {
        return view;
    }

    @Override
    final public boolean isViewAttached() {
        return view != null;
    }

    @CallSuper
    @Override
    public void onPresenterDestroy() {
        if (stateBundle != null && !stateBundle.isEmpty()) {
            stateBundle.clear();
        }
    }

    @Override
    public void onPresenterCreated() {
        if (isViewAttached()) {
            view.init();
        }
    }
}
