package com.iflove.simplespring.tx;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

public class CannotCreateTransactionException extends TransactionException {

    public CannotCreateTransactionException(String message) {
        super(message);
    }

    public CannotCreateTransactionException(String message, Throwable cause) {
        super(message, cause);
    }
}
