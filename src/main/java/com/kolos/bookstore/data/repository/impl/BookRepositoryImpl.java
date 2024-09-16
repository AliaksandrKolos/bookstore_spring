package com.kolos.bookstore.data.repository.impl;

import com.kolos.bookstore.data.entity.Book;
import com.kolos.bookstore.data.repository.BookRepository;
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
public class BookRepositoryImpl implements BookRepository {

    @PersistenceContext
    private EntityManager manager;


    @Override
    public List<Book> findByAuthor(String author, long limit, long offset) {
        CriteriaBuilder cb = manager.getCriteriaBuilder();

        CriteriaQuery<Book> cq = cb.createQuery(Book.class);
        Root<Book> root = cq.from(Book.class);
        cq.where(
                cb.and(
                        cb.equal(root.get("author"), author),
                        cb.equal(root.get("deleted"), false)

                )
        );
        TypedQuery<Book> query = manager.createQuery(cq);
        return query.setFirstResult((int) offset).setMaxResults((int) limit).getResultList();
    }

    @Override
    public long countAll() {
        TypedQuery<Long> query = manager.createQuery("SELECT COUNT(*) FROM Book b WHERE b.deleted = false ", Long.class);
        return query.getSingleResult();
    }


    @Override
    public int countAll(String messages) {
        CriteriaBuilder cb = manager.getCriteriaBuilder();

        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Book> root = cq.from(Book.class);

        cq.select(cb.count(root)).where(
                cb.and(
                        cb.equal(root.get("deleted"), false),
                        cb.like(root.get("title"), "%" + messages + "%")
                )
        );
        TypedQuery<Long> query = manager.createQuery(cq);
        return query.getSingleResult().intValue();
    }


    @Override
    public List<Book> findAllByTitle(String title, long limit, long offset) {
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<Book> cq = cb.createQuery(Book.class);
        Root<Book> root = cq.from(Book.class);
        cq.where(
                cb.and(
                        cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%"),
                        cb.equal(root.get("deleted"), false)
                )
        );
        TypedQuery<Book> query = manager.createQuery(cq);
        return query.setFirstResult((int) offset).setMaxResults((int) limit).getResultList();
    }

    @Override
    public List<Book> findAll(long limit, long offset) {
        CriteriaBuilder cb = manager.getCriteriaBuilder();

        CriteriaQuery<Book> cq = cb.createQuery(Book.class);
        Root<Book> root = cq.from(Book.class);
        cq.select(root).where(cb.equal(root.get("deleted"), false));
        TypedQuery<Book> query = manager.createQuery(cq);
        return query.setFirstResult((int) offset).setMaxResults((int) limit).getResultList();
    }


    @Override
    public Book findByIsbn(String isbn) {
        return manager.createQuery("FROM Book b WHERE b.isbn = :isbn AND b.deleted = false", Book.class)
                .setParameter("isbn", isbn)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean delete(Long id) {
        Book book = manager.find(Book.class, id);
        if (book != null) {
            book.setDeleted(true);
            return true;
        }
        return false;
    }

    @Override
    public Book find(Long id) {
        return manager.createQuery("FROM Book b WHERE b.id = :id AND b.deleted = false", Book.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }


    @Override
    public Book save(Book entity) {
        if (entity.getId() == null) {
            manager.persist(entity);
        } else {
            manager.merge(entity);
        }
        return entity;
    }
}
