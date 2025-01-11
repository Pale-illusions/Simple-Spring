package com.iflove.simplespring.webmvc.context;

import com.iflove.simplespring.context.ConfigurableApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

public interface ConfigurableWebApplicationContext extends WebApplicationContext, ConfigurableApplicationContext {

    ServletConfig getServletConfig();

    void setServletConfig(ServletConfig servletConfig);

    void setServletContext(ServletContext servletContext);
}
