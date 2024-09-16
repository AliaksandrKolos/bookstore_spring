package com.kolos.bookstore.data.repository.impl;

import com.kolos.bookstore.data.entity.User;
import com.kolos.bookstore.data.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Repository
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<User> findAll(long limit, long offset) {
        CriteriaBuilder cb = manager.getCriteriaBuilder();

        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        cq.select(root).where(cb.equal(root.get("deleted"), false));
        TypedQuery<User> query = manager.createQuery(cq);
        return query.setFirstResult((int) offset).setMaxResults((int) limit).getResultList();
    }

    @Override
    public List<User> findByLastName(String lastName, long limit, long offset) {
        CriteriaBuilder cb = manager.getCriteriaBuilder();

        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        cq.where(
                cb.and(
                        cb.equal(cb.lower(root.get("lastName")), lastName.toLowerCase()),
                        cb.equal(root.get("deleted"), false)
                )
        );
        TypedQuery<User> query = manager.createQuery(cq);
        return query.setFirstResult((int) offset).setMaxResults((int) limit).getResultList();
    }


    @Override
    public long countAll() {
        TypedQuery<Long> query = manager.createQuery("SELECT COUNT(*) FROM User u WHERE u.deleted = false", Long.class);
        return query.getSingleResult();
    }


    @Override
    public User findByEmail(String email) {
        return manager.createQuery("FROM User WHERE email = :email AND deleted = false", User.class)
                .setParameter("email", email)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }


    @Override
    public User save(User entity) {
        if (entity.getId() == null) {
            manager.persist(entity);
        } else {
            manager.merge(entity);
        }
        return entity;
    }

    @Override
    public User find(Long id) {
        return manager.createQuery("FROM User u WHERE u.id = :id AND u.deleted = false", User.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean delete(Long id) {
        User user = manager.find(User.class, id);
        if (user != null) {
            user.setDeleted(true);
            return true;
        }
        return false;
    }
}
