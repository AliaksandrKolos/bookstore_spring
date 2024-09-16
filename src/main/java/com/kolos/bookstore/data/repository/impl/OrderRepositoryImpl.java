package com.kolos.bookstore.data.repository.impl;

import com.kolos.bookstore.data.entity.Order;
import com.kolos.bookstore.data.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Repository
public class OrderRepositoryImpl implements OrderRepository {


    @PersistenceContext
    private EntityManager manager;

    @Override
    public long countAll() {
        TypedQuery<Long> query = manager.createQuery("SELECT COUNT(*) FROM Order", Long.class);
        return query.getSingleResult();
    }

    @Override
    public long countAllById(Long id) {
        CriteriaBuilder cb = manager.getCriteriaBuilder();

        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Order> root = cq.from(Order.class);
        cq.select(cb.count(root)).where(cb.equal(root.get("user").get("id"), id));

        TypedQuery<Long> query = manager.createQuery(cq);
        return query.getSingleResult();
    }

    @Override
    public List<Order> findAll(long limit, long offset) {
        CriteriaBuilder cb = manager.getCriteriaBuilder();

        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> root = cq.from(Order.class);
        cq.select(root);
        TypedQuery<Order> query = manager.createQuery(cq);
        return query.setFirstResult((int) offset).setMaxResults((int) limit).getResultList();
    }


    @Override
    public List<Order> findByUserId(Long id, long limit, long offset) {
        CriteriaBuilder cb = manager.getCriteriaBuilder();

        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> root = cq.from(Order.class);
        cq.where(cb.equal(root.get("user").get("id"), id));

        TypedQuery<Order> query = manager.createQuery(cq);
        return query.setFirstResult((int) offset).setMaxResults((int) limit).getResultList();
    }


    @Override
    public boolean delete(Long id) {
        Order order = manager.find(Order.class, id);
        if (order != null) {
            order.setStatus(Order.Status.CANCELLED);
            return true;
        }
        return false;
    }


    @Override
    public Order save(Order entity) {
        if (entity.getId() == null) {
            manager.persist(entity);
        } else {
            manager.merge(entity);
        }
        return entity;
    }


    @Override
    public Order find(Long id) {
        return manager.createQuery("FROM Order WHERE id = :id", Order.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }
}
