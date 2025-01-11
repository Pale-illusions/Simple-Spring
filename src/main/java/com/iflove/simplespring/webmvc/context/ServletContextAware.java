package com.iflove.simplespring.webmvc.context;

import com.iflove.simplespring.beans.factory.Aware;

import javax.servlet.ServletContext;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

public interface ServletContextAware extends Aware {

    void setServletContext(ServletContext servletContext);
}
