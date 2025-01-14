package com.iflove.simplespring.webmvc.adapter;

import com.iflove.simplespring.webmvc.HandlerAdapter;
import com.iflove.simplespring.webmvc.handler.HandlerMethod;
import com.iflove.simplespring.webmvc.handler.ServletInvocableHandlerMethod;
import com.iflove.simplespring.webmvc.metod.annotation.*;
import com.iflove.simplespring.webmvc.metod.support.HandlerMethodArgumentResolver;
import com.iflove.simplespring.webmvc.metod.support.HandlerMethodReturnValueHandler;
import com.iflove.simplespring.webmvc.support.WebServletRequest;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 具体的适配器实现类
 */
public class RequestMappingHandlerMethodAdapter extends ApplicationObjectSupport implements HandlerAdapter, InitializingBean {

    // 参数解析器组合器
    private HandlerMethodArgumentResolverComposite resolverComposite = new HandlerMethodArgumentResolverComposite();

    // 类型转换
    private ConversionService conversionService = new DefaultConversionService();

    // 返回值处理器组合器
    private HandlerMethodReturnValueHandlerComposite returnValueHandlerComposite = new HandlerMethodReturnValueHandlerComposite();

    private int order;

    /**
     * 是否支持
     */
    @Override
    public boolean support(HandlerMethod handlerMethod) {
        return AnnotatedElementUtils.hasAnnotation(handlerMethod.getMethod(), RequestMapping.class);
    }

    /**
     * 处理请求，执行方法就在这内部
     */
    @Override
    public void handle(HttpServletRequest req, HttpServletResponse res, HandlerMethod handler) throws Exception {
        final WebServletRequest webServletRequest = new WebServletRequest(req, res);
        // 需要将 HandlerMethod执行，创建一个类来包装
        final ServletInvocableHandlerMethod invocableMethod = new ServletInvocableHandlerMethod();
        // 获取到初始化的组件
        invocableMethod.setHandlerMethod(handler);
        invocableMethod.setConversionService(conversionService);
        invocableMethod.setResolverComposite(resolverComposite);
        invocableMethod.setReturnValueHandlerComposite(returnValueHandlerComposite);
        // 执行方法逻辑
        invocableMethod.invokeAndHandle(webServletRequest, handler);
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public int getOrder() {
        return this.order;
    }

    /**
     * 初始化基础组件
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        resolverComposite.addResolvers(getDefaultArgumentResolver());
        returnValueHandlerComposite.addMethodReturnValueHandlers(getDefaultMethodReturnValueHandler());
    }

    /**
     * 初始化返回值处理器
     */
    public List<HandlerMethodReturnValueHandler> getDefaultMethodReturnValueHandler() {
        final ArrayList<HandlerMethodReturnValueHandler> handlerMethodReturnValueHandlers = new ArrayList<>();
        handlerMethodReturnValueHandlers.add(new RequestResponseBodyMethodReturnValueHandler());
        return handlerMethodReturnValueHandlers;

    }

    /**
     * 初始化参数解析器
     */
    public List<HandlerMethodArgumentResolver> getDefaultArgumentResolver() {
        final List<HandlerMethodArgumentResolver> resolvers = new ArrayList<>();
        resolvers.add(new PathVariableMethodArgumentResolver());
        resolvers.add(new PathVariableMapMethodArgumentResolver());
        resolvers.add(new RequestCookieMethodArgumentResolver());
        resolvers.add(new RequestHeaderMethodArgumentResolver());
        resolvers.add(new RequestHeaderMapMethodArgumentResolver());
        resolvers.add(new RequestPartMethodArgumentResolver());
        resolvers.add(new RequestParamMethodArgumentResolver());
        resolvers.add(new RequestParamMapMethodArgumentResolver());
        resolvers.add(new RequestRequestBodyMethodArgumentResolver());
        resolvers.add(new ServletRequestMethodArgumentResolver());
        resolvers.add(new ServletResponseMethodArgumentResolver());
        return resolvers;
    }
}
