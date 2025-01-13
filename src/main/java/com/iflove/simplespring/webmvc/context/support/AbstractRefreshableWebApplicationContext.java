package com.iflove.simplespring.webmvc.context.support;

import com.iflove.simplespring.context.support.AbstractRefreshableConfigApplicationContext;
import com.iflove.simplespring.webmvc.context.ConfigurableWebApplicationContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote 不使用，仅作展示
 */

public abstract class AbstractRefreshableWebApplicationContext extends AbstractRefreshableConfigApplicationContext implements ConfigurableWebApplicationContext {

    private ServletContext servletContext;

    private ServletConfig servletConfig;

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Override
    public ServletContext getServletContext() {
        return servletContext;
    }

    @Override
    public void setServletConfig(ServletConfig servletConfig) {
        this.servletConfig = servletConfig;
    }

    @Override
    public ServletConfig getServletConfig() {
        return servletConfig;
    }


}
