package com.iflove.simplespring.tx;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

public class NestedTransactionNotSupportedException extends CannotCreateTransactionException {
    public NestedTransactionNotSupportedException(String message) {
        super(message);
    }

    public NestedTransactionNotSupportedException(String message, Throwable cause) {
        super(message, cause);
    }
}
