package com.iflove.simplespring.tx.interceptor;

import java.io.Serializable;

public class RollbackRuleAttribute implements Serializable {

    private final String exceptionName;

    public RollbackRuleAttribute(Class<?> clazz) {
        this.exceptionName = clazz.getName();
    }
}
