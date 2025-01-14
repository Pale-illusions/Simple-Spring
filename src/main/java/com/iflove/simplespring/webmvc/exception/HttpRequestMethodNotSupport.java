package com.iflove.simplespring.webmvc.exception;

/**
 * 请求不支持异常
 */
public class HttpRequestMethodNotSupport extends Exception{

    public HttpRequestMethodNotSupport(String message) {
        super(message);
    }
}
