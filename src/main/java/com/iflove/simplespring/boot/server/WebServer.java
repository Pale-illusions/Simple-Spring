package com.iflove.simplespring.boot.server;

import org.apache.catalina.LifecycleException;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

/**
 * @author Yusiheng
 */

public interface WebServer {

    void start(AnnotationConfigWebApplicationContext applicationContext);

}
