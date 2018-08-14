package com.shichen.architecture.partone;

import com.shichen.architecture.basic.BaseActivity;
import com.shichen.architecture.basic.ViewResId;
import com.shichen.architecture.partone.iview.IIntentionalCustomersView;
import com.shichen.architecture.partone.presenter.IntentionalCustomersPresenter;

/**
 * @author shichen 754314442@qq.com
 * 意向客户页面
 * Created by Administrator on 2018/8/10.
 */
@ViewResId(layout = 0)
public class IntentionalCustomersActivity extends BaseActivity<IIntentionalCustomersView,IntentionalCustomersPresenter> {

    @Override
    protected void init() {

    }

    @Override
    protected IntentionalCustomersPresenter createPresenter() {
        return null;
    }
}
