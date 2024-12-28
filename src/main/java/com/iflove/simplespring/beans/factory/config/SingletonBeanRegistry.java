package com.iflove.simplespring.beans.factory.config;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote Bean的单例注册接口
 */

public interface SingletonBeanRegistry {
    /**
     * 获取单例对象
     * @param beanName  bean名称
     * @return          单例对象
     */
    Object getSingleton(String beanName);

    /**
     * 注册单例对象
     * @param beanName beanName
     * @param singletonObject 单例对象
     */
    void registerSingleton(String beanName, Object singletonObject);
}
