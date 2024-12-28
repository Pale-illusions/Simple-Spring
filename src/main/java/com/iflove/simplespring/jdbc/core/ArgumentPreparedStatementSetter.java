package com.iflove.simplespring.jdbc.core;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 * Simple adapter for {@link PreparedStatementSetter} that applies a given array
 * of arguments.
 */

public class ArgumentPreparedStatementSetter implements PreparedStatementSetter {

    private final Object[] args;

    public ArgumentPreparedStatementSetter(Object[] args) {
        this.args = args;
    }

    /**
     * 给 PreparedStatement 的占位符设置 Value
     * @param ps the PreparedStatement
     * @throws SQLException if thrown by PreparedStatement methods
     */
    @Override
    public void setValues(PreparedStatement ps) throws SQLException {
        if (null != args) {
            for (int i = 1; i <= args.length; i++) {
                ps.setObject(i, args[i - 1]);
            }
        }
    }
}
