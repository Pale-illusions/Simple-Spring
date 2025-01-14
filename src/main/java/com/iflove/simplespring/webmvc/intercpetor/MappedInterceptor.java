package com.iflove.simplespring.webmvc.intercpetor;

import com.iflove.simplespring.webmvc.HandlerInterceptor;
import com.iflove.simplespring.webmvc.handler.HandlerMethod;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 路径拦截器，可以拦截对应路径下的方法
 */
public class MappedInterceptor implements HandlerInterceptor {

    // 拦截器
    private HandlerInterceptor interceptor;
    // 拦截路径
    private List<String> includePatterns = new ArrayList<>();
    // 排除路径
    private List<String> excludePatterns = new ArrayList<>();

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    // 路径匹配
    public boolean match(String path) {
        for (String excludePattern : this.excludePatterns) {
            if (antPathMatcher.match(excludePattern, path)) {
                return false;
            }
        }
        for (String includePattern : this.includePatterns) {
            if (antPathMatcher.match(includePattern, path)) {
                return true;
            }
        }

        return false;
    }

    public MappedInterceptor(InterceptorRegistration interceptorRegistration) {
        this.interceptor = interceptorRegistration.getInterceptor();
        this.includePatterns = interceptorRegistration.getIncludePatterns();
        this.excludePatterns = interceptorRegistration.getExcludePatterns();

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response) {
        return interceptor.preHandle(request, response);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response) {
        interceptor.postHandle(request, response);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler, Exception ex) {
        interceptor.afterCompletion(request, response, handler, ex);
    }
}
