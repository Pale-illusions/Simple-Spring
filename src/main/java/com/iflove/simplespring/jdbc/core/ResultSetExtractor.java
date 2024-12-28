package com.iflove.simplespring.jdbc.core;

import com.iflove.simplespring.jdbc.support.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 * Callback interface used by {@link JdbcTemplate}'s query methods.
 * Implementations of this interface perform the actual work of extracting
 * results from a {@link ResultSet}, but don't need to worry
 * about exception handling. {@link SQLException SQLExceptions}
 * will be caught and handled by the calling JdbcTemplate.
 */

public interface ResultSetExtractor<T> {

    /**
     * Implementations must implement this method to process the entire ResultSet.
     * @param rs the ResultSet to extract data from. Implementations should
     * not close this: it will be closed by the calling JdbcTemplate.
     * @return an arbitrary result object, or {@code null} if none
     * (the extractor will typically be stateful in the latter case).
     * @throws SQLException if an SQLException is encountered getting column
     * values or navigating (that is, there's no need to catch SQLException)
     */
    T extractData(ResultSet rs) throws SQLException;
}

