package com.iflove.simplespring.webmvc.intercpetor;

import com.iflove.simplespring.webmvc.HandlerInterceptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 封装拦截器信息类
 */
public class InterceptorRegistration {

    // 拦截器
    private HandlerInterceptor interceptor;
    // 拦截路径
    private List<String> includePatterns = new ArrayList<>();
    // 排除路径
    private List<String> excludePatterns = new ArrayList<>();

    public InterceptorRegistration setInterceptor(HandlerInterceptor interceptor) {
        this.interceptor = interceptor;
        return this;
    }

    public InterceptorRegistration addIncludePatterns(String... path) {
        // 不要直接赋值否则会被覆盖
        this.includePatterns.addAll(Arrays.asList(path));
        return this;
    }

    public InterceptorRegistration addExcludePatterns(String... path) {

        this.excludePatterns.addAll(Arrays.asList(path));
        return this;
    }

    public HandlerInterceptor getInterceptor() {
        return interceptor;
    }

    public List<String> getIncludePatterns() {
        return includePatterns;
    }

    public List<String> getExcludePatterns() {
        return excludePatterns;
    }
}
