package com.shichen.architecture.basic;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.JsonParseException;
import com.shichen.architecture.utils.StatusUtil;
import com.shichen.architecture.widget.LoadingDialog;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author shichen 754314442@qq.com
 * Created by Administrator on 2018/9/28.
 */
public abstract class BaseFragment<V extends BaseContract.View, P extends BaseContract.Presenter<V>> extends Fragment implements BaseContract.View {
    protected P presenter;
    protected Unbinder mUnbinder;
    protected final String TAG = getClass().getSimpleName();


    @CallSuper
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = initPresenter();
        presenter.attachView((V) this);
        checkNotNull(presenter);
        presenter.onPresenterCreated();
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
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        presenter.detachView();
    }

    @Override
    public void showLoading() {
        //清除已经存在的，同样的fragment
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        checkNotNull(ft);
        Fragment prev = getChildFragmentManager().findFragmentByTag(LoadingDialog.class.getSimpleName());
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        LoadingDialog newFragment = LoadingDialog.getInstance();
        newFragment.show(ft, LoadingDialog.class.getSimpleName());
    }

    @Override
    public void disMissLoading() {
        Fragment prev = getChildFragmentManager().findFragmentByTag(LoadingDialog.class.getSimpleName());
        if (prev != null) {
            LoadingDialog.getInstance().dismiss();
        }
    }

    @Override
    public void shortToast(String msg) {
        checkNotNull(getActivity());
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void logMsg(String TAG, String msg, Throwable e) {
        Log.e(TAG, msg, e);
    }

    @Override
    public void logMsg(String TAG, String msg) {
        Log.e(TAG, msg);
    }

    @Override
    public void setStatusColor(int color) {
        StatusUtil.setUseStatusBarColor(getActivity(), color);
    }

    @Override
    public void setImmerse(boolean immerse, boolean black) {
        StatusUtil.setSystemStatus(getActivity(), immerse, black);
    }

    private List<Disposable> mDisposables = new ArrayList<>();

    @Override
    public void subscribe(Disposable d) {
        mDisposables.add(d);
    }

    @Override
    public void unSubscribe() {
        if (mDisposables.size() > 0) {
            for (int i = 0; i < mDisposables.size(); i++) {
                if (mDisposables.get(i) != null) {
                    if (!mDisposables.get(i).isDisposed()) {
                        mDisposables.get(i).dispose();
                    }
                }
            }
        }
    }

    @Override
    public void checkPermissions(String... permissions) {

    }

    @Override
    public void allPermissionsOk(String[] permissions) {

    }

    @Override
    public void permissionsDenied(List<String> deniedPermissions) {

    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getClass().getName());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getClass().getName());
    }

    @Override
    public void onFailed(@Nullable String msg, @Nullable Throwable e) {
        if (!TextUtils.isEmpty(msg)) {
            shortToast(msg);
        } else {
            if (e != null) {
                if (e instanceof SocketTimeoutException) {
                    shortToast("网络链接超时");
                } else if (e instanceof UnknownHostException){
                    shortToast("未知服务");
                }else {
                    shortToast("其他错误");
                }
            }
        }
    }
}
