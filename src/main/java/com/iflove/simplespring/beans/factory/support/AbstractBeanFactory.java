package com.iflove.simplespring.beans.factory.support;

import com.iflove.simplespring.beans.BeansException;
import com.iflove.simplespring.beans.factory.FactoryBean;
import com.iflove.simplespring.beans.factory.config.BeanDefinition;
import com.iflove.simplespring.beans.factory.config.BeanPostProcessor;
import com.iflove.simplespring.beans.factory.config.ConfigurableBeanFactory;
import com.iflove.simplespring.core.convert.ConversionService;
import com.iflove.simplespring.utils.StringValueResolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote bean工厂抽象类
 */

public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {

    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    /**
     * String resolvers to apply e.g. to annotation attribute values
     */
    private final List<StringValueResolver> embeddedValueResolvers = new ArrayList<>();

    private ConversionService conversionService;

    /**
     * 获取bean对象
     * @param beanName  bean名称
     * @return          bean对象
     * @throws BeansException Bean相关异常
     */
    @Override
    public Object getBean(String beanName) throws BeansException {
        return doGetBean(beanName, null);
    }

    /**
     * 获取bean对象(参数实例化)
     * @param beanName      bean名称
     * @param args          实例化参数
     * @return              bean对象
     * @throws BeansException Bean相关异常
     */
    @Override
    public Object getBean(String beanName, Object... args) throws BeansException {
        return doGetBean(beanName, args);
    }

    /**
     * 获取bean对象(对象Class类型)
     * @param beanName      bean名称
     * @param requiredType  对象Class类型
     * @return              bean对象
     * @throws BeansException Bean相关异常
     */
    @Override
    public <T> T getBean(String beanName, Class<T> requiredType) throws BeansException {
        return (T) getBean(beanName);
    }

    /**
     * 获取bean对象
     * @param name  bean名称
     * @param args  实例化参数
     * @return      bean对象
     * @param <T>   bean对象类型
     */
    protected <T> T doGetBean(final String name, final Object[] args) {
        // 获取单例对象
        Object sharedInstance = getSingleton(name);
        // 单例对象不存在，说明未初始化
        if (Objects.nonNull(sharedInstance)) {
            // 如果是 FactoryBean，则需要调用 FactoryBean#getObject
            return (T) getObjectForBeanInstance(sharedInstance, name);
        }

        // 初始化bean对象
        BeanDefinition beanDefinition = getBeanDefinition(name);
        Object bean = createBean(name, beanDefinition, args);
        return (T) getObjectForBeanInstance(bean, name);
    }

    private Object getObjectForBeanInstance(Object beanInstance, String beanName) {
        // 判断：是否为 FactoryBean 对象，如果不是则不需要代理
        if (!(beanInstance instanceof FactoryBean)) {
            return beanInstance;
        }

        Object object = getCachedObjectForFactoryBean(beanName);

        if (object == null) {
            FactoryBean<?> factoryBean = (FactoryBean<?>) beanInstance;
            object = getObjectFromFactoryBean(factoryBean, beanName);
        }

        return object;
    }

    /**
     * 获取bean定义
     * @param beanName  bean名称
     * @return          bean定义
     */
    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    /**
     * 初始化bean对象
     * @param beanName          bean名称
     * @param beanDefinition    bean定义
     * @param args              实例化参数
     * @return bean对象
     */
    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException;

    /**
     * 添加 Bean 后置处理器
     *
     * @param beanPostProcessor Bean 后置处理器
     */
    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    /**
     * Return the list of BeanPostProcessors that will get applied
     * to beans created with this factory.
     */
    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }

    @Override
    public void addEmbeddedValueResolver(StringValueResolver valueResolver) {
        this.embeddedValueResolvers.add(valueResolver);
    }

    @Override
    public String resolveEmbeddedValue(String value) {
        String result = value;
        for (StringValueResolver resolver : this.embeddedValueResolvers) {
            result = resolver.resolveStringValue(result);
        }
        return result;
    }

    @Override
    public boolean containsBean(String name) {
        return containsBeanDefinition(name);
    }

    protected abstract boolean containsBeanDefinition(String beanName);

    @Override
    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public ConversionService getConversionService() {
        return conversionService;
    }
}
