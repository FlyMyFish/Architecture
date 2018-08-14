package com.shichen.architecture.basic;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * @author shichen 754314442@qq.com
 * MVP-模式Presenter父类，所有Presenter需继承该类
 * Created by Administrator on 2018/8/10.
 */
public abstract class BasePresenter<V extends IBaseView> {
    protected Reference<V> mViewRef;

    public void attachView(V view) {
        mViewRef = new WeakReference<V>(view);
    }

    protected V getView() {
        return mViewRef.get();
    }

    public boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }
}
