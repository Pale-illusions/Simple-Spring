package com.iflove.simplespring.jdbc.core;

import com.iflove.simplespring.jdbc.support.JdbcTemplate;
import org.jetbrains.annotations.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 * An interface used by {@link JdbcTemplate} for mapping rows of a
 * {@link ResultSet} on a per-row basis. Implementations of this
 * interface perform the actual work of mapping each row to a result object
 * but don't need to worry about exception handling.
 */

@FunctionalInterface
public interface RowMapper<T> {

    @Nullable
    T mapRow(ResultSet rs, int rowNum) throws SQLException;

}
