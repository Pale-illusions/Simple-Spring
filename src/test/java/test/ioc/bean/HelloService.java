package test.ioc.bean;

import com.iflove.simplespring.beans.BeansException;
import com.iflove.simplespring.beans.factory.BeanFactory;
import com.iflove.simplespring.beans.factory.BeanFactoryAware;
import com.iflove.simplespring.context.ApplicationContext;
import com.iflove.simplespring.context.ApplicationContextAware;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

public class HelloService implements ApplicationContextAware, BeanFactoryAware {

    private ApplicationContext applicationContext;

    private BeanFactory beanFactory;

    public String sayHello() {
        System.out.println("hello");
        return "hello";
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }
}