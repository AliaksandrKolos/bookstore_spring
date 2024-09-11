package com.kolos.bookstore.data.connection.impl;

import com.kolos.bookstore.data.connection.ConnectionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;

@Component
@Slf4j
public class ConnectionManagerImpl implements Closeable, ConnectionManager {

    private ConnectionPool connectionPool;
    private final String url;
    private final String password;
    private final String user;
    private final String driver;
    private int poolSize;

    public ConnectionManagerImpl(
            @Value("${db.url}") String url,
            @Value("${db.password}") String password,
            @Value("${db.user}") String user,
            @Value("${db.driver}") String driver,
            @Value("${db.poolSize}") int poolSize) {
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
