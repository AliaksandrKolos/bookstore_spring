package com.kolos.bookstore.data.repository;

import java.util.List;

public interface AbstractRepository <K, T> {

    T find(K id);

    List<T> findAll(int offset, int limit);

    T save(T entity);

    T update(T entity);

    boolean delete(K id);
}
