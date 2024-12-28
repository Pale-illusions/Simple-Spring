package com.iflove.simplespring.jdbc.datasource;

import cn.hutool.core.lang.Assert;

import java.sql.Connection;

public class SimpleConnectionHandler implements ConnectionHandler {

    private final Connection connection;

    public SimpleConnectionHandler(Connection connection) {
        Assert.notNull(connection, "Connection must not be null");
        this.connection = connection;
    }

    @Override
    public Connection getConnection() {
        return this.connection;
    }

}
