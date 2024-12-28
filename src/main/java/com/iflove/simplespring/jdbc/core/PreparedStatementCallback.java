package com.iflove.simplespring.jdbc.core;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 * Generic callback interface for code that operates on a PreparedStatement.
 * Allows to execute any number of operations on a single PreparedStatement,
 * for example a single {@code executeUpdate} call or repeated
 * {@code executeUpdate} calls with varying parameters.
 */

public interface PreparedStatementCallback<T> {

    /**
     * Gets called by {@code JdbcTemplate.execute} with an active JDBC
     * PreparedStatement. Does not need to care about closing the Statement
     * or the Connection, or about handling transactions: this will all be
     * handled by Spring's JdbcTemplate.
     */
    T doInPreparedStatement(PreparedStatement ps) throws SQLException;
}
