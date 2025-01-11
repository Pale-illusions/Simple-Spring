package com.iflove.simplespring.webmvc;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

public abstract class HttpServletBean extends HttpServlet {

    @Override
    public final void init() throws ServletException {
        // 源码里面设置了 ServletConfigPropertyValues
        // 不是很理解有什么作用？在哪里起作用？暂时不做实现

        // Let subclasses do whatever initialization they like.
        initServletBean();
    }

    /**
     * Subclasses may override this to perform custom initialization.
     * All bean properties of this servlet will have been set before this
     * method is invoked.
     * <p>This default implementation is empty.
     * @throws ServletException if subclass initialization fails
    */
    protected void initServletBean() throws ServletException {
    }
}
