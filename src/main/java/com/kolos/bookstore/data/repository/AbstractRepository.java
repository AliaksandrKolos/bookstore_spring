package com.kolos.bookstore.data.repository;

import java.util.List;

public interface AbstractRepository <K, T> {

    T find(K id);

    List<T> findAll(long offset, long limit);

    T save(T entity);

    boolean delete(K id);
}
