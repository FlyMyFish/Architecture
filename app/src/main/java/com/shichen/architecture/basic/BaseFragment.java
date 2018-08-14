package com.shichen.architecture.basic;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author shichen 754314442@qq.com
 * MVP-模式 所有Fragment需继承该父类，以便使用[友盟统计]
 * 使用注解@ViewResId(layout = R.layout.activity_login_with_pwd)为容器添加布局
 * Created by Administrator on 2018/8/9.
 */
public abstract class BaseFragment<V extends IBaseView, P extends BasePresenter<V>> extends RxFragment implements IBaseView{
    protected Unbinder unbinder;
    protected View rootView;
    protected P mPresenter;

    /**
     * 子类实现该方法执行页面数据的初始化
     */
    protected abstract void init();

    /**
     * 子类实现该方法返回Activity对应的Presenter逻辑处理类
     *
     * @return presenter
     */
    protected abstract P createPresenter();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layoutResId = getClass().getAnnotation(ViewResId.class).layout();
        if (layoutResId != ViewResId.LAYOUT_NOT_DEFINED) {
            rootView = inflater.inflate(layoutResId, container, false);
        }
        //初始化ButterKnife
        unbinder = ButterKnife.bind(this, rootView);
        //创建presenter
        mPresenter = createPresenter();
        mPresenter.attachView((V) this);
        //初始化页面
        init();
        return rootView;
    }

    public void shortToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public void longToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        mPresenter.detachView();
    }
}
