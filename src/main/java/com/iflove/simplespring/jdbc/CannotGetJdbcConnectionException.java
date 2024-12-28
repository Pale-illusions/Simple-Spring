package com.iflove.simplespring.jdbc;

import java.sql.SQLException;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

public class CannotGetJdbcConnectionException extends RuntimeException {

    public CannotGetJdbcConnectionException(String message) {
        super(message);
    }

    public CannotGetJdbcConnectionException(String message, SQLException ex) {
        super(message, ex);
    }
}


