package com.iflove.simplespring.webmvc;

import com.iflove.simplespring.webmvc.handler.HandlerMethod;
import com.iflove.simplespring.webmvc.intercpetor.MappedInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

public class HandlerExecutionChain {

    private HandlerMethod handlerMethod;

    private List<HandlerInterceptor> handlerInterceptors = new ArrayList<>();

    public HandlerExecutionChain(HandlerMethod handlerMethod) {
        this.handlerMethod = handlerMethod;
    }

    public HandlerMethod getHandlerMethod() {
        return handlerMethod;
    }

    public List<HandlerInterceptor> getHandlerInterceptors() {
        return handlerInterceptors;
    }

    public void setHandlerInterceptors(List<HandlerInterceptor> handlerInterceptors) {
        // 路径映射匹配
        for (HandlerInterceptor interceptor : handlerInterceptors) {
            if (interceptor instanceof MappedInterceptor) {
                if (((MappedInterceptor) interceptor).match(handlerMethod.getPath())) {
                    this.handlerInterceptors.add(interceptor);
                }

            } else {
                this.handlerInterceptors.add(interceptor);
            }
        }
    }

    // 多个拦截器执行，一旦有一个拦截器返回false，整个链路就可以崩掉
    public boolean applyPreInterceptor(HttpServletRequest req, HttpServletResponse resp) {
        for (HandlerInterceptor handlerInterceptor : this.handlerInterceptors) {
            if (!handlerInterceptor.preHandle(req, resp)) {
                return false;
            }
        }
        return true;
    }

    public void applyPostInterceptor(HttpServletRequest req, HttpServletResponse resp) {
        for (HandlerInterceptor handlerInterceptor : this.handlerInterceptors) {
            handlerInterceptor.postHandle(req, resp);
        }
    }

    public void afterCompletion(HttpServletRequest req, HttpServletResponse resp, HandlerMethod handlerMethod, Exception ex) {
        for (HandlerInterceptor handlerInterceptor : this.handlerInterceptors) {
            handlerInterceptor.afterCompletion(req, resp, handlerMethod, ex);
        }
    }
}
