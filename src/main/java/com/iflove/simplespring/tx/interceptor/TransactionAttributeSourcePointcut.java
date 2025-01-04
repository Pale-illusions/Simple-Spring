package com.iflove.simplespring.tx.interceptor;


import com.iflove.simplespring.aop.support.StaticMethodMatcherPointcut;
import com.iflove.simplespring.tx.PlatformTransactionManager;
import java.io.Serializable;
import java.lang.reflect.Method;

public abstract class TransactionAttributeSourcePointcut extends StaticMethodMatcherPointcut implements Serializable {

    @Override
    public boolean matches(Method method, Class<?> clazz) {
        if (PlatformTransactionManager.class.isAssignableFrom(clazz)) {
            return false;
        }

        TransactionAttributeSource tas = getTransactionAttributeSource();

        return null == tas || tas.getTransactionAttribute(method, clazz) != null;
    }


    //---------------------------------------------------------------------
    // Abstract methods to be implemented by subclasses start
    //---------------------------------------------------------------------

    protected abstract TransactionAttributeSource getTransactionAttributeSource();

    //---------------------------------------------------------------------
    // Abstract methods to be implemented by subclasses end
    //---------------------------------------------------------------------
}
