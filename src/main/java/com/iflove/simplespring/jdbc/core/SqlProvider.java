package com.iflove.simplespring.jdbc.core;

import org.jetbrains.annotations.Nullable;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote Interface to be implemented by objects that can provide SQL strings.
 */

public interface SqlProvider {

    /**
     * Return the SQL string for this object, i.e.
     * typically the SQL used for creating statements.
     * @return the SQL string, or {@code null} if not available
     */
    @Nullable
    String getSql();
}
