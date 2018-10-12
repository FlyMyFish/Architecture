package com.shichen.architecture.sport;

import com.shichen.architecture.R;
import com.shichen.architecture.basic.BaseActivity;
import com.shichen.architecture.basic.Viewable;

/**
 * @author shichen 754314442@qq.com
 * Created by Administrator on 2018/10/12.
 */
@Viewable(presenter = SportActivityPresenter.class,layout = R.layout.activity_sport)
public class SportActivity extends BaseActivity<SportContract.View,SportActivityPresenter> implements SportContract.View{
    @Override
    public void init() {

    }
}
