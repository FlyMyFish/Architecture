package com.shichen.architecture.partthree;

import com.shichen.architecture.basic.BaseActivity;
import com.shichen.architecture.partthree.iview.IOrderListView;
import com.shichen.architecture.partthree.presenter.OrderListPresenter;

/**
 * @author shichen 754314442@qq.com
 * 订单列表页
 * Created by Administrator on 2018/8/10.
 */
public class OrderListActivity extends BaseActivity<IOrderListView,OrderListPresenter> {
    @Override
    protected int layout() {
        return 0;
    }

    @Override
    protected void init() {

    }

    @Override
    protected OrderListPresenter createPresenter() {
        return null;
    }
}
