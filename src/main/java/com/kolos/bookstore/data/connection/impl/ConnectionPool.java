package com.kolos.bookstore.data.connection.impl;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

@Slf4j
class ConnectionPool {
    private final int poolSize;
    private final BlockingDeque<ProxyConnection> freeConnections;
    private final Queue<ProxyConnection> givenConnections;

    ConnectionPool(String driver, String url, String user, String password, int poolSize) {
        this.poolSize = poolSize;
        freeConnections = new LinkedBlockingDeque<>(this.poolSize);
        givenConnections = new ArrayDeque<>();
        try {
            Class.forName(driver);
            log.info("Driver loaded");
            for (int i = 0; i < this.poolSize; i++) {
                Connection connection = DriverManager.getConnection(url, user, password);
                freeConnections.offer(new ProxyConnection(connection, this));
                log.info("Added connection");
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = freeConnections.take();
            givenConnections.offer(connection);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        if (connection instanceof ProxyConnection proxy && givenConnections.remove(connection)) {
            freeConnections.offer(proxy);
        } else {
            throw new RuntimeException();
        }
    }

    public void destroyPool() {
        for (int i = 0; i < poolSize; i++) {
            try {
                freeConnections.take().reallyClose();
                log.info("Removed connection");
            } catch (InterruptedException | SQLException e) {
                throw new RuntimeException(e);
            }
        }
        deregisterDrivers();
    }

    private void deregisterDrivers() {
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
                log.info("Driver={} deregistered", driver);
            } catch (SQLException e) {
                log.info("driver deregistered failed");
            }
        });
    }
}
