package com.shichen.architecture.sport;

import android.widget.TextView;

import com.shichen.architecture.R;
import com.shichen.architecture.basic.BaseActivity;
import com.shichen.architecture.basic.Viewable;

import butterknife.BindView;

/**
 * @author shichen 754314442@qq.com
 * Created by Administrator on 2018/10/12.
 */
@Viewable(presenter = SportActivityPresenter.class, layout = R.layout.activity_sport)
public class SportActivity extends BaseActivity<SportContract.View, SportActivityPresenter> implements SportContract.View {
    @BindView(R.id.tv_category)
    TextView tvCategory;

    @Override
    public void init() {
        presenter.start();
    }

    @Override
    public void setCategory(String category) {
        tvCategory.setText(category);
    }
}
