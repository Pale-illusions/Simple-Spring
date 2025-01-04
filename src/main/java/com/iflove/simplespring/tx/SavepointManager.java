package com.iflove.simplespring.tx;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

public interface SavepointManager {

    Object createSavepoint() throws TransactionException;

    void rollbackToSavepoint(Object savepoint) throws TransactionException;

    void releaseSavepoint(Object savepoint) throws TransactionException;

}
