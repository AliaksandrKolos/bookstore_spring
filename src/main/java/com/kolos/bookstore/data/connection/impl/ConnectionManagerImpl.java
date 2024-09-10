package com.kolos.bookstore.data.connection.impl;

import com.kolos.bookstore.data.connection.ConnectionManager;
import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;

@Slf4j
public class ConnectionManagerImpl implements Closeable, ConnectionManager {

    private ConnectionPool connectionPool;
    private final String url;
    private final String password;
    private final String user;
    private final String driver;
    private int poolSize = 16;

    public ConnectionManagerImpl(String url, String password, String user, String driver) {
        this.url = url;
        this.password = password;
        this.user = user;
        this.driver = driver;
        connectionPool = new ConnectionPool(driver, url, user, password, poolSize);
        log.info("Connection pool created");
    }

    @Override
    public Connection getConnection() {
        if (connectionPool == null) {
            connectionPool = new ConnectionPool(driver, url, user, password, poolSize);
            log.info("Connection pool created");
        }
        return connectionPool.getConnection();
    }

    @Override
    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }

    @Override
    public void close() throws IOException {

    }
}
