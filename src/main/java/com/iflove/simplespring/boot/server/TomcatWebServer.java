package com.iflove.simplespring.boot.server;

import com.iflove.simplespring.webmvc.DispatcherServlet;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote 内嵌 tomcat
 */

public class TomcatWebServer implements WebServer {
    private Tomcat tomcat;
    private String[] args;

    public TomcatWebServer(String[] args) {
       this.args = args;
    }

    @Override
    public void start() throws LifecycleException {
        tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.start();

        Context context = new StandardContext();
        context.setPath("");
        context.addLifecycleListener(new Tomcat.FixContextListener());

        DispatcherServlet servlet = new DispatcherServlet();
        Tomcat.addServlet(context, "dispatcherServlet", servlet).setAsyncSupported(true);
        context.addServletMappingDecoded("/", "dispatcherServlet");

        tomcat.getHost().addChild(context);

        Thread awaitThread = new Thread("tomcat_await_thread") {
            @Override
            public void run() {
                TomcatWebServer.this.tomcat.getServer().await();
            }
        };
        awaitThread.setDaemon(false);
        awaitThread.start();
    }

    @Override
    public void stop() {

    }

    @Override
    public int getPort() {
        Connector connector = this.tomcat.getConnector();
        return connector != null ? connector.getLocalPort() : -1;
    }
}
