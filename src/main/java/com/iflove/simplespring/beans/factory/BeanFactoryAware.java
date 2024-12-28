package com.iflove.simplespring.beans.factory;

import com.iflove.simplespring.beans.BeansException;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote 实现此接口，既能感知到所属的 BeanFactory
 * Interface to be implemented by beans that wish to be aware of their
 * owning {@link BeanFactory}.
 */

public interface BeanFactoryAware extends Aware {
    void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}
