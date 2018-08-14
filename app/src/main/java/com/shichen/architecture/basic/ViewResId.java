package com.shichen.architecture.basic;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author shichen 754314442@qq.com
 * 使用注解为Activity-Fragment添加布局
 * Created by Administrator on 2018/8/14.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewResId {
    int LAYOUT_NOT_DEFINED = -1;

    int layout() default LAYOUT_NOT_DEFINED;
}
