package com.iflove.simplespring.tx.interceptor;

import com.iflove.simplespring.tx.PlatformTransactionManager;
import com.iflove.simplespring.utils.ClassUtils;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.io.Serializable;

public class TransactionInterceptor extends TransactionAspectSupport
        implements MethodInterceptor, Serializable {

    public TransactionInterceptor(PlatformTransactionManager ptm, TransactionAttributeSource transactionAttributeSource) {
        setTransactionManager(ptm);
        setTransactionAttributeSource(transactionAttributeSource);
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Class<?> target = invocation.getThis().getClass();
        target = ClassUtils.isCglibProxyClass(target) ? target.getSuperclass() : target;
        return invokeWithinTransaction(invocation.getMethod(),
                target, invocation::proceed);
    }
}
