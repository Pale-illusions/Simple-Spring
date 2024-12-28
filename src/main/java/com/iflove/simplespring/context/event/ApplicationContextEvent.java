package com.iflove.simplespring.context.event;

import com.iflove.simplespring.context.ApplicationContext;
import com.iflove.simplespring.context.ApplicationEvent;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 * Base class for events raised for an <code>ApplicationContext</code>.
 */

public class ApplicationContextEvent extends ApplicationEvent {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ApplicationContextEvent(Object source) {
        super(source);
    }

    /**
     * Get the <code>ApplicationContext</code> that the event was raised for.
     */
    public final ApplicationContext getApplicationContext() {
        return (ApplicationContext) getSource();
    }
}
