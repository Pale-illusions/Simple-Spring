package com.iflove.simplespring.context.event;

import com.iflove.simplespring.context.ApplicationEvent;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 * Event raised when an <code>ApplicationContext</code> gets closed.
 */

public class ContextClosedEvent extends ApplicationEvent {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ContextClosedEvent(Object source) {
        super(source);
    }
}
