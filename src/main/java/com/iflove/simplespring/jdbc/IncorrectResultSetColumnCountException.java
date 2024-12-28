package com.iflove.simplespring.jdbc;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

public class IncorrectResultSetColumnCountException extends RuntimeException {

    private final int expectedCount;

    private final int actualCount;

    public IncorrectResultSetColumnCountException(int expectedCount, int actualCount) {
        super("Incorrect column count: expected " + expectedCount + ", actual " + actualCount);
        this.expectedCount = expectedCount;
        this.actualCount = actualCount;
    }
}

