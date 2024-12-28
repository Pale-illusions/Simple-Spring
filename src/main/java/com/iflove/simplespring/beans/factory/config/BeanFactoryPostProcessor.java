package com.iflove.simplespring.beans.factory.config;

import com.iflove.simplespring.beans.BeansException;
import com.iflove.simplespring.beans.factory.ConfigurableListableBeanFactory;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote 允许自定义修改 BeanDefinition 属性信息
 * Allows for custom modification of an application context's bean definitions,
 * adapting the bean property values of the context's underlying bean factory.
 */

public interface BeanFactoryPostProcessor {

    /**
     * 在所有的 BeanDefinition 加载完成后，实例化 Bean 对象之前，提供修改 BeanDefinition 属性的机制
     *
     * @param beanFactory   bean工厂
     */
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;

}
