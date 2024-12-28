package com.iflove.simplespring.beans;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote Bean相关异常
 */

public class BeansException extends RuntimeException {
    public BeansException(String msg) {
        super(msg);
    }

    public BeansException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
