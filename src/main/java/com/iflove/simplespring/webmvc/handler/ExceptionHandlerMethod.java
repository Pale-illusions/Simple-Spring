package com.iflove.simplespring.webmvc.handler;

import java.lang.reflect.Method;

/**
 * 异常处理器方法的封装类
 * author: Yusiheng
 */
public class ExceptionHandlerMethod extends HandlerMethod {

    public ExceptionHandlerMethod(Object bean, Method method) {
        super(bean, method);
    }
}
