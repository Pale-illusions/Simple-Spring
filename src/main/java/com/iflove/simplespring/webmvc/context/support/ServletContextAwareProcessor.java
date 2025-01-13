package com.iflove.simplespring.webmvc.context.support;

import com.iflove.simplespring.beans.BeansException;
import com.iflove.simplespring.beans.factory.config.BeanPostProcessor;
import com.iflove.simplespring.webmvc.context.ServletConfigAware;
import com.iflove.simplespring.webmvc.context.ServletContextAware;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote 不使用，仅作展示
 */

public class ServletContextAwareProcessor implements BeanPostProcessor {

    private ServletContext servletContext;

    private ServletConfig servletConfig;

    public ServletContextAwareProcessor(ServletContext servletContext, ServletConfig servletConfig) {
        this.servletContext = servletContext;
        this.servletConfig = servletConfig;
    }

    protected ServletContext getServletContext() {
        if (this.servletContext == null && getServletConfig() != null) {
            return getServletConfig().getServletContext();
        }
        return this.servletContext;
    }

    protected ServletConfig getServletConfig() {
        return this.servletConfig;
    }


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (getServletConfig() != null && bean instanceof ServletConfigAware){
            ((ServletConfigAware)bean).setServletConfig(this.servletConfig);
        }
        if (getServletContext() != null && bean instanceof ServletContextAware){
            ((ServletContextAware)bean).setServletContext(this.servletContext);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        return bean;
    }

}
