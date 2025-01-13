package com.iflove.simplespring.webmvc;

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

    /** HandlerExceptionResolvers 集合 */
    @Nullable
    private List<HandlerExceptionResolver> handlerExceptionResolvers = new ArrayList<>();

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
        // 初始化 请求url映射器
        initHandlerMappings(context);
        // 初始化 请求适配器
        initHandlerAdapters(context);
        // 初始化 异常处理器
        initHandlerExceptionResolvers(context);
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
        // TODO		this.handlerAdapters = null;

    }

    private void initHandlerExceptionResolvers(ApplicationContext context) {
        // TODO		this.handlerExceptionResolvers = null;

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
     * @param request current HTTP request
     * @param response current HTTP response
     * @throws Exception in case of any kind of processing failure
     */
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {


		HttpServletRequest processedRequest = request;
        HandlerExecutionChain mappedHandler = null;
//		HandlerExecutionChain mappedHandler = getHandler(processedRequest);
//        HandlerAdapter ha = getHandlerAdapter(mappedHandler.getHandler());
//        ModelAndView mv = ha.handle(processedRequest, response, mappedHandler.getHandler());
//
//        mappedHandler.applyPostHandle(processedRequest, response, mv);
//        processDispatchResult(processedRequest, response,mappedHandler, mv);


//		boolean multipartRequestParsed = false;
//
//		WebAsyncManager asyncManager = WebAsyncUtils.getAsyncManager(request);

		try {
			ModelAndView mv = null;
			Exception dispatchException = null;

			try {
//				processedRequest = checkMultipart(request);
//				multipartRequestParsed = (processedRequest != request);

				// Determine handler for the current request.
				mappedHandler = getHandler(processedRequest);
				if (mappedHandler == null) {
					noHandlerFound(processedRequest, response);
					return;
				}

				// Determine handler adapter for the current request.
				HandlerAdapter ha = getHandlerAdapter(mappedHandler.getHandler());

				// Process last-modified header, if supported by the handler.
//				String method = request.getMethod();
//				boolean isGet = HttpMethod.GET.matches(method);
//				if (isGet || HttpMethod.HEAD.matches(method)) {
//					long lastModified = ha.getLastModified(request, mappedHandler.getHandler());
//					if (new ServletWebRequest(request, response).checkNotModified(lastModified) && isGet) {
//						return;
//					}
//				}


                // TODO 拦截器
//				if (!mappedHandler.applyPreHandle(processedRequest, response)) {
//					return;
//				}

				// Actually invoke the handler.
				mv = ha.handle(processedRequest, response, mappedHandler.getHandler());

//				if (asyncManager.isConcurrentHandlingStarted()) {
//					return;
//				}

//				applyDefaultViewName(processedRequest, mv);

                // TODO 拦截器
//				mappedHandler.applyPostHandle(processedRequest, response, mv);
			}
			catch (Exception ex) {
				dispatchException = ex;
			}
			catch (Throwable err) {
				// As of 4.3, we're processing Errors thrown from handler methods as well,
				// making them available for @ExceptionHandler methods and other scenarios.
				dispatchException = new ServletException("Handler dispatch failed: " + err, err);
			}
			processDispatchResult(processedRequest, response, mappedHandler, mv, dispatchException);
		}
		catch (Exception ex) {
            throw new Exception(ex);
//			triggerAfterCompletion(processedRequest, response, mappedHandler, ex);
		}
		catch (Throwable err) {

//			triggerAfterCompletion(processedRequest, response, mappedHandler,
//					new ServletException("Handler processing failed: " + err, err));
		}
//		finally {
//			if (asyncManager.isConcurrentHandlingStarted()) {
//				// Instead of postHandle and afterCompletion
//				if (mappedHandler != null) {
//					mappedHandler.applyAfterConcurrentHandlingStarted(processedRequest, response);
//				}
//				asyncManager.setMultipartRequestParsed(multipartRequestParsed);
//			}
//			else {
//				// Clean up any resources used by a multipart request.
//				if (multipartRequestParsed || asyncManager.isMultipartRequestParsed()) {
//					cleanupMultipart(processedRequest);
//				}
//			}
//		}
    }

    private void processDispatchResult(HttpServletRequest processedRequest, HttpServletResponse response, HandlerExecutionChain mappedHandler, ModelAndView mv, Exception dispatchException) {
        // TODO
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        writer.println("Hello, world!");
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
    protected HandlerAdapter getHandlerAdapter(Object handler) throws ServletException {
        if (this.handlerAdapters != null) {
            for (HandlerAdapter adapter : this.handlerAdapters) {
                if (adapter.supports(handler)) {
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
                    Class<?> clazz = ClassUtils.forName(className, org.springframework.web.servlet.DispatcherServlet.class.getClassLoader());
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
