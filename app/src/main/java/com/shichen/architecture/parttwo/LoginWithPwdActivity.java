package com.shichen.architecture.parttwo;

import com.shichen.architecture.basic.BaseActivity;
import com.shichen.architecture.parttwo.iview.ILoginWithPwdView;
import com.shichen.architecture.parttwo.presenter.LoginWithPwdPresenter;

/**
 * @author shichen 754314442@qq.com
 * 账号密码登录页面
 * Created by Administrator on 2018/8/10.
 */
public class LoginWithPwdActivity extends BaseActivity<ILoginWithPwdView,LoginWithPwdPresenter> implements ILoginWithPwdView {
    @Override
    protected int layout() {
        return 0;
    }

    @Override
    protected void init() {

    }

    @Override
    protected LoginWithPwdPresenter createPresenter() {
        return null;
    }
}
