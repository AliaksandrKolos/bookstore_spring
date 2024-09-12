package com.kolos.bookstore.data.repository;

import com.kolos.bookstore.data.entity.Order;

import java.util.List;

public interface OrderRepository extends AbstractRepository<Long, Order> {

    List<Order> findByUserId(Long userId, int limit, int offset);

    int countAll();

    int countAllMassage(Long id);

}
