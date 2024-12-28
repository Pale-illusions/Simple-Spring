package com.iflove.simplespring.context;

import java.util.EventObject;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 * Class to be extended by all application events. Abstract as it
 * doesn't make sense for generic events to be published directly.
 */

public abstract class ApplicationEvent extends EventObject {

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ApplicationEvent(Object source) {
        super(source);
    }
}
