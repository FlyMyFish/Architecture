package com.shichen.architecture.partfour;

import com.shichen.architecture.basic.BaseActivity;
import com.shichen.architecture.partfour.iview.ISignOrderListView;
import com.shichen.architecture.partfour.presenter.SignOrderListPresenter;

/**
 * @author shichen 754314442@qq.com
 * 签约订单列表页
 * Created by Administrator on 2018/8/10.
 */
public class SignOrderListActivity extends BaseActivity<ISignOrderListView,SignOrderListPresenter> {
    @Override
    protected int layout() {
        return 0;
    }

    @Override
    protected void init() {

    }

    @Override
    protected SignOrderListPresenter createPresenter() {
        return new SignOrderListPresenter();
    }
}
