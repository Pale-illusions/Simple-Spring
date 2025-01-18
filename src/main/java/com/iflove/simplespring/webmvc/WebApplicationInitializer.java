package com.iflove.simplespring.webmvc;

import javax.servlet.ServletContext;

/**
 * Web容器初始化器顶层接口
 */
public interface WebApplicationInitializer {

    /**
     * 启动Web容器
     */
    void onStartUp(ServletContext servletContext);
}
