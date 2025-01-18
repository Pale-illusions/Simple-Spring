package com.iflove.simplespring.webmvc.handler;

import com.iflove.simplespring.beans.factory.InitializingBean;
import com.iflove.simplespring.core.Ordered;
import com.iflove.simplespring.webmvc.HandlerExecutionChain;
import com.iflove.simplespring.webmvc.HandlerInterceptor;
import com.iflove.simplespring.webmvc.HandlerMapping;
import com.iflove.simplespring.webmvc.annotation.RequestMethod;
import com.iflove.simplespring.webmvc.intercpetor.MappedInterceptor;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.http.server.RequestPath;
import org.springframework.lang.Nullable;
import org.springframework.web.context.support.WebApplicationObjectSupport;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.util.ServletRequestPathUtils;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

public abstract class AbstractHandlerMapping extends WebApplicationObjectSupport implements HandlerMapping, InitializingBean {

    private int order = Ordered.LOWEST_PRECEDENCE;  // default: same as non-Ordered

    private final List<HandlerInterceptor> interceptors = new ArrayList<>();

    // 路径映射表，每个路径对应多个 HandlerMethod
    private final Map<String, Set<HandlerMethod>> mappingRegistry = new HashMap<>();

    public void addInterceptor(HandlerInterceptor interceptor) {
        interceptors.add(interceptor);
    }

    public void addInterceptors(List<MappedInterceptor> interceptors) {
        this.interceptors.addAll(interceptors);
    }

    public List<HandlerInterceptor> getInterceptors() {
        return interceptors;
    }

    /**
     * 查找当前请求的最佳匹配处理方法。 如果找到多个匹配项，则选择最佳匹配。
     * @param lookupPath mapping lookup path within the current servlet mapping
     * @param request the current request
     * @return the best-matching handler method, or {@code null} if no match
     */
    @Nullable
    protected HandlerMethod lookupHandlerMethod(String lookupPath, HttpServletRequest request) throws Exception {
        // 查找匹配的处理器集合
        Set<HandlerMethod> candidates = mappingRegistry.get(lookupPath);
        if (candidates == null || candidates.isEmpty()) {
            return null; // 没有匹配项
        }

        // 从候选者中选择最佳匹配的处理器
        return selectBestHandler(candidates, request);
    }

    private HandlerMethod selectBestHandler(Set<HandlerMethod> candidates, HttpServletRequest request) {
        // 遍历候选处理器，根据条件（如请求方法）过滤并选择最佳匹配
        for (HandlerMethod handlerMethod : candidates) {
            for (RequestMethod requestMethod : handlerMethod.getRequestMethods()) {
                // 判断请求方法是否一致
                if (requestMethod.name().equals(request.getMethod())) {
                    return handlerMethod;
                }
            }
        }
        return null;
    }

    public void register(HandlerMethod handlerMethod) {
        // 获取请求路径
        String path = handlerMethod.getPath();
        if (path.contains("{") && path.contains("}")) {
            // 路径替换
            path = path.replaceAll("\\{\\w+}", "(\\\\w+)");
            // 存在，可能请求类型一样
            register(path, handlerMethod);
        } else {
            // 根据请求路径的不同分别保存HandlerMethod
            register(path, handlerMethod);
        }
    }

    public void register(String path, HandlerMethod handlerMethod) {
        // 注册处理器
        mappingRegistry.computeIfAbsent(path, k -> new HashSet<>()).add(handlerMethod);
    }

    /**
     * 获取处理器
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
        HandlerMethod handler = getHandlerInternal(request);
        if (handler == null) {
            return null;
        }
        return getHandlerExecutionChain(handler, request);
    }

    protected abstract HandlerMethod getHandlerInternal(HttpServletRequest request) throws Exception;

    /**
     * 获取处理器执行链
     * @param handler
     * @param request
     * @return
     */
    protected HandlerExecutionChain getHandlerExecutionChain(HandlerMethod handler, HttpServletRequest request) {
        HandlerExecutionChain chain = new HandlerExecutionChain(handler);
        chain.setHandlerInterceptors(interceptors);
        return chain;
    }

    /**
     * 路径解析
     * @param request
     * @return
     */
    protected String initLookupPath(HttpServletRequest request) {
        request.removeAttribute(UrlPathHelper.PATH_ATTRIBUTE);
        RequestPath requestPath = getRequestPath(request);
        String lookupPath = requestPath.pathWithinApplication().value();
        return UrlPathHelper.defaultInstance.removeSemicolonContent(lookupPath);
    }

    private RequestPath getRequestPath(HttpServletRequest request) {
        // Expect pre-parsed path with DispatcherServlet,
        // but otherwise parse per handler lookup + cache for handling
        return (request.getAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE) != null ?
                ServletRequestPathUtils.getParsedRequestPath(request) :
                ServletRequestPathUtils.parseAndCache(request));
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initHandlerMethod();
    }

    /**
     * 初始化处理器方法
     */
    private void initHandlerMethod() throws Exception {
        // from Yusiheng
        //获取所有bean
        final ApplicationContext context = obtainApplicationContext();
        String[] names = BeanFactoryUtils.beanNamesForTypeIncludingAncestors(context, Object.class);
        for (String name : names) {
            //拿到当前class
            Class type = null;
            try {
                type = context.getType(name);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //判断是否是一个handler -> 交给子类
            if (type != null && isHandler(type)) {
                //找到bean当中的HandlerMethod -> 交给子类
                detectHandlerMethod(name);
            }
        }
    }

    protected abstract void detectHandlerMethod(String name) throws Exception;

    protected abstract boolean isHandler(Class type);

}
