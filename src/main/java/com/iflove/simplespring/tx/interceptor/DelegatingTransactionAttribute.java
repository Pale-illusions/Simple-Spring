package com.iflove.simplespring.tx.interceptor;


import com.iflove.simplespring.tx.support.DelegatingTransactionDefinition;

import java.io.Serializable;

public abstract class DelegatingTransactionAttribute extends DelegatingTransactionDefinition implements TransactionAttribute, Serializable {

    private final TransactionAttribute targetAttribute;


    public DelegatingTransactionAttribute(TransactionAttribute targetAttribute) {
        super(targetAttribute);
        this.targetAttribute = targetAttribute;
    }


    @Override
    public boolean rollbackOn(Throwable ex) {
        return this.targetAttribute.rollbackOn(ex);
    }
}
