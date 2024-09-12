package com.kolos.bookstore.data.dao.impl;

import com.kolos.bookstore.data.dao.OrderItemDao;
import com.kolos.bookstore.data.dto.OrderItemDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository
public class OrderItemDaoImpl implements OrderItemDao {


    private static final String FIND_BY_ID = """
            SELECT id, book_id, quantity, price, order_id
            FROM order_items
            WHERE id = ?;
            """;

    private static final String FIND_BY_ORDER_ID = """
            SELECT id, book_id, quantity, price, order_id
            FROM order_items
            WHERE order_id = ?
            """;

    private static final String FIND_ALL_ORDER_ITEM = """
            SELECT id, book_id, quantity, price, order_id
            FROM order_items
            """;

    private static final String DELETE_BY_ID = """
            DELETE FROM order_items
            WHERE id = ?
            """;

    private static final String CREATE_ORDER_ITEM = """
            INSERT INTO order_items (book_id, quantity, price, order_id)
            VALUES (?, ?, ?, ?);
            """;

    private final JdbcTemplate jdbcTemplate;

    @Override
    public OrderItemDto find(Long id) {
        return jdbcTemplate.queryForObject(FIND_BY_ID, this::mapRow, id);
    }


    @Override
    public List<OrderItemDto> findAll() {
        return jdbcTemplate.query(FIND_ALL_ORDER_ITEM, this::mapRow);
    }

    @Override
    public OrderItemDto save(OrderItemDto dto) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(CREATE_ORDER_ITEM, new String[]{"id"});
            ps.setLong(1, dto.getBookId());
            ps.setLong(2, dto.getQuantity());
            ps.setBigDecimal(3, dto.getPrice());
            ps.setLong(4, dto.getOrderId());
            return ps;
        }, keyHolder);
        Number key = keyHolder.getKey();
        if (key != null) {
            return find(key.longValue());
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        int countRow = jdbcTemplate.update(DELETE_BY_ID, id);
        return countRow == 1;
    }

    @Override
    public List<OrderItemDto> findByOrderId(Long orderId) {
        return jdbcTemplate.query(FIND_BY_ORDER_ID, this::mapRow, orderId);

    }


    private OrderItemDto mapRow(ResultSet resultSet, int row) throws SQLException {
        OrderItemDto dto = new OrderItemDto();
        dto.setId(resultSet.getLong("id"));
        dto.setBookId(resultSet.getLong("book_id"));
        dto.setQuantity(resultSet.getInt("quantity"));
        dto.setPrice(resultSet.getBigDecimal("price"));
        dto.setOrderId(resultSet.getLong("order_id"));
        return dto;
    }

}
