package com.iflove.simplespring.jdbc;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

public class UncategorizedSQLException extends RuntimeException{


    public UncategorizedSQLException(String message) {
        super(message);
    }

    public UncategorizedSQLException(String task,String sql, Throwable cause) {
        super(sql, cause);
    }
}

