package com.iflove.simplespring.boot;

import com.iflove.simplespring.boot.server.TomcatWebServer;
import org.apache.catalina.LifecycleException;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

public class SpringApplication {

    public static void run(Class<?> cls, String[] args) {
        System.out.println("-------Simple-Spring-------");
        TomcatWebServer tomcatWebServer = new TomcatWebServer(args);
        try {
            tomcatWebServer.start();

        } catch (LifecycleException e) {
            throw new RuntimeException(e);
        }
    }
}
