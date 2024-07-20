package com.kiss.personalproject.conn;

import jakarta.servlet.ServletRequest;

public interface Database<T> {
    boolean getConnect(ServletRequest request);
    T getTable(String schema,String tableName);
    void close();
    void rollback();
}
