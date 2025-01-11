package com.iflove.simplespring.webmvc.context;

import com.iflove.simplespring.context.ApplicationContext;

import javax.servlet.ServletContext;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

public interface WebApplicationContext extends ApplicationContext {

    /**
     * Context attribute to bind root WebApplicationContext to on successful startup.
     * <p>Note: If the startup of the root context fails, this attribute can contain
     * an exception or error as value. Use WebApplicationContextUtils for convenient
     * lookup of the root WebApplicationContext.
     * @see com.iflove.simplespring.webmvc.context.support.WebApplicationContextUtils#getWebApplicationContext
     */
    String ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE = WebApplicationContext.class.getName() + ".ROOT";

    /**
     * Scope identifier for request scope: "request".
     * Supported in addition to the standard scopes "singleton" and "prototype".
     */
    String SCOPE_REQUEST = "request";

    /**
     * Scope identifier for session scope: "session".
     * Supported in addition to the standard scopes "singleton" and "prototype".
     */
    String SCOPE_SESSION = "session";

    /**
     * Scope identifier for the global web application scope: "application".
     * Supported in addition to the standard scopes "singleton" and "prototype".
     */
    String SCOPE_APPLICATION = "application";

    ServletContext getServletContext();
}
