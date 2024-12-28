package com.iflove.simplespring.jdbc.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 * One of the two central callback interfaces used by the JdbcTemplate class.
 * This interface creates a PreparedStatement given a connection, provided
 * by the JdbcTemplate class. Implementations are responsible for providing
 * SQL and any necessary parameters.
 */

public interface PreparedStatementCreator {

    /**
     * Create a statement in this connection. Allows implementations to use
     * PreparedStatements. The JdbcTemplate will close the created statement.
     * @param con the connection used to create statement
     * @return a prepared statement
     * @throws SQLException there is no need to catch SQLExceptions
     * that may be thrown in the implementation of this method.
     * The JdbcTemplate class will handle them.
     */
    PreparedStatement createPreparedStatement(Connection con) throws SQLException;
}
