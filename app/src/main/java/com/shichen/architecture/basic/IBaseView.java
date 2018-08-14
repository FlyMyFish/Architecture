package com.shichen.architecture.basic;

/**
 * @author shichen 754314442@qq.com
 * MVP-模式页面逻辑控制基类所有页面的IView需继承该父类
 * Created by Administrator on 2018/8/10.
 */
public interface IBaseView {
    void shortToast(String msg);

    void longToast(String msg);


}
