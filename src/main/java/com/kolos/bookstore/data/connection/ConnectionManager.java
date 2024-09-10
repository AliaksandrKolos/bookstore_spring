package com.kolos.bookstore.data.connection;

import java.io.Closeable;
import java.sql.Connection;

public interface ConnectionManager extends Closeable {

    Connection getConnection();

    void setPoolSize(int poolSize);

}
