package com.shichen.architecture.basic;

import android.Manifest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Chatikyan on 29.06.2017.
 * 自定义注解，辅助activity，fragment设置布局以及presenter
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Viewable {
    int LAYOUT_NOT_DEFINED = -1;
    //使用到了友盟，默认必须获得此权限
    String DEFAULT_PERMISSIONS = Manifest.permission.READ_PHONE_STATE;

    Class<? extends BasePresenter> presenter();

    int layout() default LAYOUT_NOT_DEFINED;

    String[] needPermissions() default {DEFAULT_PERMISSIONS};
}
