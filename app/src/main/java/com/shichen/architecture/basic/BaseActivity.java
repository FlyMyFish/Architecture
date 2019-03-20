package com.shichen.architecture.basic;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonParseException;
import com.shichen.architecture.R;
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
public abstract class BaseActivity<V extends BaseContract.View, P extends BaseContract.Presenter<V>> extends AppCompatActivity implements BaseContract.View {
    protected P presenter;
    protected Unbinder mUnbinder;
    protected final String TAG = getClass().getSimpleName();
    private NetWorkStateReceiver mNetWorkStateReceiver;
    private boolean isRegistered = false;


    @SuppressWarnings("unchecked")
    @CallSuper
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        //使用注解的方式，添加布局以及presenter
        int layoutResId = getClass().getAnnotation(Viewable.class).layout();
        if (layoutResId != Viewable.LAYOUT_NOT_DEFINED) {
            setContentView(layoutResId);
            mUnbinder = ButterKnife.bind(this);
        }
        presenter = initPresenter();
        presenter.attachView((V) this);
        checkNotNull(presenter);
        presenter.onPresenterCreated();
        registerNewWorkStateReceiver();

    }

    private boolean netWorkUnusableShowed = false;

    private void registerNewWorkStateReceiver() {
        mNetWorkStateReceiver = NetWorkChangedFactory.getInstance(new NetWorkStateReceiver.OnNetWorkChangedListener() {
            @Override
            public void onNetWorkUsable() {
                if (netWorkUnusableShowed) {
                    //shortToast("网络已正常连接");
                    //TODO 监听手机联网状态 此处可以做一些恢复联网之后的逻辑
                    netWorkUnusableShowed = false;
                }
            }

            @Override
            public void onNetWorkUnusable() {
                if (!netWorkUnusableShowed) {
                    //shortToast("网络不可用");
                    //TODO 监听手机联网状态 此处可以做一些断网之后的逻辑
                    netWorkUnusableShowed = true;
                }
            }
        }).getReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mNetWorkStateReceiver, filter);
        isRegistered = true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isNeedCheck) {
            //通过注解反射得到该页面需要的权限
            String[] needPermissions = getClass().getAnnotation(Viewable.class).needPermissions();
            if (needPermissions.length > 0) {
                checkPermissions(needPermissions);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @CallSuper
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        presenter.detachView();
        //解绑
        if (isRegistered) {
            unregisterReceiver(mNetWorkStateReceiver);
        }
        //页面暂停时就取消所有订阅
        unSubscribe();
    }

    @SuppressWarnings("unchecked")
    protected P initPresenter() {
        return (P) AnnotationHelper.createPresenter(getClass());
    }

    @Override
    public void showLoading() {
        //清除已经存在的，同样的fragment
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag(LoadingDialog.class.getSimpleName());
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
        Fragment prev = getSupportFragmentManager().findFragmentByTag(LoadingDialog.class.getSimpleName());
        if (prev != null) {
            LoadingDialog.getInstance().dismiss();
        }
    }

    @Override
    public void shortToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
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
    public Context getContext() {
        return getBaseContext();
    }

    @Override
    public void setStatusColor(int color) {
        StatusUtil.setUseStatusBarColor(this, color);
    }

    @Override
    public void setImmerse(boolean immerse, boolean black) {
        // 第二个参数是是否沉浸,第三个参数是状态栏字体是否为黑色。
        StatusUtil.setSystemStatus(this, immerse, black);
    }

    private List<Disposable> mDisposables = new ArrayList<>();

    @Override
    public void subscribe(Disposable d) {
        mDisposables.add(d);
    }

    /**
     * 取消所有订阅
     */
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

    private static final int PERMISSON_REQUESTCODE = 0;

    /**
     * 判断是否需要检测，防止不停的弹框
     */
    private boolean isNeedCheck = true;

    @Override
    public void checkPermissions(String... permissions) {
        List<String> needRequestPermissonList = findDeniedPermissions(permissions);
        if (null != needRequestPermissonList
                && needRequestPermissonList.size() > 0) {
            ActivityCompat.requestPermissions(this,
                    needRequestPermissonList.toArray(
                            new String[needRequestPermissonList.size()]),
                    PERMISSON_REQUESTCODE);
        } else {
            allPermissionsOk(permissions);
        }
    }

    /**
     * 获取权限集中需要申请权限的列表
     *
     * @param permissions
     * @return
     * @since 2.5.0
     */
    private List<String> findDeniedPermissions(String... permissions) {
        List<String> needRequestPermissionList = new ArrayList<String>();
        for (String perm : permissions) {
            if (ContextCompat.checkSelfPermission(this,
                    perm) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    this, perm)) {
                needRequestPermissionList.add(perm);
            }
        }
        return needRequestPermissionList;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSON_REQUESTCODE) {
            if (!verifyPermissions(grantResults)) {
                permissionsDenied(deniedPermissions(permissions, grantResults));
                isNeedCheck = false;
            } else {
                allPermissionsOk(permissions);
            }
        }
    }

    /**
     * 检测是否说有的权限都已经授权
     *
     * @param grantResults 授权结果
     * @return
     * @since 2.5.0
     */
    private boolean verifyPermissions(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private List<String> deniedPermissions(@NonNull String[] permissions, @NonNull int[] grantResults) {
        List<String> deniedPermissionList = new ArrayList<>();
        for (int index = 0; index < grantResults.length; index++) {
            if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
                deniedPermissionList.add(permissions[index]);
            }
        }
        return deniedPermissionList;
    }

    @Override
    public void allPermissionsOk(String[] permissions) {
        Log.e(TAG, "allPermissionsOk");
    }

    @Override
    public void permissionsDenied(List<String> deniedPermissions) {
        Log.e(TAG, "permissionsDenied");
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onFailed(@Nullable String msg, @Nullable Throwable e) {
        if (!TextUtils.isEmpty(msg)) {
            shortToast(msg);
        } else {
            if (e != null) {
                if (e instanceof SocketTimeoutException) {
                    shortToast("网络链接超时");
                } else if (e instanceof UnknownHostException) {
                    shortToast("未知服务");
                } else {
                    shortToast("其他错误");
                }
            }
        }
    }
}
