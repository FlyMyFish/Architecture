package com.shichen.architecture.sport;

import android.Manifest;
import android.widget.TextView;

import com.shichen.architecture.R;
import com.shichen.architecture.basic.BaseActivity;
import com.shichen.architecture.basic.Viewable;

import java.util.List;

import butterknife.BindView;

/**
 * @author shichen 754314442@qq.com
 * Created by Administrator on 2018/10/12.
 */
@Viewable(presenter = SportActivityPresenter.class, layout = R.layout.activity_sport, needPermissions = {Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
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

    @Override
    public void allPermissionsOk(String[] permissions) {
        super.allPermissionsOk(permissions);
        //如果顺利获取到了所有的权限，你的逻辑
    }

    @Override
    public void permissionsDenied(List<String> deniedPermissions) {
        super.permissionsDenied(deniedPermissions);
        //如果一些权限或者所有权限被禁止，你的逻辑
    }
}
