package com.iflove.simplespring.beans.factory.support;

import com.iflove.simplespring.beans.BeansException;
import com.iflove.simplespring.beans.factory.FactoryBean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

public abstract class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry {
    /**
     * Cache of singleton objects created by FactoryBeans: FactoryBean name --> object
     */
    private final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<>();

    /**
     * 从缓存中获取 FactoryBean 代理对象
     * @param beanName beanName
     * @return 缓存中的代理对象
     */
    protected Object getCachedObjectForFactoryBean(String beanName) {
        Object object = this.factoryBeanObjectCache.get(beanName);
        return (object != NULL_OBJECT ? object : null);
    }

    /**
     * 获取 FactoryBean 代理对象
     * @param factoryBean FactoryBean
     * @param beanName BeanName
     * @return FactoryBean 代理对象
     */
    protected Object getObjectFromFactoryBean(FactoryBean factoryBean, String beanName) {
        // 单例
        if (factoryBean.isSingleton()) {
            Object object = this.factoryBeanObjectCache.get(beanName);
            // 缓存未命中
            if (object == null) {
                // FactoryBean getObject
                object = doGetObjectFromFactoryBean(factoryBean, beanName);
                // 存入缓存
                this.factoryBeanObjectCache.put(beanName, (object != null ? object : NULL_OBJECT));
            }
            return (object != null ? object : NULL_OBJECT);
        } else {
            // 原型对象不做缓存
            return doGetObjectFromFactoryBean(factoryBean, beanName);
        }
    }

    /**
     * 获取 FactoryBean 代理对象
     * @param factoryBean FactoryBean
     * @param beanName BeanName
     * @return FactoryBean 代理对象
     */
    private Object doGetObjectFromFactoryBean(final FactoryBean factoryBean, final String beanName) {
        try {
            return factoryBean.getObject();
        } catch (Exception e) {
            throw new BeansException("FactoryBean threw exception on object[" + beanName + "] creation", e);
        }
    }
}
