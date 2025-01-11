package com.iflove.simplespring.boot.server;

import org.apache.catalina.LifecycleException;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

public interface WebServer {

    void start() throws LifecycleException;

    void stop();

    int getPort();

    default void destroy() {
        this.stop();
    }
}
