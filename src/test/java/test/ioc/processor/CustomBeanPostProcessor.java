package test.ioc.processor;

import com.iflove.simplespring.beans.BeansException;
import com.iflove.simplespring.beans.factory.config.BeanPostProcessor;
import test.ioc.bean.Car;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

public class CustomBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("CustomBeanPostProcessor#postProcessBeforeInitialization, beanName: " + beanName);
        // 改为五菱宏光
        if ("car".equals(beanName)) {
            ((Car) bean).setBrand("五菱宏光");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("CustomBeanPostProcessor#postProcessAfterInitialization, beanName: " + beanName);
        return bean;
    }
}
