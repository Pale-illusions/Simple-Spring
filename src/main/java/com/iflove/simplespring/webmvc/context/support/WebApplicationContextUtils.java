package com.iflove.simplespring.webmvc.context.support;

import com.iflove.simplespring.webmvc.context.WebApplicationContext;

import javax.servlet.ServletContext;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

public class WebApplicationContextUtils {

    /**
     * Find the root {@code WebApplicationContext} for this web app, typically
     * loaded via {@link com.iflove.simplespring.webmvc.context.ContextLoaderListener}.
     * <p>Will rethrow an exception that happened on root context startup,
     * to differentiate between a failed context startup and no context at all.
     * @param sc the ServletContext to find the web application context for
     * @return the root WebApplicationContext for this web app, or {@code null} if none
     * @see com.iflove.simplespring.webmvc.context.WebApplicationContext#ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE
     */
    public static WebApplicationContext getWebApplicationContext(ServletContext sc) {
        return getWebApplicationContext(sc, WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
    }


    /**
     * Find a custom {@code WebApplicationContext} for this web app.
     * @param sc the ServletContext to find the web application context for
     * @param attrName the name of the ServletContext attribute to look for
     * @return the desired WebApplicationContext for this web app, or {@code null} if none
     */
    public static WebApplicationContext getWebApplicationContext(ServletContext sc, String attrName) {
        Object attr = sc.getAttribute(attrName);
        if (attr == null) {
            return null;
        }
        if (attr instanceof RuntimeException) {
            throw (RuntimeException) attr;
        }
        if (attr instanceof Error) {
            throw (Error) attr;
        }
        if (attr instanceof Exception) {
            throw new IllegalStateException((Exception) attr);
        }
        if (!(attr instanceof WebApplicationContext)) {
            throw new IllegalStateException("Context attribute is not of type WebApplicationContext: " + attr);
        }
        return (WebApplicationContext) attr;
    }

}
