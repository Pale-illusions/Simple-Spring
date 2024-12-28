package com.iflove.simplespring.beans.factory.support;

import com.iflove.simplespring.beans.BeansException;
import com.iflove.simplespring.beans.factory.config.BeanDefinition;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote bean定义注册
 */

public interface BeanDefinitionRegistry {
    /**
     * 向注册表中注册bean定义
     * @param beanName          bean名称
     * @param beanDefinition    bean定义
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    /**
     * 使用Bean名称查询BeanDefinition
     *
     * @param beanName 指定名称
     * @return {@link BeanDefinition}
     */
    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    /**
     * 判断是否包含指定名称的BeanDefinition
     * @param beanName 指定名称
     * @return boolen
     */
    boolean containsBeanDefinition(String beanName);

    /**
     * 返回注册表中所有的Bean名称
     * @return 返回注册表中所有的Bean名称
     */
    String[] getBeanDefinitionNames();
}
