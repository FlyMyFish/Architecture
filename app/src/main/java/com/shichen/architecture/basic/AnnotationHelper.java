package com.shichen.architecture.basic;

/**
 * Created by Chatikyan on 29.06.2017.
 * 注解辅助类，通过注解创建presenter
 */

class AnnotationHelper {

    static BaseContract.Presenter createPresenter(Class<?> annotatedClass) {
        try {
            return annotatedClass.getAnnotation(Viewable.class).presenter().newInstance();
        } catch (InstantiationException e) {
            throw new MvpException("Cannot create an instance of " + annotatedClass, e);
        } catch (IllegalAccessException e) {
            throw new MvpException("Cannot create an instance of " + annotatedClass, e);
        }
    }
}
