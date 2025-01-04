package com.iflove.simplespring.tx;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

public class TransactionException extends RuntimeException {
    public TransactionException(String message) {
        super(message);
    }

    public TransactionException(String message, Throwable cause) {
        super(message, cause);
    }
}
