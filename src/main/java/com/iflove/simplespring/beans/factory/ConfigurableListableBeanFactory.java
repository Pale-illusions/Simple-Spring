package com.iflove.simplespring.beans.factory;

import com.iflove.simplespring.beans.BeansException;
import com.iflove.simplespring.beans.factory.config.AutowireCapableBeanFactory;
import com.iflove.simplespring.beans.factory.config.BeanDefinition;
import com.iflove.simplespring.beans.factory.config.ConfigurableBeanFactory;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 * Configuration interface to be implemented by most listable bean factories.
 * In addition to {@link ConfigurableBeanFactory}, it provides facilities to
 * analyze and modify bean definitions, and to pre-instantiate singletons.
 */

public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {
    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    void preInstantiateSingletons() throws BeansException;
}
