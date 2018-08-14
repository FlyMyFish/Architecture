package com.shichen.architecture.basic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author shichen 754314442@qq.com
 * MVP-模式 所有Activity需继承该父类，以便使用[友盟统计]
 * Created by Administrator on 2018/8/9.
 */
public abstract class BaseActivity<V extends IBaseView,P extends BasePresenter<V>> extends RxAppCompatActivity {
    protected Unbinder unbinder;
    protected P mPresenter;

    /**
     * 子类实现该方法返回Activity的布局resId
     * @return resId
     */
    protected abstract int layout();

    /**
     * 子类实现该方法执行页面数据的初始化
     */
    protected abstract void init();

    /**
     * 子类实现该方法返回Activity对应的Presenter逻辑处理类
     * @return presenter
     */
    protected abstract P createPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout());
        //初始化ButterKnife
        unbinder= ButterKnife.bind(this);
        //创建presenter
        mPresenter=createPresenter();
        mPresenter.attachView((V)this);
        //初始化页面
        init();
    }

    public void shortToast(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }
    public void longToast(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        mPresenter.detachView();
    }
}
