package com.iflove.simplespring.webmvc;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

public abstract class FrameworkServlet extends HttpServletBean implements ApplicationContextAware {

    /**
     * hutool log
     */
    protected final Log logger = LogFactory.get();

    /**
     * WebApplicationContext for this servlet.
     */
    private WebApplicationContext webApplicationContext;

    /**
     * If the WebApplicationContext was injected via {@link #setApplicationContext}.
     */
    private boolean webApplicationContextInjected = false;

    /**
     * ServletContext attribute to find the WebApplicationContext in.
     */
    private String contextAttribute;

    /**
     * Prefix for the ServletContext attribute for the WebApplicationContext.
     * The completion is the servlet name.
     */
    public static final String SERVLET_CONTEXT_PREFIX = FrameworkServlet.class.getName() + ".CONTEXT.";

    /**
     * HTTP methods supported by {@link javax.servlet.http.HttpServlet}.
     */
    private static final Set<String> HTTP_SERVLET_METHODS =
            new HashSet<>(Arrays.asList("DELETE", "HEAD", "GET", "OPTIONS", "POST", "PUT", "TRACE"));


    public FrameworkServlet() {
    }

    public FrameworkServlet(WebApplicationContext webApplicationContext) {
        this.webApplicationContext = webApplicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        if (this.webApplicationContext == null && applicationContext instanceof WebApplicationContext) {
            this.webApplicationContext = (WebApplicationContext) applicationContext;
            this.webApplicationContextInjected = true;
        }
    }

    @Override
    protected void initServletBean() throws ServletException {
        logger.info("Initializing Spring " + getClass().getSimpleName() + " '" + getServletName() + "'");
        logger.info("Initializing Servlet '" + getServletName() + "'");

        long startTime = System.currentTimeMillis();

        // 初始化 Web IOC 容器
        this.webApplicationContext = initWebApplicationContext();
        // 默认空实现，且无子类重写
        initFrameworkServlet();

        logger.info("Completed initialization in " + (System.currentTimeMillis() - startTime) + " ms");
    }

    /**
     * Initialize and publish the WebApplicationContext for this servlet.
     *
     * @return the WebApplicationContext instance
     */
    protected WebApplicationContext initWebApplicationContext() {

        WebApplicationContext rootContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        WebApplicationContext wac = this.webApplicationContext;

        if (wac instanceof ConfigurableWebApplicationContext) {
            ConfigurableWebApplicationContext cwac = (ConfigurableWebApplicationContext) wac;
            if (cwac.getParent() == null) {
                cwac.setParent(rootContext);
            }
            // 配置上下文并刷新
            configureAndRefreshWebApplicationContext(cwac);
        } else if (wac == null) {
            // 寻找 IOC 容器
            wac = findWebApplicationContext();
        }

        if (wac == null) {
            // 创建新的 IOC 容器
            wac = createWebApplicationContext(rootContext);
        }

        // MVC 初始化 (由 DispatcherServlet 实现)
        onRefresh(wac);

        // 将 WebApplicationContext 实例放入 Servlet 上下文中共享
        String attrName = getServletContextAttributeName();
        getServletContext().setAttribute(attrName, wac);

        return wac;
    }

    protected WebApplicationContext findWebApplicationContext() {
        String attrName = getContextAttribute();
        if (attrName == null) {
            return null;
        }
        WebApplicationContext wac =
                WebApplicationContextUtils.getWebApplicationContext(getServletContext(), attrName);
        if (wac == null) {
            throw new IllegalStateException("No WebApplicationContext found: initializer not registered?");
        }
        return wac;
    }

    /**
     * This method will be invoked after any bean properties have been set and
     * the WebApplicationContext has been loaded. The default implementation is empty;
     * subclasses may override this method to perform any initialization they require.
     *
     * @throws ServletException in case of an initialization exception
     */
    protected void initFrameworkServlet() throws ServletException {
    }

    public String getServletContextAttributeName() {
        return SERVLET_CONTEXT_PREFIX + getServletName();
    }

    protected WebApplicationContext createWebApplicationContext(ApplicationContext parent) {
        // 创建一个新的 IOC 容器
        ConfigurableWebApplicationContext wac = new AnnotationConfigWebApplicationContext();
        // 设置父容器
        wac.setParent(parent);
        // 配置上下文并刷新
        configureAndRefreshWebApplicationContext(wac);
        return wac;
    }

    protected void configureAndRefreshWebApplicationContext(ConfigurableWebApplicationContext wac) {
        // 配置 servlet 上下文
        wac.setServletContext(getServletContext());
        wac.setServletConfig(getServletConfig());
        // 刷新容器
        wac.refresh();
    }

    public String getContextAttribute() {
        return contextAttribute;
    }

    public void setContextAttribute(String contextAttribute) {
        this.contextAttribute = contextAttribute;
    }

    /**
     * Template method which can be overridden to add servlet-specific refresh work.
     * Called after successful context refresh.
     * <p>This implementation is empty.
     *
     * @param context the current WebApplicationContext
     */
    protected void onRefresh(ApplicationContext context) {
        // For subclasses: do nothing by default.
    }


    /**
     * Close the WebApplicationContext of this servlet.
     *
     * @see com.iflove.simplespring.context.ConfigurableApplicationContext#close()
     */
    @Override
    public void destroy() {
        getServletContext().log("Destroying Spring FrameworkServlet '" + getServletName() + "'");
        // Only call close() on WebApplicationContext if locally managed...
        if (!this.webApplicationContextInjected && this.webApplicationContext instanceof ConfigurableApplicationContext) {
            ((ConfigurableApplicationContext) this.webApplicationContext).close();
        }
    }


    /**
     * 拦截请求，根据请求类型判断去向
     *
     * @param request  Http 请求
     * @param response Http 响应
     * @throws ServletException ServletException
     * @throws IOException      IOException
     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 如果请求类型为 GET, POST, DELETE, PUT 等常见 Http 方法，走 super.service 处理
        if (HTTP_SERVLET_METHODS.contains(request.getMethod())) {
            super.service(request, response);
        } else {
            processRequest(request, response);
        }
    }

    /**
     * Delegate GET requests to processRequest/doService.
     * <p>Will also be invoked by HttpServlet's default implementation of {@code doHead},
     * with a {@code NoBodyResponse} that just captures the content length.
     *
     * @see #doHead
     */
    @Override
    protected final void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Delegate POST requests to {@link #processRequest}.
     */
    @Override
    protected final void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);
    }

    /**
     * Delegate PUT requests to {@link #processRequest}.
     */
    @Override
    protected final void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);
    }

    /**
     * Delegate DELETE requests to {@link #processRequest}.
     */
    @Override
    protected final void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);
    }


    /**
     * 处理 Http 请求
     *
     * @param request  Http 请求
     * @param response Http 响应
     */
    protected final void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 记录开始时间，设置请求上下文，初始化异步管理器
        /*
        long startTime = System.currentTimeMillis();
        Throwable failureCause = null;

        LocaleContext previousLocaleContext = LocaleContextHolder.getLocaleContext();
        LocaleContext localeContext = buildLocaleContext(request);

        RequestAttributes previousAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes requestAttributes = buildRequestAttributes(request, response, previousAttributes);

        WebAsyncManager asyncManager = WebAsyncUtils.getAsyncManager(request);
        asyncManager.registerCallableInterceptor(org.springframework.web.servlet.FrameworkServlet.class.getName(), new org.springframework.web.servlet.FrameworkServlet.RequestBindingInterceptor());

        initContextHolders(request, localeContext, requestAttributes);
        */

        try {
            doService(request, response);
        } catch (ServletException | IOException ex) {
            throw ex;
        } catch (Throwable ex) {
            throw new ServletException("Request processing failed: " + ex, ex);
        }

        // 清理上下文，日志记录和事件发布
        /*
        finally {
            resetContextHolders(request, previousLocaleContext, previousAttributes);
            if (requestAttributes != null) {
                requestAttributes.requestCompleted();
            }
            logResult(request, response, failureCause, asyncManager);
            publishRequestHandledEvent(request, response, startTime, failureCause);
        }
        */
    }

    /**
     * 子类必须实现此方法以执行请求处理的工作，接收对 GET、POST、PUT 和 DELETE 的集中回调。
     *
     * <p>该约定与 HttpServlet 中常被重写的 {@code doGet} 或 {@code doPost} 方法基本相同。
     * <p>此类拦截调用以确保异常处理和事件发布的发生。
     *
     * @param request  current HTTP request
     * @param response current HTTP response
     * @throws Exception in case of any kind of processing failure
     * @see javax.servlet.http.HttpServlet#doGet
     * @see javax.servlet.http.HttpServlet#doPost
     */
    protected abstract void doService(HttpServletRequest request, HttpServletResponse response)
            throws Exception;


    /**
     * 返回 WebApplicationContext
     */
    public final WebApplicationContext getWebApplicationContext() {
        return this.webApplicationContext;
    }
}

