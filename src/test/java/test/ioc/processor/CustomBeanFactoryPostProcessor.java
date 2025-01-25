package test.ioc.processor;

import com.iflove.simplespring.beans.BeansException;
import com.iflove.simplespring.beans.PropertyValue;
import com.iflove.simplespring.beans.PropertyValues;
import com.iflove.simplespring.beans.factory.ConfigurableListableBeanFactory;
import com.iflove.simplespring.beans.factory.config.BeanDefinition;
import com.iflove.simplespring.beans.factory.config.BeanFactoryPostProcessor;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

public class CustomBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("CustomBeanFactoryPostProcessor#postProcessBeanFactory");
        BeanDefinition personBeanDefiniton = beanFactory.getBeanDefinition("person");
        PropertyValues propertyValues = personBeanDefiniton.getPropertyValues();
        //将person的name属性改为苍镜月(改)
        propertyValues.addPropertyValue(new PropertyValue("name", "苍镜月(改)"));
    }
}