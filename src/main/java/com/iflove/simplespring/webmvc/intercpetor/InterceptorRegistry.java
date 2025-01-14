package com.iflove.simplespring.webmvc.intercpetor;

import com.iflove.simplespring.webmvc.HandlerInterceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 拦截器注册中心
 */
public class InterceptorRegistry {

    private List<InterceptorRegistration> interceptorRegistrations = new ArrayList<>();

    /**
     * 添加拦截器方法，该设计可以进行链式调用，方便了用户的使用
     */
    public InterceptorRegistration addInterceptor(HandlerInterceptor interceptor) {
        final InterceptorRegistration interceptorRegistration = new InterceptorRegistration();
        interceptorRegistration.setInterceptor(interceptor);
        interceptorRegistrations.add(interceptorRegistration);
        return interceptorRegistration;
    }

    /**
     * 将拦截器，转换成路径映射匹配的拦截器
     */
    public List<MappedInterceptor> getInterceptors() {
        List<MappedInterceptor> mappedInterceptors = this.interceptorRegistrations.stream().map(MappedInterceptor::new).collect(Collectors.toList());

        return mappedInterceptors;
    }
}
