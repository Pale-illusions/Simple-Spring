package com.iflove.simplespring.tx.annotation;

import com.iflove.simplespring.tx.interceptor.AbstractFallbackTransactionAttributeSource;
import com.iflove.simplespring.tx.interceptor.TransactionAttribute;

import java.io.Serializable;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Set;

public class AnnotationTransactionAttributeSource extends AbstractFallbackTransactionAttributeSource
        implements Serializable {


    private final boolean publicMethodsOnly;

    private final Set<TransactionAnnotationParser> annotationParsers;

    public AnnotationTransactionAttributeSource() {
        this(true);
    }

    public AnnotationTransactionAttributeSource(boolean publicMethodsOnly) {
        this.publicMethodsOnly = publicMethodsOnly;
        this.annotationParsers = Collections.singleton(new SpringTransactionAnnotationParser());
    }

    @Override
    protected TransactionAttribute findTransactionAttribute(Method method) {
        return determineTransactionAttribute(method);
    }

    @Override
    public TransactionAttribute findTransactionAttribute(Class<?> clazz) {
        return determineTransactionAttribute(clazz);
    }

    protected TransactionAttribute determineTransactionAttribute(AnnotatedElement element) {
        for (TransactionAnnotationParser annotationParser : this.annotationParsers) {
            TransactionAttribute attr = annotationParser.parseTransactionAnnotation(element);
            if (attr != null) {
                return attr;
            }
        }
        return null;
    }
}
