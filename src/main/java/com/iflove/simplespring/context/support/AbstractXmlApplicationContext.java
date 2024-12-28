package com.iflove.simplespring.context.support;

import com.iflove.simplespring.beans.factory.support.DefaultListableBeanFactory;
import com.iflove.simplespring.beans.factory.xml.XmlBeanDefinitionReader;

import java.util.Objects;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 * Convenient base class for {@link com.iflove.simplespring.context.ApplicationContext}
 * implementations, drawing configuration from XML documents containing bean definitions
 * understood by an {@link XmlBeanDefinitionReader}.
 */

public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext {

    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) {
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory, this);
        String[] configLocations = getConfigLocations();
        if (Objects.nonNull(configLocations)) {
            beanDefinitionReader.loadBeanDefinitions(configLocations);
        }
    }

    protected abstract String[] getConfigLocations();
}
