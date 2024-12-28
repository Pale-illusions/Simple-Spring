package com.iflove.simplespring.context.support;

import com.iflove.simplespring.beans.BeansException;
import com.iflove.simplespring.beans.factory.config.BeanPostProcessor;
import com.iflove.simplespring.context.ApplicationContext;
import com.iflove.simplespring.context.ApplicationContextAware;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

public class ApplicationContextAwareProcessor implements BeanPostProcessor {

    private final ApplicationContext applicationContext;

    public ApplicationContextAwareProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ApplicationContextAware) {
            ((ApplicationContextAware) bean).setApplicationContext(this.applicationContext);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
