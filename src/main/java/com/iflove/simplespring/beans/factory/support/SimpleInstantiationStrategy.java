package com.iflove.simplespring.beans.factory.support;

import com.iflove.simplespring.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote JDK实例化
 */

public class SimpleInstantiationStrategy implements InstantiationStrategy {
    /**
     * 实例化bean(JDK)
     * @param beanDefinition    bean定义
     * @param beanName          bean名称
     * @param ctor              构造器
     * @param args              实例化参数
     * @return bean对象
     */
    @Override
    public Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor ctor, Object[] args) {
        Class clazz = beanDefinition.getBeanClass();
        try {
            if (Objects.nonNull(ctor)) {
                return clazz.getDeclaredConstructor(ctor.getParameterTypes()).newInstance(args);
            } else {
                return clazz.getDeclaredConstructor().newInstance();
            }
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
