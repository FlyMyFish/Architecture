package com.shichen.architecture.sport;

import com.shichen.architecture.basic.BaseContract;

/**
 * @author shichen 754314442@qq.com
 * Created by Administrator on 2018/10/12.
 */
public interface SportContract {
    interface View extends BaseContract.View{
        void setCategory(String category);
    }

    interface Presenter extends BaseContract.Presenter<SportContract.View>{
        void start();
    }
}
