package com.iflove.simplespring.jdbc.core;

import com.iflove.simplespring.jdbc.support.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 * General callback interface used by the {@link JdbcTemplate} class.
 */

public interface PreparedStatementSetter {

    /**
     * Set parameter values on the given PreparedStatement.
     * @param ps the PreparedStatement to invoke setter methods on
     * @throws SQLException if an SQLException is encountered
     * (i.e. there is no need to catch SQLException)
     */
    void setValues(PreparedStatement ps) throws SQLException;
}
