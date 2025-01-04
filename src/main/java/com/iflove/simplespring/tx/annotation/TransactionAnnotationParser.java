package com.iflove.simplespring.tx.annotation;

import com.iflove.simplespring.tx.interceptor.TransactionAttribute;

import java.lang.reflect.AnnotatedElement;

public interface TransactionAnnotationParser {

    /**
     * 解析 事务注解
     */
    TransactionAttribute parseTransactionAnnotation(AnnotatedElement element);
}
