package com.iflove.simplespring.jdbc.datasource;

public abstract class JdbcTransactionObjectSupport {

    private ConnectionHolder connectionHolder;



    public void setConnectionHolder(ConnectionHolder connectionHolder) {
        this.connectionHolder = connectionHolder;
    }


    public ConnectionHolder getConnectionHolder() {
        return connectionHolder;
    }

    public boolean hasConnectionHolder() {
        return null != this.connectionHolder;
    }


}
