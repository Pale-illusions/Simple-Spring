package com.iflove.simplespring.webmvc;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.iflove.simplespring.context.ApplicationContext;
import com.iflove.simplespring.context.ConfigurableApplicationContext;
import com.iflove.simplespring.webmvc.context.ConfigurableWebApplicationContext;
import com.iflove.simplespring.webmvc.context.WebApplicationContext;
import com.iflove.simplespring.webmvc.context.support.AnnotationConfigWebApplicationContext;
import com.iflove.simplespring.webmvc.context.support.WebApplicationContextUtils;

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

public abstract class FrameworkServlet extends HttpServletBean {

    /** hutool log */
    protected final Log logger = LogFactory.get();

    /** WebApplicationContext for this servlet. */
    private WebApplicationContext webApplicationContext;

    /** ServletContext attribute to find the WebApplicationContext in. */
    private String contextAttribute;

    /**
     * Prefix for the ServletContext attribute for the WebApplicationContext.
     * The completion is the servlet name.
     */
    public static final String SERVLET_CONTEXT_PREFIX = FrameworkServlet.class.getName() + ".CONTEXT.";

    /**
     * HTTP methods supported by {@link jakarta.servlet.http.HttpServlet}.
     */
    private static final Set<String> HTTP_SERVLET_METHODS =
            new HashSet<>(Arrays.asList("DELETE", "HEAD", "GET", "OPTIONS", "POST", "PUT", "TRACE"));

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
     * @param context the current WebApplicationContext
     */
    protected void onRefresh(ApplicationContext context) {
        // For subclasses: do nothing by default.
    }


    /**
     * Close the WebApplicationContext of this servlet.
     * @see com.iflove.simplespring.context.ConfigurableApplicationContext#close()
     */
    @Override
    public void destroy() {
        getServletContext().log("Destroying Spring FrameworkServlet '" + getServletName() + "'");
        // Only call close() on WebApplicationContext if locally managed...
        if (this.webApplicationContext instanceof ConfigurableApplicationContext) {
            ((ConfigurableApplicationContext) this.webApplicationContext).close();
        }
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (HTTP_SERVLET_METHODS.contains(request.getMethod())) {
            super.service(request, response);
        }
        else {
            processRequest(request, response);
        }
    }

    /**
     * Delegate GET requests to processRequest/doService.
     * <p>Will also be invoked by HttpServlet's default implementation of {@code doHead},
     * with a {@code NoBodyResponse} that just captures the content length.
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


    protected final void processRequest(HttpServletRequest request, HttpServletResponse response) {
        // TODO 处理器 映射器等
    }
}

