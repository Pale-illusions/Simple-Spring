package com.iflove.simplespring.context;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote 事件发布者接口
 * Interface that encapsulates event publication functionality.
 * Serves as super-interface for ApplicationContext.
 */

public interface ApplicationEventPublisher {

    /**
     * Notify all listeners registered with this application of an application
     * event. Events may be framework events (such as RequestHandledEvent)
     * or application-specific events.
     * @param event the event to publish
     */
    void publishEvent(ApplicationEvent event);

}
