package com.shichen.architecture.basic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

/**
 * @author shichen 754314442@qq.com
 * Created by Administrator on 2018/11/16.
 */
public class NetWorkStateReceiver extends BroadcastReceiver {
    private final String TAG = "NetWorkStateReceiver";

    private OnNetWorkChangedListener mOnNetWorkChangedListener;

    public NetWorkStateReceiver() {
    }

    public NetWorkStateReceiver(OnNetWorkChangedListener mOnNetWorkChangedListener) {
        this.mOnNetWorkChangedListener = mOnNetWorkChangedListener;
    }

    public interface OnNetWorkChangedListener {
        void onNetWorkUsable();

        void onNetWorkUnusable();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //检测API是不是小于23，因为到了API23之后getNetworkInfo(int networkType)方法被弃用
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
            //获得ConnectivityManager对象
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connMgr != null) {
                //获取ConnectivityManager对象对应的NetworkInfo对象
                //获取WIFI连接的信息
                NetworkInfo wifiNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                //获取移动数据连接的信息
                NetworkInfo dataNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                if (wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
                    if (mOnNetWorkChangedListener != null) {
                        mOnNetWorkChangedListener.onNetWorkUsable();
                    }
                } else if (wifiNetworkInfo.isConnected() && !dataNetworkInfo.isConnected()) {
                    if (mOnNetWorkChangedListener != null) {
                        mOnNetWorkChangedListener.onNetWorkUsable();
                    }
                } else if (!wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
                    if (mOnNetWorkChangedListener != null) {
                        mOnNetWorkChangedListener.onNetWorkUsable();
                    }
                } else {
                    if (mOnNetWorkChangedListener != null) {
                        mOnNetWorkChangedListener.onNetWorkUnusable();
                    }
                }
            }
            //API大于23时使用下面的方式进行网络监听
        } else {
            //获得ConnectivityManager对象
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connMgr != null) {
                //获取所有网络连接的信息
                Network[] networks = connMgr.getAllNetworks();
                //通过循环将网络信息逐个取出来
                boolean isNetConnected = false;
                for (int i = 0; i < networks.length; i++) {
                    //获取ConnectivityManager对象对应的NetworkInfo对象
                    NetworkInfo networkInfo = connMgr.getNetworkInfo(networks[i]);
                    if (networkInfo.isConnected()) {
                        isNetConnected = true;
                        break;
                    }
                }
                if (isNetConnected) {
                    if (mOnNetWorkChangedListener != null) {
                        mOnNetWorkChangedListener.onNetWorkUsable();
                    }
                } else {
                    if (mOnNetWorkChangedListener != null) {
                        mOnNetWorkChangedListener.onNetWorkUnusable();
                    }
                }
            }
        }
    }
}
