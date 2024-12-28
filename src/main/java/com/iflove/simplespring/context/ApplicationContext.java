package com.iflove.simplespring.context;

import com.iflove.simplespring.beans.factory.HierarchicalBeanFactory;
import com.iflove.simplespring.beans.factory.ListableBeanFactory;
import com.iflove.simplespring.core.io.ResourceLoader;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote 应用上下文
 * Central interface to provide configuration for an application.
 * This is read-only while the application is running, but may be
 * reloaded if the implementation supports this.
 */

public interface ApplicationContext extends ListableBeanFactory, HierarchicalBeanFactory, ResourceLoader, ApplicationEventPublisher {

}
