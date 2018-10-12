package com.shichen.architecture.basic;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.shichen.architecture.R;
import com.shichen.architecture.utils.StatusUtil;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author shichen 754314442@qq.com
 * Created by Administrator on 2018/9/28.
 */
public abstract class BaseActivity<V extends BaseContract.View, P extends BaseContract.Presenter<V>> extends RxAppCompatActivity implements BaseContract.View {
    protected P presenter;
    protected Unbinder mUnbinder;
    protected final String TAG=getClass().getSimpleName();

    @SuppressWarnings("unchecked")
    @CallSuper
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //使用注解的方式，添加布局以及presenter
        int layoutResId = getClass().getAnnotation(Viewable.class).layout();
        if (layoutResId != Viewable.LAYOUT_NOT_DEFINED) {
            setContentView(layoutResId);
            mUnbinder= ButterKnife.bind(this);
        }
        setStatusColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        setSystemInvadeBlack(true);
        presenter = initPresenter();
        presenter.attachView((V) this);
        checkNotNull(presenter);
        presenter.onPresenterCreated();
    }

    @CallSuper
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        presenter.detachView();
    }

    @SuppressWarnings("unchecked")
    protected P initPresenter() {
        return (P) AnnotationHelper.createPresenter(getClass());
    }

    @Override
    public void shortToast(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getContext() {
        return getBaseContext();
    }

    @Override
    public RxAppCompatActivity getRxActivity() {
        return this;
    }

    @Override
    public RxFragment getRxFragment() {
        return null;
    }

    @Override
    public void setStatusColor(int color) {
        StatusUtil.setUseStatusBarColor(this, color);
    }

    @Override
    public void setSystemInvadeBlack(boolean black) {
        // 第二个参数是是否沉浸,第三个参数是状态栏字体是否为黑色。
        StatusUtil.setSystemStatus(this, false, black);
    }
}
