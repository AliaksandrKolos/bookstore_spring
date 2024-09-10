package com.kolos.bookstore.data.dao.impl;

import com.kolos.bookstore.data.connection.ConnectionManager;
import com.kolos.bookstore.data.dao.OrderDao;
import com.kolos.bookstore.data.dto.OrderDto;
import com.kolos.bookstore.data.entity.Order;
import com.kolos.bookstore.service.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
public class OrderDaoImpl implements OrderDao {

    private final ConnectionManager connectionManager;

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
            LIMIT ? OFFSET ?
            """;

    private static final String CREATE_ORDER = """
            INSERT INTO orders (user_id, cost, status_id)
            VALUES (?, ?, (SELECT id FROM status WHERE status_name = 'PENDING'))
            """;


    private static final String UPDATE_ORDER = """
            UPDATE orders
            SET user_id = ?,
                cost = ?,
                status_id = (SELECT id FROM status WHERE status_name = ?)
            WHERE id = ?
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
                WHERE o.user_id = ?
                ORDER BY o.id
                LIMIT ? OFFSET ?
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


    @Override
    public OrderDto find(Long id) {
        try (Connection connection = connectionManager.getConnection()) {
            log.info("Connecting to the database...");
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                OrderDto dto = new OrderDto();
                dto.setId(resultSet.getLong("id"));
                dto.setStatus(OrderDto.Status.valueOf(resultSet.getString("status_name")));
                dto.setCost(resultSet.getBigDecimal("totalCost"));
                dto.setUserId(resultSet.getLong("user_id"));
                return dto;
            }
        } catch (SQLException e) {
            throw new NotFoundException("Failed to find order with id " + id, e);
        }
        return null;
    }

    @Override
    public List<OrderDto> findByUserId(Long id, int limit, int offset) {
        try (Connection connection = connectionManager.getConnection()) {
            log.info("Connecting to the database...");
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_USER_ID);
            preparedStatement.setLong(1, id);
            preparedStatement.setInt(2, limit);
            preparedStatement.setInt(3, offset);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<OrderDto> dtos = new ArrayList<>();
            while (resultSet.next()) {
                OrderDto dto = new OrderDto();
                dto.setId(resultSet.getLong("id"));
                dto.setStatus(OrderDto.Status.valueOf(resultSet.getString("status_name")));
                dto.setCost(resultSet.getBigDecimal("totalCost"));
                dto.setUserId(resultSet.getLong("user_id"));
                dtos.add(dto);
            }
            return dtos;
        } catch (SQLException e) {
            throw new NotFoundException("Failed to find order with id " + id, e);
        }
    }

    @Override
    public int countAll() {
        int totalCount = 0;
        try (Connection connection = connectionManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(COUNT_ALL)) {
            log.info("Connecting to the database and executing COUNT_ALL query...");
            if (resultSet.next()) {
                totalCount = resultSet.getInt("totalCount");
            }
        } catch (SQLException e) {
            log.error("SQL exception occurred while counting books", e);
            throw new NotFoundException("Cannot get Book count", e);
        }
        return totalCount;
    }

    @Override
    public int countAll(String messages) {
        int totalCount = 0;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(COUNT_ALL_MESSAGE)) {
            statement.setLong(1, Long.parseLong(messages));
            log.info("Connecting to the database and executing COUNT_ALL query...");
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    totalCount = resultSet.getInt("totalCount");
                }
            }
        } catch (SQLException e) {
            log.error("SQL exception occurred while counting books", e);
            throw new NotFoundException("Cannot get Book count", e);
        }
        return totalCount;
    }



    @Override
    public List<OrderDto> findAll(int limit, int offset) {
        try (Connection connection = connectionManager.getConnection()) {
            log.info("Connecting to the database...");
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_ORDER_PAGES);
            statement.setInt(1, limit);
            statement.setInt(2, offset);
            ResultSet resultSet = statement.executeQuery();
            log.debug("Executing query...");
            List<OrderDto> orders = new ArrayList<>();
            while (resultSet.next()) {
                OrderDto dto = mapToOrderDto(resultSet);
                orders.add(dto);
            }
            return orders;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public OrderDto save(OrderDto dto) {
        try (Connection connection = connectionManager.getConnection()) {
            log.info("Connecting to the database...");
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_ORDER, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setLong(1, dto.getUserId());
            preparedStatement.setBigDecimal(2, dto.getCost());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                Long id = resultSet.getLong(1);
                return find(id);
            }
        } catch (SQLException e) {
            log.error("Error saving order", e);
            throw new RuntimeException("Error creating order", e);
        }
        throw new RuntimeException("Error creating order, no ID returned");
    }


    @Override
    public OrderDto update(OrderDto dto) {
        try (Connection connection = connectionManager.getConnection()) {
            log.info("Connecting to the database...");
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ORDER);
            log.debug("Executing query...");
            preparedStatement.setLong(1, dto.getUserId());
            preparedStatement.setBigDecimal(2, dto.getCost());
            preparedStatement.setString(3, dto.getStatus().name());
            preparedStatement.setLong(4, dto.getId());
            preparedStatement.executeUpdate();
            return find(dto.getId());

        } catch (SQLException e) {
            throw new RuntimeException("Couldn't update dto");
        }
    }


    @Override
    public boolean delete(Long id) {
        try (Connection connection = connectionManager.getConnection()) {
            log.info("Connecting to the database...");
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ORDER_STATUS_BY_ID);
            log.debug("Executing query...");
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            log.error("Error updating order status", e);
            throw new RuntimeException("Error updating order status to CANCELLED", e);
        }
    }


    private static OrderDto mapToOrderDto(ResultSet resultSet) throws SQLException {
        OrderDto dto = new OrderDto();
        dto.setId(resultSet.getLong("id"));
        dto.setCost(resultSet.getBigDecimal("totalCost"));
        dto.setStatus(OrderDto.Status.valueOf(resultSet.getString("status_name")));
        dto.setUserId(resultSet.getLong("user_id"));
        return dto;
    }


}
