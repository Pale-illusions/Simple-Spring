package com.iflove.simplespring.tx.interceptor;

import java.lang.reflect.Method;

public interface TransactionAttributeSource {

    TransactionAttribute getTransactionAttribute(Method method, Class<?> targetClass);

}
