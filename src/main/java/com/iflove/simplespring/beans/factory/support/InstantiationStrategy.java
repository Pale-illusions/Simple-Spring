package com.iflove.simplespring.beans.factory.support;

import com.iflove.simplespring.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote 实例化策略接口
 */

public interface InstantiationStrategy {
    /**
     * 实例化bean
     * @param beanDefinition    bean定义
     * @param beanName          bean名称
     * @param ctor              构造器
     * @param args              实例化参数
     * @return bean对象
     */
    Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor ctor, Object[] args);
}
