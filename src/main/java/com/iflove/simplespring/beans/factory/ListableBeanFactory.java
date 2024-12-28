package com.iflove.simplespring.beans.factory;

import com.iflove.simplespring.beans.BeansException;

import java.util.Map;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote 对于 {@link BeanFactory} 的扩展接口，由有遍历全部bean实例能力的类实现
 */

public interface ListableBeanFactory extends BeanFactory {

    /**
     * 按照类型返回 Bean 实例
     * @param type  类型
     * @param <T>   类型
     * @return Bean实例集合
     */
    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;

    /**
     * 返回注册表中所有的Bean名称
     * @return 注册表中所有的Bean名称
     */
    String[] getBeanDefinitionNames();
}
