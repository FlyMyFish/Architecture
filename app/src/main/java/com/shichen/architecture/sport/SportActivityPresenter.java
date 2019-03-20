package com.shichen.architecture.sport;

import com.shichen.architecture.BuildConfig;
import com.shichen.architecture.basic.BasePresenter;
import com.shichen.architecture.data.CategoryBean;
import com.shichen.architecture.data.source.CategoryBeanRepository;
import com.shichen.architecture.data.source.ICategoryBeanSource;
import com.shichen.architecture.data.source.remote.CategoryBeanRemoteSource;

import io.reactivex.disposables.Disposable;

/**
 * @author shichen 754314442@qq.com
 * Created by Administrator on 2018/10/12.
 */
public class SportActivityPresenter extends BasePresenter<SportContract.View> implements SportContract.Presenter{
    @Override
    public void start() {
        CategoryBeanRepository.getInstance(CategoryBeanRemoteSource.getInstance())
                .getCategory(BuildConfig.APPKEY, new ICategoryBeanSource.GetCategoryCallBack() {
                    @Override
                    public void onSuccess(CategoryBean response) {
                        view.setCategory(response.toString());
                    }

                    @Override
                    public void onFailed(String msg, Throwable e) {
                        view.onFailed(msg,e);
                    }

                    @Override
                    public void onDisposable(Disposable disposable) {
                        view.subscribe(disposable);
                    }
                });
    }
}
