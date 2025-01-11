package com.iflove.simplespring.webmvc;

import com.iflove.simplespring.context.ApplicationContext;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

public class DispatcherServlet extends FrameworkServlet {

    @Override
    protected void onRefresh(ApplicationContext context) {
        initStrategies(context);
    }

    protected void initStrategies(ApplicationContext context) {
        initHandlerMappings(context);
        initHandlerAdapters(context);
        initHandlerExceptionResolvers(context);
        initViewResolvers(context);
    }

    private void initHandlerMappings(ApplicationContext context) {
        // TODO
    }

    private void initHandlerAdapters(ApplicationContext context) {
        // TODO
    }

    private void initHandlerExceptionResolvers(ApplicationContext context) {
        // TODO
    }

    private void initViewResolvers(ApplicationContext context) {
        // TODO
    }

}
