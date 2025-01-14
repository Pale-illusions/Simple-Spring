package com.iflove.simplespring.webmvc;

import com.iflove.simplespring.webmvc.handler.HandlerMethod;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.async.WebAsyncManager;
import org.springframework.web.context.request.async.WebAsyncUtils;
import org.springframework.web.servlet.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

public class DispatcherServlet extends FrameworkServlet {

    public static final String HANDLER_MAPPING_BEAN_NAME = "handlerMapping";

    public static final String HANDLER_ADAPTER_BEAN_NAME = "handlerAdapter";

    public static final String HANDLER_EXCEPTION_RESOLVER_BEAN_NAME = "handlerExceptionResolver";

    /** HandlerMappings 集合 */
    @Nullable
    private List<HandlerMapping> handlerMappings = new ArrayList<>();

    /** HandlerAdapters 集合 */
    @Nullable
    private List<HandlerAdapter> handlerAdapters = new ArrayList<>();


    /**
     * 与 DispatcherServlet 类相关的类路径资源的名称，用于定义 DispatcherServlet 的默认策略名称
     * 注意：路径应保持一致
     */
    private static final String DEFAULT_STRATEGIES_PATH = "DispatcherServlet.properties";

    private static Properties defaultStrategies;

    public static final String WEB_APPLICATION_CONTEXT_ATTRIBUTE = org.springframework.web.servlet.DispatcherServlet.class.getName() + ".CONTEXT";

    /**
     * mvc 初始化(核心方法)
     * @param context the current WebApplicationContext
     */
    @Override
    protected void onRefresh(ApplicationContext context) {
        initStrategies(context);
    }

    protected void initStrategies(ApplicationContext context) {
        // 初始化文件上传解析器（MultipartResolver）
        // 用于解析包含文件上传的 multipart 请求。Spring 提供了标准实现，如
        // CommonsMultipartResolver（基于 Apache Commons FileUpload）和
        // StandardServletMultipartResolver（基于 Servlet 3.0 标准）。
        // 如果需要支持文件上传功能，则必须配置此解析器。
        // initMultipartResolver(context);

        // 初始化区域解析器（LocaleResolver）
        // 用于解析请求的区域信息（Locale），以便在国际化应用中提供正确的语言和格式。
        // 常见的实现包括 CookieLocaleResolver 和 SessionLocaleResolver，
        // 它们分别基于 Cookie 或 Session 存储和解析区域信息。
        // initLocaleResolver(context);

        // 初始化主题解析器（ThemeResolver）
        // 用于支持 Spring MVC 的主题功能。主题通常用来改变应用程序的视觉外观，比如 CSS 样式。
        // 常见实现包括 FixedThemeResolver 和 CookieThemeResolver，
        // 它们分别支持固定主题和基于 Cookie 的动态主题切换。
        // initThemeResolver(context);

        // 初始化请求 URL 映射器（HandlerMapping）
        // 用于将请求 URL 映射到对应的处理器（Handler）。
        // Spring MVC 提供了多种 HandlerMapping 实现，如
        // - RequestMappingHandlerMapping：基于 @RequestMapping 注解的映射。
        // - SimpleUrlHandlerMapping：基于 XML 或 Java 配置的静态映射。
        // DispatcherServlet 会从 HandlerMapping 中找到与请求匹配的处理器。
        initHandlerMappings(context);

        // 初始化请求适配器（HandlerAdapter）
        // 用于将请求的处理委派给具体的处理器（Handler）。
        // HandlerAdapter 通过多态支持不同类型的处理器，例如：
        // - HttpRequestHandlerAdapter：支持实现 HttpRequestHandler 接口的处理器。
        // - SimpleControllerHandlerAdapter：支持实现 Controller 接口的处理器。
        // - RequestMappingHandlerAdapter：支持基于 @RequestMapping 的处理器方法。
        // DispatcherServlet 会根据匹配的处理器选择适当的适配器执行请求。
        initHandlerAdapters(context);

        // 初始化异常处理器（HandlerExceptionResolver）
        // 用于捕获和处理请求过程中抛出的异常。
        // Spring MVC 提供默认实现，如：
        // - ExceptionHandlerExceptionResolver：支持 @ExceptionHandler 注解。
        // - ResponseStatusExceptionResolver：支持 @ResponseStatus 注解。
        // - DefaultHandlerExceptionResolver：处理 Spring 内部标准异常。
        // 如果没有匹配的异常处理器，异常将被容器默认的错误页面处理。
        // initHandlerExceptionResolvers(context);

        // 初始化请求到视图名称的转换器（RequestToViewNameTranslator）
        // 用于在没有显式指定视图名称的情况下，从请求路径推断视图名称。
        // 默认实现是 DefaultRequestToViewNameTranslator，它会从 URL 中提取路径作为视图名称。
        // 例如：请求 `/user/profile` 会解析为视图名称 `user/profile`。
        // initRequestToViewNameTranslator(context);

        // 初始化视图解析器（ViewResolver）
        // 用于将视图名称解析为具体的视图对象（View），比如 JSP、Thymeleaf 或 Freemarker。
        // 常见的实现包括：InternalResourceViewResolver、ThymeleafViewResolver。
        // DispatcherServlet 根据解析后的视图对象渲染响应。
        // initViewResolvers(context);

        // 初始化 Flash 属性管理器（FlashMapManager）
        // 用于在请求重定向时，存储和传递临时属性（Flash 属性）。
        // Spring 提供默认实现：DefaultFlashMapManager，基于 Session 存储 Flash 属性。
        // Flash 属性通常用于传递一次性的用户反馈消息，例如表单提交后的成功提示。
        // initFlashMapManager(context);
    }

    private void initHandlerMappings(ApplicationContext context) {
        //从容器中拿
        final Map<String, HandlerMapping> map = BeanFactoryUtils.beansOfTypeIncludingAncestors(context, HandlerMapping.class, true, false);
        if (!ObjectUtils.isEmpty(map)) {
            this.handlerMappings = new ArrayList<>(map.values());
        } else {
            //没有则从默认配置文件中拿
            this.handlerMappings.addAll(getDefaultStrategies(context, HandlerMapping.class));
        }
        this.handlerMappings.sort(Comparator.comparingInt(Ordered::getOrder));
    }

    private void initHandlerAdapters(ApplicationContext context) {
        final Map<String, HandlerAdapter> map = BeanFactoryUtils.beansOfTypeIncludingAncestors(context, HandlerAdapter.class, true, false);
        if (!ObjectUtils.isEmpty(map)) {
            this.handlerAdapters = new ArrayList<>(map.values());
        } else {
            //没有则从默认配置文件中拿
            this.handlerAdapters.addAll(getDefaultStrategies(context, HandlerAdapter.class));
        }
        this.handlerAdapters.sort(Comparator.comparingInt(Ordered::getOrder));

    }


    /**
     * 暴露特定于 DispatcherServlet 的请求属性，并将实际的请求分派委托给 {@link #doDispatch} 方法
     */
    @Override
    protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 日志记录，请求包含支持，添加框架级对象，解析闪存信息，路径解析
        /*
        logRequest(request);

		// Keep a snapshot of the request attributes in case of an include,
		// to be able to restore the original attributes after the include.
		Map<String, Object> attributesSnapshot = null;
		if (WebUtils.isIncludeRequest(request)) {
			attributesSnapshot = new HashMap<>();
			Enumeration<?> attrNames = request.getAttributeNames();
			while (attrNames.hasMoreElements()) {
				String attrName = (String) attrNames.nextElement();
				if (this.cleanupAfterInclude || attrName.startsWith(DEFAULT_STRATEGIES_PREFIX)) {
					attributesSnapshot.put(attrName, request.getAttribute(attrName));
				}
			}
		}

		// Make framework objects available to handlers and view objects.
		request.setAttribute(WEB_APPLICATION_CONTEXT_ATTRIBUTE, getWebApplicationContext());
		request.setAttribute(LOCALE_RESOLVER_ATTRIBUTE, this.localeResolver);
		request.setAttribute(THEME_RESOLVER_ATTRIBUTE, this.themeResolver);
		request.setAttribute(THEME_SOURCE_ATTRIBUTE, getThemeSource());

		if (this.flashMapManager != null) {
			FlashMap inputFlashMap = this.flashMapManager.retrieveAndUpdate(request, response);
			if (inputFlashMap != null) {
				request.setAttribute(INPUT_FLASH_MAP_ATTRIBUTE, Collections.unmodifiableMap(inputFlashMap));
			}
			request.setAttribute(OUTPUT_FLASH_MAP_ATTRIBUTE, new FlashMap());
			request.setAttribute(FLASH_MAP_MANAGER_ATTRIBUTE, this.flashMapManager);
		}

		RequestPath previousRequestPath = null;
		if (this.parseRequestPath) {
			previousRequestPath = (RequestPath) request.getAttribute(ServletRequestPathUtils.PATH_ATTRIBUTE);
			ServletRequestPathUtils.parseAndCache(request);
		}
        */

        // 设置 Web 应用上下文
        request.setAttribute(WEB_APPLICATION_CONTEXT_ATTRIBUTE, getWebApplicationContext());

        // 核心请求分发逻辑
        doDispatch(request, response);

        /*
		finally {
			if (!WebAsyncUtils.getAsyncManager(request).isConcurrentHandlingStarted()) {
				// Restore the original attribute snapshot, in case of an include.
				if (attributesSnapshot != null) {
					restoreAttributesAfterInclude(request, attributesSnapshot);
				}
			}
			if (this.parseRequestPath) {
				ServletRequestPathUtils.setParsedRequestPath(previousRequestPath, request);
			}
		}
         */
    }

    /**
     * 处理实际的分派到处理器的过程。
     *
     * <p>处理器将通过按顺序应用 servlet 的 HandlerMappings 来获取。
     * HandlerAdapter 将通过查询 servlet 安装的 HandlerAdapters 来找到第一个支持该处理器类的适配器。
     * <p>所有的 HTTP 方法都由该方法处理。具体哪些方法是可接受的，由 HandlerAdapters 或处理器本身决定
     * @param req current HTTP request
     * @param resp current HTTP response
     * @throws Exception in case of any kind of processing failure
     */
    protected void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        Exception ex = null;
        HandlerExecutionChain handlerExecutionChain = null;
        try {
            handlerExecutionChain = getHandler(req);
            //handlerMethod找不到则返回404
            if (ObjectUtils.isEmpty(handlerExecutionChain)) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            handlerExecutionChain.afterCompletion(req, resp, handlerExecutionChain.getHandlerMethod(), ex);

            // 获得适配器
            HandlerAdapter ha = getHandlerAdapter(handlerExecutionChain.getHandlerMethod());
            if (!handlerExecutionChain.applyPreInterceptor(req, resp)) {
                return;
            }
            ha.handle(req, resp, handlerExecutionChain.getHandlerMethod());
            handlerExecutionChain.applyPostInterceptor(req, resp);
        } catch (Exception e) {
            ex = e;
        }
        try {
            processDispatchResult(req, resp, handlerExecutionChain, ex);
        } catch (Exception e) {
            throw new ServletException(e.getMessage());
        }
    }

    private void processDispatchResult(HttpServletRequest req, HttpServletResponse resp, HandlerExecutionChain chain, Exception ex) {
        if (ex != null) {
            // 简单的错误处理
            PrintWriter writer = null;
            try {
                writer = resp.getWriter();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            writer.println("500 Internal Server Error");
            ex.printStackTrace(writer);
        }
    }


    /**
     * Return the HandlerExecutionChain for this request.
     * <p>Tries all handler mappings in order.
     * @param request current HTTP request
     * @return the HandlerExecutionChain, or {@code null} if no handler could be found
     */
    @Nullable
    protected HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
        if (this.handlerMappings != null) {
            for (HandlerMapping mapping : this.handlerMappings) {
                HandlerExecutionChain handler = mapping.getHandler(request);
                if (handler != null) {
                    return handler;
                }
            }
        }
        return null;
    }


    /**
     * No handler found &rarr; set appropriate HTTP response status.
     * @param request current HTTP request
     * @param response current HTTP response
     * @throws Exception if preparing the response failed
     */
    protected void noHandlerFound(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    /**
     * Return the HandlerAdapter for this handler object.
     * @param handler the handler object to find an adapter for
     * @throws ServletException if no HandlerAdapter can be found for the handler. This is a fatal error.
     */
    protected HandlerAdapter getHandlerAdapter(HandlerMethod handler) throws ServletException {
        if (this.handlerAdapters != null) {
            for (HandlerAdapter adapter : this.handlerAdapters) {
                if (adapter.support(handler)) {
                    return adapter;
                }
            }
        }
        throw new ServletException("No adapter for handler [" + handler +
                "]: The DispatcherServlet configuration needs to include a HandlerAdapter that supports this handler");
    }


    protected <T> List<T> getDefaultStrategies(ApplicationContext context, Class<T> strategyInterface) {
        if (defaultStrategies == null) {
            try {
                // Load default strategy implementations from properties file.
                // This is currently strictly internal and not meant to be customized
                // by application developers.
                ClassPathResource resource = new ClassPathResource(DEFAULT_STRATEGIES_PATH, DispatcherServlet.class);
                defaultStrategies = PropertiesLoaderUtils.loadProperties(resource);
            }
            catch (IOException ex) {
                throw new IllegalStateException("Could not load '" + DEFAULT_STRATEGIES_PATH + "': " + ex.getMessage());
            }
        }

        String key = strategyInterface.getName();
        String value = defaultStrategies.getProperty(key);
        if (value != null) {
            String[] classNames = StringUtils.commaDelimitedListToStringArray(value);
            List<T> strategies = new ArrayList<>(classNames.length);
            for (String className : classNames) {
                try {
                    Class<?> clazz = ClassUtils.forName(className, DispatcherServlet.class.getClassLoader());
                    Object strategy = createDefaultStrategy(context, clazz);
                    strategies.add((T) strategy);
                }
                catch (ClassNotFoundException ex) {
                    throw new BeanInitializationException(
                            "Could not find DispatcherServlet's default strategy class [" + className +
                                    "] for interface [" + key + "]", ex);
                }
                catch (LinkageError err) {
                    throw new BeanInitializationException(
                            "Unresolvable class definition for DispatcherServlet's default strategy class [" +
                                    className + "] for interface [" + key + "]", err);
                }
            }
            return strategies;
        }
        else {
            return Collections.emptyList();
        }
    }

    protected Object createDefaultStrategy(ApplicationContext context, Class<?> clazz) {
        return context.getAutowireCapableBeanFactory().createBean(clazz);
    }

}
