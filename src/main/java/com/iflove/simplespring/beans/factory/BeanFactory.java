package com.iflove.simplespring.beans.factory;

import com.iflove.simplespring.beans.BeansException;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

public interface BeanFactory {
    /**
     * 获取bean对象
     * @param beanName  bean名称
     * @return          bean对象
     * @throws BeansException Bean相关异常
     */
    Object getBean(String beanName) throws BeansException;

    /**
     * 获取bean对象(参数实例化)
     * @param beanName  bean名称
     * @param args      实例化参数
     * @return bean对象
     * @throws BeansException Bean相关异常
     */
    Object getBean(String beanName, Object... args) throws BeansException;

    /**
     * 获取bean对象(对象Class类型)
     * @param beanName      bean名称
     * @param requiredType  对象Class类型
     * @return bean对象
     * @throws BeansException Bean相关异常
     */
    <T> T getBean(String beanName, Class<T> requiredType) throws BeansException;

    /**
     * 获取bean对象(对象Class类型)
     * @param requiredType  对象Class类型
     * @return bean对象
     * @throws BeansException Bean相关异常
     */
    <T> T getBean(Class<T> requiredType) throws BeansException;

    /**
     * Does this bean factory contain a bean definition or externally registered singleton
     * instance with the given name?
     * <p>If the given name is an alias, it will be translated back to the corresponding
     * canonical bean name.
     * <p>If this factory is hierarchical, will ask any parent factory if the bean cannot
     * be found in this factory instance.
     * <p>If a bean definition or singleton instance matching the given name is found,
     * this method will return {@code true} whether the named bean definition is concrete
     * or abstract, lazy or eager, in scope or not. Therefore, note that a {@code true}
     * return value from this method does not necessarily indicate that {@link #getBean}
     * will be able to obtain an instance for the same name.
     * @param name the name of the bean to query
     * @return whether a bean with the given name is present
     */
    boolean containsBean(String name);
}
