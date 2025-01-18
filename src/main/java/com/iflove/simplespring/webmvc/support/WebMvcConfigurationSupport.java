package com.iflove.simplespring.webmvc.support;

import com.iflove.simplespring.webmvc.HandlerAdapter;
import com.iflove.simplespring.webmvc.HandlerMapping;
import com.iflove.simplespring.webmvc.adapter.RequestMappingHandlerMethodAdapter;
import com.iflove.simplespring.webmvc.handler.RequestMappingHandlerMapping;
import com.iflove.simplespring.webmvc.intercpetor.InterceptorRegistry;
import com.iflove.simplespring.webmvc.intercpetor.MappedInterceptor;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * @EnableWebMVC 导入类的抽象类，用于初始化组件，允许拦截器操作
 * author: Yusiheng
 */
public abstract class WebMvcConfigurationSupport {

    /**
     * 扫描拦截器并注册到映射器中
     */
    @Bean
    public HandlerMapping handlerMapping() {
        final RequestMappingHandlerMapping requestMappingHandlerMapping = new RequestMappingHandlerMapping();
        requestMappingHandlerMapping.setOrder(0);
        InterceptorRegistry registry = new InterceptorRegistry();
        // 注册中心扫描并获取拦截器
        getIntercept(registry);
        // 获取拦截器
        final List<MappedInterceptor> interceptors = registry.getInterceptors();
        //添加拦截器
        requestMappingHandlerMapping.addInterceptors(interceptors);
        return requestMappingHandlerMapping;
    }

    protected abstract void getIntercept(InterceptorRegistry registry);

    /**
     * 初始化适配器
     */
    @Bean
    public HandlerAdapter handlerMethodAdapter() {
        final RequestMappingHandlerMethodAdapter requestMappingHandlerMethodAdapter = new RequestMappingHandlerMethodAdapter();
        requestMappingHandlerMethodAdapter.setOrder(0);
        return requestMappingHandlerMethodAdapter;
    }

}

