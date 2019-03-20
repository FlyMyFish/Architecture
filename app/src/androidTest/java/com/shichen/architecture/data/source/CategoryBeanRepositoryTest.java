package com.shichen.architecture.data.source;

import android.util.Log;

import com.shichen.architecture.BuildConfig;
import com.shichen.architecture.data.CategoryBean;
import com.shichen.architecture.data.source.remote.CategoryBeanRemoteSource;

import org.junit.Before;
import org.junit.Test;

import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.junit.Assert.*;

public class CategoryBeanRepositoryTest {

    private final String TAG = "CategoryRepositoryTest";

    CategoryBeanRemoteSource mCategoryBeanRemoteSource;

    @Before
    public void setUp() throws Exception {
        initRxJava2();
        mCategoryBeanRemoteSource = CategoryBeanRemoteSource.getInstance();
    }

    /**
     * 因为网络请求是异步的，所以我们直接测试是不能直接拿到数据，
     * 因此无法打印出Log以及测试。所以我们使用initRxJava2()方法将异步转化为同步。
     * 这样我们就可以看到返回信息
     */
    private void initRxJava2() {
        RxJavaPlugins.reset();
        RxJavaPlugins.setIoSchedulerHandler(new Function<Scheduler, Scheduler>() {
            @Override
            public Scheduler apply(Scheduler scheduler) throws Exception {
                return Schedulers.trampoline();
            }
        });
        RxAndroidPlugins.reset();
        RxAndroidPlugins.setMainThreadSchedulerHandler(new Function<Scheduler, Scheduler>() {
            @Override
            public Scheduler apply(Scheduler scheduler) throws Exception {
                return Schedulers.trampoline();
            }
        });
    }

    @Test
    public void getCategory() {
        CategoryBeanRepository.getInstance(mCategoryBeanRemoteSource)
                .getCategory(BuildConfig.APPKEY, new ICategoryBeanSource.GetCategoryCallBack() {
                    @Override
                    public void onSuccess(CategoryBean response) {
                        Log.e(TAG, "getCategory - > onSuccess -> response = " + response.toString());
                    }

                    @Override
                    public void onFailed(String msg, Throwable e) {
                        Log.e(TAG, "getCategory - > onFailed -> msg = " + msg, e);
                    }

                    @Override
                    public void onDisposable(Disposable disposable) {
                        Log.e(TAG, "getCategory - > onDisposable ");
                    }
                });
    }
}