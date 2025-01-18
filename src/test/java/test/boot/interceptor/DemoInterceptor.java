package test.boot.interceptor;

import com.iflove.simplespring.webmvc.HandlerInterceptor;
import com.iflove.simplespring.webmvc.handler.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

public class DemoInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("这是拦截器前置处理");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("这是拦截器后置处理");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler, Exception ex) {
        System.out.println("请求处理完毕");
    }
}
