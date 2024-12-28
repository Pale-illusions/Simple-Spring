package com.iflove.simplespring.jdbc.core;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 * Generic callback interface for code that operates on a JDBC Statement.
 * Allows to execute any number of operations on a single Statement,
 * for example a single {@code executeUpdate} call or repeated
 * {@code executeUpdate} calls with varying SQL.
 */

public interface StatementCallback<T> {

    /**
     * Gets called by {@code JdbcTemplate.execute} with an active JDBC
     * Statement. Does not need to care about closing the Statement or the
     * Connection, or about handling transactions: this will all be handled
     * by Spring's JdbcTemplate.
     */
    T doInStatement(Statement statement) throws SQLException;
}

