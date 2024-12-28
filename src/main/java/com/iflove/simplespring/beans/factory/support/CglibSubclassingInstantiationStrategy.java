package com.iflove.simplespring.beans.factory.support;

import com.iflove.simplespring.beans.factory.config.BeanDefinition;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

import java.lang.reflect.Constructor;
import java.util.Objects;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote cglib实例化
 */

public class CglibSubclassingInstantiationStrategy implements InstantiationStrategy {
    /**
     * 实例化bean(cglib)
     * @param beanDefinition    bean定义
     * @param beanName          bean名称
     * @param ctor              构造器
     * @param args              实例化参数
     * @return bean对象
     */
    @Override
    public Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor ctor, Object[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(beanDefinition.getBeanClass());
        enhancer.setCallback(new NoOp() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }
        });
        if (Objects.isNull(ctor)) return enhancer.create();
        return enhancer.create(ctor.getParameterTypes(), args);
    }
}
