package com.iflove.simplespring.context.event;

import com.iflove.simplespring.beans.factory.BeanFactory;
import com.iflove.simplespring.context.ApplicationEvent;
import com.iflove.simplespring.context.ApplicationListener;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster {

    public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
        setBeanFactory(beanFactory);
    }

    @Override
    public void multicastEvent(final ApplicationEvent event) {
        for (ApplicationListener listener : getApplicationListeners(event)) {
            listener.onApplicationEvent(event);
        }
    }
}
