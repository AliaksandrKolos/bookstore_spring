package com.kolos.bookstore.data.dao;

import java.util.List;

public interface AbstractDao<K, T> {

    T find(K id);

    List<T> findAll(int size, int offset);

    T save(T entity);

    T update(T entity);

    boolean delete(K id);

}
