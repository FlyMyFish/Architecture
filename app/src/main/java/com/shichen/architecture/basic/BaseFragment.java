package com.shichen.architecture.basic;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
public abstract class BaseFragment<V extends BaseContract.View, P extends BaseContract.Presenter<V>> extends RxFragment implements BaseContract.View {
    protected P presenter;
    protected Unbinder mUnbinder;
    protected final String TAG=getClass().getSimpleName();

    @SuppressWarnings("unchecked")
    @CallSuper
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = initPresenter();
        presenter.attachView((V) this);
        checkNotNull(presenter);
        presenter.onPresenterCreated();
        setStatusColor(Color.WHITE);
        setSystemInvadeBlack(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //使用注解的方式，添加布局以及presenter
        int layoutResId = getClass().getAnnotation(Viewable.class).layout();
        if (layoutResId != Viewable.LAYOUT_NOT_DEFINED) {
            View view = inflater.inflate(layoutResId, container, false);
            mUnbinder = ButterKnife.bind(this, view);
            return view;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    protected P initPresenter() {
        return (P) AnnotationHelper.createPresenter(getClass());
    }

    @CallSuper
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
        presenter.detachView();
    }

    @Override
    public void shortToast(String msg) {
        checkNotNull(getActivity());
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public RxAppCompatActivity getRxActivity() {
        return null;
    }

    @Override
    public RxFragment getRxFragment() {
        return this;
    }

    @Override
    public void setStatusColor(int color) {
        StatusUtil.setUseStatusBarColor(getActivity(), color);
    }

    @Override
    public void setSystemInvadeBlack(boolean black) {
        // 第二个参数是是否沉浸,第三个参数是状态栏字体是否为黑色。
        StatusUtil.setSystemStatus(getActivity(), false, black);
    }
}
