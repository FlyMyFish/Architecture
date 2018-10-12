package com.shichen.architecture.basic;

/**
 * @author shichen 754314442@qq.com
 * Created by Administrator on 2018/9/28.
 */
public class MvpException extends RuntimeException {

    MvpException(String message) {
        super(message);
    }

    MvpException(String message, Throwable cause) {
        super(message, cause);
    }

    MvpException(Throwable cause) {
        super(cause);
    }
}
