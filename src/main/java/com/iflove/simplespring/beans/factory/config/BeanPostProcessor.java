package com.iflove.simplespring.beans.factory.config;

import com.iflove.simplespring.beans.BeansException;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote 用于修改新实例化 Bean 对象的扩展点
 * Factory hook that allows for custom modification of new bean instances,
 * e.g. checking for marker interfaces or wrapping them with proxies.
 */

public interface BeanPostProcessor {

    /**
     * 在 Bean 对象执行初始化方法之前，执行此方法
     *
     * @param bean bean对象
     * @param beanName bean名称
     * @return 修改后的 Bean 实例
     * @throws BeansException
     */
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    /**
     * 在 Bean 对象执行初始化方法之后，执行此方法
     *
     * @param bean bean对象
     * @param beanName bean名称
     * @return 修改后的 Bean 实例
     * @throws BeansException
     */
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;
}
