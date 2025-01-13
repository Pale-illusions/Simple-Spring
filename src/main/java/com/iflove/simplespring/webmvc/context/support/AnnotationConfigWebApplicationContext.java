package com.iflove.simplespring.webmvc.context.support;

import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.*;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.support.AbstractRefreshableWebApplicationContext;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote 不使用，仅作展示
 */

public class AnnotationConfigWebApplicationContext extends AbstractRefreshableWebApplicationContext implements AnnotationConfigRegistry {

    @Nullable
    private BeanNameGenerator beanNameGenerator;

    @Nullable
    private ScopeMetadataResolver scopeMetadataResolver;

    private final Set<Class<?>> componentClasses = new LinkedHashSet<>();

    private final Set<String> basePackages = new LinkedHashSet<>();


    /**
     * Set a custom {@link BeanNameGenerator} for use with {@link AnnotatedBeanDefinitionReader}
     * and/or {@link ClassPathBeanDefinitionScanner}.
     * <p>Default is {@link org.springframework.context.annotation.AnnotationBeanNameGenerator}.
     * @see AnnotatedBeanDefinitionReader#setBeanNameGenerator
     * @see ClassPathBeanDefinitionScanner#setBeanNameGenerator
     */
    public void setBeanNameGenerator(@Nullable BeanNameGenerator beanNameGenerator) {
        this.beanNameGenerator = beanNameGenerator;
    }

    /**
     * Return the custom {@link BeanNameGenerator} for use with {@link AnnotatedBeanDefinitionReader}
     * and/or {@link ClassPathBeanDefinitionScanner}, if any.
     */
    @Nullable
    protected BeanNameGenerator getBeanNameGenerator() {
        return this.beanNameGenerator;
    }

    /**
     * Set a custom {@link ScopeMetadataResolver} for use with {@link AnnotatedBeanDefinitionReader}
     * and/or {@link ClassPathBeanDefinitionScanner}.
     * <p>Default is an {@link org.springframework.context.annotation.AnnotationScopeMetadataResolver}.
     * @see AnnotatedBeanDefinitionReader#setScopeMetadataResolver
     * @see ClassPathBeanDefinitionScanner#setScopeMetadataResolver
     */
    public void setScopeMetadataResolver(@Nullable ScopeMetadataResolver scopeMetadataResolver) {
        this.scopeMetadataResolver = scopeMetadataResolver;
    }

    /**
     * Return the custom {@link ScopeMetadataResolver} for use with {@link AnnotatedBeanDefinitionReader}
     * and/or {@link ClassPathBeanDefinitionScanner}, if any.
     */
    @Nullable
    protected ScopeMetadataResolver getScopeMetadataResolver() {
        return this.scopeMetadataResolver;
    }


    /**
     * Register one or more component classes to be processed.
     * <p>Note that {@link #refresh()} must be called in order for the context
     * to fully process the new classes.
     * @param componentClasses one or more component classes,
     * e.g. {@link org.springframework.context.annotation.Configuration @Configuration} classes
     * @see #scan(String...)
     * @see #loadBeanDefinitions(DefaultListableBeanFactory)
     * @see #setConfigLocation(String)
     * @see #refresh()
     */
    @Override
    public void register(Class<?>... componentClasses) {
        Collections.addAll(this.componentClasses, componentClasses);
    }

    /**
     * Perform a scan within the specified base packages.
     * <p>Note that {@link #refresh()} must be called in order for the context
     * to fully process the new classes.
     * @param basePackages the packages to check for component classes
     * @see #loadBeanDefinitions(DefaultListableBeanFactory)
     * @see #register(Class...)
     * @see #setConfigLocation(String)
     * @see #refresh()
     */
    @Override
    public void scan(String... basePackages) {
        Collections.addAll(this.basePackages, basePackages);
    }


    /**
     * Register a {@link org.springframework.beans.factory.config.BeanDefinition} for
     * any classes specified by {@link #register(Class...)} and scan any packages
     * specified by {@link #scan(String...)}.
     * <p>For any values specified by {@link #setConfigLocation(String)} or
     * {@link #setConfigLocations(String[])}, attempt first to load each location as a
     * class, registering a {@code BeanDefinition} if class loading is successful,
     * and if class loading fails (i.e. a {@code ClassNotFoundException} is raised),
     * assume the value is a package and attempt to scan it for component classes.
     * <p>Enables the default set of annotation configuration post processors, such that
     * {@code @Autowired} and associated annotations can be used.
     * <p>Configuration class bean definitions are registered with generated bean
     * definition names unless the {@code value} attribute is provided to the stereotype
     * annotation.
     * @param beanFactory the bean factory to load bean definitions into
     * @see #register(Class...)
     * @see #scan(String...)
     * @see #setConfigLocation(String)
     * @see #setConfigLocations(String[])
     * @see AnnotatedBeanDefinitionReader
     * @see ClassPathBeanDefinitionScanner
     */
    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) {
        AnnotatedBeanDefinitionReader reader = getAnnotatedBeanDefinitionReader(beanFactory);
        ClassPathBeanDefinitionScanner scanner = getClassPathBeanDefinitionScanner(beanFactory);

        BeanNameGenerator beanNameGenerator = getBeanNameGenerator();
        if (beanNameGenerator != null) {
            reader.setBeanNameGenerator(beanNameGenerator);
            scanner.setBeanNameGenerator(beanNameGenerator);
            beanFactory.registerSingleton(AnnotationConfigUtils.CONFIGURATION_BEAN_NAME_GENERATOR, beanNameGenerator);
        }

        ScopeMetadataResolver scopeMetadataResolver = getScopeMetadataResolver();
        if (scopeMetadataResolver != null) {
            reader.setScopeMetadataResolver(scopeMetadataResolver);
            scanner.setScopeMetadataResolver(scopeMetadataResolver);
        }

        if (!this.componentClasses.isEmpty()) {
            if (logger.isDebugEnabled()) {
                logger.debug("Registering component classes: [" +
                        StringUtils.collectionToCommaDelimitedString(this.componentClasses) + "]");
            }
            reader.register(ClassUtils.toClassArray(this.componentClasses));
        }

        if (!this.basePackages.isEmpty()) {
            if (logger.isDebugEnabled()) {
                logger.debug("Scanning base packages: [" +
                        StringUtils.collectionToCommaDelimitedString(this.basePackages) + "]");
            }
            scanner.scan(StringUtils.toStringArray(this.basePackages));
        }

        String[] configLocations = getConfigLocations();
        if (configLocations != null) {
            for (String configLocation : configLocations) {
                try {
                    Class<?> clazz = ClassUtils.forName(configLocation, getClassLoader());
                    if (logger.isTraceEnabled()) {
                        logger.trace("Registering [" + configLocation + "]");
                    }
                    reader.register(clazz);
                }
                catch (ClassNotFoundException ex) {
                    if (logger.isTraceEnabled()) {
                        logger.trace("Could not load class for config location [" + configLocation +
                                "] - trying package scan. " + ex);
                    }
                    int count = scanner.scan(configLocation);
                    if (count == 0 && logger.isDebugEnabled()) {
                        logger.debug("No component classes found for specified class/package [" + configLocation + "]");
                    }
                }
            }
        }
    }


    /**
     * Build an {@link AnnotatedBeanDefinitionReader} for the given bean factory.
     * <p>This should be pre-configured with the {@code Environment} (if desired)
     * but not with a {@code BeanNameGenerator} or {@code ScopeMetadataResolver} yet.
     * @param beanFactory the bean factory to load bean definitions into
     * @since 4.1.9
     * @see #getEnvironment()
     * @see #getBeanNameGenerator()
     * @see #getScopeMetadataResolver()
     */
    protected AnnotatedBeanDefinitionReader getAnnotatedBeanDefinitionReader(DefaultListableBeanFactory beanFactory) {
        return new AnnotatedBeanDefinitionReader(beanFactory, getEnvironment());
    }

    /**
     * Build a {@link ClassPathBeanDefinitionScanner} for the given bean factory.
     * <p>This should be pre-configured with the {@code Environment} (if desired)
     * but not with a {@code BeanNameGenerator} or {@code ScopeMetadataResolver} yet.
     * @param beanFactory the bean factory to load bean definitions into
     * @since 4.1.9
     * @see #getEnvironment()
     * @see #getBeanNameGenerator()
     * @see #getScopeMetadataResolver()
     */
    protected ClassPathBeanDefinitionScanner getClassPathBeanDefinitionScanner(DefaultListableBeanFactory beanFactory) {
        return new ClassPathBeanDefinitionScanner(beanFactory, true, getEnvironment());
    }

}
