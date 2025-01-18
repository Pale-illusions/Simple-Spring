package com.iflove.simplespring.boot.server;

import org.apache.catalina.LifecycleException;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

public interface WebServer {

    void start(AnnotationConfigWebApplicationContext applicationContext);

}
