package com.iflove.simplespring.beans.factory.support;

import com.iflove.simplespring.beans.BeansException;
import com.iflove.simplespring.core.io.Resource;
import com.iflove.simplespring.core.io.ResourceLoader;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote Bean定义读取接口
 */

public interface BeanDefinitionReader {
    BeanDefinitionRegistry getRegistry();

    ResourceLoader getResourceLoader();

    void loadBeanDefinitions(Resource resource) throws BeansException;

    void loadBeanDefinitions(Resource... resources) throws BeansException;

    void loadBeanDefinitions(String location) throws BeansException;

    void loadBeanDefinitions(String... locations) throws BeansException;
}
