package com.shichen.architecture.basic;

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

    Class<? extends BasePresenter> presenter();

    int layout() default LAYOUT_NOT_DEFINED;
}
