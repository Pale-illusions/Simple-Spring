package com.iflove.simplespring.beans.factory.config;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote Bean引用
 */

public class BeanReference {
    private String beanName;

    public BeanReference(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }
}
