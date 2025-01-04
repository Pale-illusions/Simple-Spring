package com.iflove.simplespring.tx;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

public interface PlatformTransactionManager extends TransactionManager {

    TransactionStatus getTransaction(TransactionDefinition definition) throws TransactionException;

    void commit(TransactionStatus status) throws TransactionException;

    void rollback(TransactionStatus status) throws TransactionException;

}
