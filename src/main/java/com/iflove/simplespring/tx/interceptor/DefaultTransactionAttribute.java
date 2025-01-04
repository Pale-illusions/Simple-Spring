package com.iflove.simplespring.tx.interceptor;


import com.iflove.simplespring.tx.support.DefaultTransactionDefinition;

public class DefaultTransactionAttribute extends DefaultTransactionDefinition implements TransactionAttribute {


    public DefaultTransactionAttribute() {
        super();
    }


    @Override
    public boolean rollbackOn(Throwable ex) {
        return (ex instanceof RuntimeException || ex instanceof Error);
    }

    @Override
    public String toString() {
        return "DefaultTransactionAttribute{}";
    }
}
