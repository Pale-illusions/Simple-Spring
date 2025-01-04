package com.iflove.simplespring.tx.interceptor;

import com.iflove.simplespring.tx.TransactionDefinition;

public interface TransactionAttribute extends TransactionDefinition {

    boolean rollbackOn(Throwable ex);
}
