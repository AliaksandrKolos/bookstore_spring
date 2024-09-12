package com.kolos.bookstore.data.dao.impl;

import com.kolos.bookstore.data.dao.OrderDao;
import com.kolos.bookstore.data.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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
public class OrderDaoImpl implements OrderDao {


    private static final String FIND_BY_ID = """
            SELECT o.id, o.user_id, s.status_name, o.cost AS totalCost
            FROM orders o
            JOIN status s ON o.status_id = s.id
            WHERE o.id = ?
            """;

    private static final String FIND_ALL_ORDER_PAGES = """
            SELECT o.id, o.user_id, s.status_name, o.cost AS totalCost
            FROM orders o
            JOIN status s ON o.status_id = s.id
            ORDER BY o.id
            LIMIT :limit OFFSET :offset
            """;

    private static final String CREATE_ORDER = """
            INSERT INTO orders (user_id, cost, status_id)
            VALUES (?, ?, (SELECT id FROM status WHERE status_name = 'PENDING'))
            """;


    private static final String UPDATE_ORDER = """
            UPDATE orders
            SET user_id = :user_id,
                cost = :cost,
                status_id = (SELECT id FROM status WHERE status_name = :status_name)
            WHERE id = :id
            """;

    private static final String UPDATE_ORDER_STATUS_BY_ID = """
            UPDATE orders
            SET status_id = (SELECT id FROM status WHERE status_name = 'CANCELLED')
            WHERE id = ?
            """;


    private static final String FIND_BY_USER_ID = """
                SELECT o.id, o.user_id, s.status_name, o.cost AS totalCost
                FROM orders o
                JOIN status s ON o.status_id = s.id
                WHERE o.user_id = :id
                ORDER BY o.id
                LIMIT :limit OFFSET :offset
            """;

    private static final String COUNT_ALL = """
            SELECT COUNT(o.id) as totalCount
            FROM orders o
            """;

    private static final String COUNT_ALL_MESSAGE = """
            SELECT COUNT(o.id) as totalCount
            FROM orders o
            WHERE o.user_id = ?
            """;


    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Override
    public OrderDto find(Long id) {
        return jdbcTemplate.queryForObject(FIND_BY_ID, this::mapRow, id);
    }

    @Override
    public List<OrderDto> findByUserId(Long id, int limit, int offset) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        params.addValue("limit", limit);
        params.addValue("offset", offset);
        return namedParameterJdbcTemplate
                .queryForStream(FIND_BY_USER_ID, params, this::mapRow)
                .toList();
    }

    @Override
    public int countAll() {
        return jdbcTemplate.queryForObject(COUNT_ALL, Integer.class);
    }

    @Override
    public int countAll(Long id) {
        return jdbcTemplate.queryForObject(COUNT_ALL_MESSAGE, Integer.class, id);
    }


    @Override
    public List<OrderDto> findAll(int limit, int offset) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("limit", limit);
        params.addValue("offset", offset);
        return namedParameterJdbcTemplate
                .queryForStream(FIND_ALL_ORDER_PAGES, params, this::mapRow)
                .toList();
    }


    @Override
    public OrderDto save(OrderDto dto) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(CREATE_ORDER, new String[]{"id"});
            ps.setLong(1, dto.getUserId());
            ps.setBigDecimal(2, dto.getCost());
            return ps;
        }, keyHolder);
        Number key = keyHolder.getKey();
        if (key != null) {
            return find(key.longValue());
        }
        return null;
    }


    @Override
    public OrderDto update(OrderDto dto) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("user_id", dto.getUserId());
        params.addValue("cost", dto.getCost());
        params.addValue("status_name", dto.getStatus().name());
        params.addValue("id", dto.getId());
        namedParameterJdbcTemplate.update(UPDATE_ORDER, params);
        return find(dto.getId());
    }


    @Override
    public boolean delete(Long id) {
        int countRow = jdbcTemplate.update(UPDATE_ORDER_STATUS_BY_ID, id);
        return countRow == 1;
    }


    private OrderDto mapRow(ResultSet resultSet, int row) throws SQLException {
        OrderDto dto = new OrderDto();
        dto.setId(resultSet.getLong("id"));
        dto.setCost(resultSet.getBigDecimal("totalCost"));
        dto.setStatus(OrderDto.Status.valueOf(resultSet.getString("status_name")));
        dto.setUserId(resultSet.getLong("user_id"));
        return dto;
    }


}
