package com.iflove.simplespring.context.support;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote 最终实现类
 */

public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext {

    public ClassPathXmlApplicationContext() {

    }

    /**
     * 从xml中加载 BeanDefinition，并刷新上下文
     *
     * @param configLocation 配置地址
     */
    public ClassPathXmlApplicationContext(String configLocation) {
        this(new String[] {configLocation});
    }

    /**
     * 从xml中加载 BeanDefinition，并刷新上下文
     *
     * @param configLocations 配置地址
     */
    public ClassPathXmlApplicationContext(String[] configLocations) {
        setConfigLocations(configLocations);
        refresh();
    }
}
