package com.kolos.bookstore.data.connection.impl;

import com.kolos.bookstore.data.connection.ConnectionManager;
import com.kolos.bookstore.platform.ConfigurationManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;

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
    private int poolSize;

    public ConnectionManagerImpl(ConfigurationManager configurationManager) {
        this.url = configurationManager.getProperty("db.url");
        this.password = configurationManager.getProperty("db.password");
        this.user = configurationManager.getProperty("db.user");
        this.driver = configurationManager.getProperty("db.driver");
        this.poolSize = Integer.parseInt(configurationManager.getProperty("db.poolSize"));
        connectionPool = new ConnectionPool(driver, url, user, password, poolSize);
        log.info("Connection pool initialized");
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
