package com.shichen.architecture.basic;

/**
 * @author shichen 754314442@qq.com
 * 通过一个单例创建广播
 * Created by Administrator on 2018/11/16.
 */
public class NetWorkChangedFactory {

    private static NetWorkChangedFactory INSTANCE;
    private NetWorkStateReceiver mNetWorkStateReceiver;

    private NetWorkChangedFactory(NetWorkStateReceiver.OnNetWorkChangedListener onNetWorkChangedListener) {
        mNetWorkStateReceiver = new NetWorkStateReceiver(onNetWorkChangedListener);
    }

    public static NetWorkChangedFactory getInstance(NetWorkStateReceiver.OnNetWorkChangedListener onNetWorkChangedListener) {
        if (INSTANCE == null) {
            synchronized (NetWorkChangedFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new NetWorkChangedFactory(onNetWorkChangedListener);
                }
            }
        }
        return INSTANCE;
    }

    public NetWorkStateReceiver getReceiver() {
        return mNetWorkStateReceiver;
    }
}
