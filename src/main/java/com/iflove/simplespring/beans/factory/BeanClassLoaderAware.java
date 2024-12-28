package com.iflove.simplespring.beans.factory;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 * Callback that allows a bean to be aware of the bean
 * {@link ClassLoader class loader}; that is, the class loader used by the
 * present bean factory to load bean classes.
 */

public interface BeanClassLoaderAware extends Aware {
    void setBeanClassLoader(ClassLoader classLoader);
}
