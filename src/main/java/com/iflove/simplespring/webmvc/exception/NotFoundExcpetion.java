package com.iflove.simplespring.webmvc.exception;

/**
 * 没找到映射路径对应的方法异常
 */
public class NotFoundExcpetion extends Exception{

    public NotFoundExcpetion(String message) {
        super(message);
    }
}
