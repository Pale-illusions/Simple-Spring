package com.iflove.simplespring.webmvc.support;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import javax.servlet.Filter;

/**
 * WebApplicationInitializer的子类，用于获取到父子容器的配置，具体实现类由用户实现
 */
public abstract class AbstractAnnotationConfigDispatcherServletInitializer extends AbstractDispatcherServletInitializer{
    @Override
    protected AnnotationConfigApplicationContext createRootApplicationContext() {
        final Class<?>[] rootConfigClasses = getRootConfigClasses();
        if (!ObjectUtils.isEmpty(rootConfigClasses)){
            AnnotationConfigApplicationContext rootContext = new AnnotationConfigApplicationContext();
            rootContext.register(rootConfigClasses);
            return rootContext;
        }
        return null;
    }

    @Override
    protected WebApplicationContext createWebApplicationContext() {
        final Class<?>[] webConfigClasses = getWebConfigClasses();
        if (!ObjectUtils.isEmpty(webConfigClasses)){
            AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
            webContext.register(webConfigClasses);
            return webContext;
        }
        return null;
    }

    @Override
    protected Filter[] getFilters() {
        return new Filter[0];
    }


}
