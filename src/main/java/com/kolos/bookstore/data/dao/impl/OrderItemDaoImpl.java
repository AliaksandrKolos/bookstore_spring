package com.kolos.bookstore.data.dao.impl;

import com.kolos.bookstore.data.connection.ConnectionManager;
import com.kolos.bookstore.data.dao.OrderItemDao;
import com.kolos.bookstore.data.dto.OrderItemDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class OrderItemDaoImpl implements OrderItemDao {

    private final ConnectionManager connectionManager;

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

    @Override
    public OrderItemDto find(Long id) {
        log.info("Finding order item with ID: {}", id);
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                log.debug("Order item found with ID: {}", id);
                return getItemDto(resultSet);
            }
            log.warn("No order item found with ID: {}", id);
        } catch (SQLException e) {
            log.error("Failed to find order item with ID: {}", id, e);
            throw new RuntimeException("Failed to find order item with ID " + id, e);
        }
        return null;
    }

    @Override
    public List<OrderItemDto> findAll() {
        log.info("Fetching all order items");
        List<OrderItemDto> orderItemDtoList = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(FIND_ALL_ORDER_ITEM);
            log.debug("Executing query to fetch all order items");
            while (resultSet.next()) {
                OrderItemDto dto = getItemDto(resultSet);
                orderItemDtoList.add(dto);
            }
            log.info("Successfully fetched {} order items", orderItemDtoList.size());
        } catch (SQLException e) {
            log.error("Error fetching all order items", e);
            throw new RuntimeException("Error fetching all order items", e);
        }
        return orderItemDtoList;
    }

    @Override
    public OrderItemDto save(OrderItemDto dto) {
        log.info("Saving new order item: {}", dto);
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_ORDER_ITEM, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setLong(1, dto.getBookId());
            preparedStatement.setLong(2, dto.getQuantity());
            preparedStatement.setBigDecimal(3, dto.getPrice());
            preparedStatement.setLong(4, dto.getOrderId());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                Long id = resultSet.getLong(1);
                log.debug("Order item created with ID: {}", id);
                return find(id);
            }
        } catch (SQLException e) {
            log.error("Error saving order item: {}", dto, e);
            throw new RuntimeException("Error creating order item", e);
        }
        log.error("Error creating order item, no ID returned for: {}", dto);
        throw new RuntimeException("Error creating order item, no ID returned");
    }

    @Override
    public boolean delete(Long id) {
        log.info("Deleting order item with ID: {}", id);
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID);
            preparedStatement.setLong(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 1) {
                log.debug("Successfully deleted order item with ID: {}", id);
                return true;
            } else {
                log.warn("No order item found to delete with ID: {}", id);
            }
        } catch (SQLException e) {
            log.error("Error deleting order item with ID: {}", id, e);
            throw new RuntimeException("Error deleting order item with ID " + id, e);
        }
        return false;
    }

    @Override
    public List<OrderItemDto> findByOrderId(Long orderId) {
        log.info("Fetching order items for order ID: {}", orderId);
        List<OrderItemDto> orderItemDtoList = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ORDER_ID);
            preparedStatement.setLong(1, orderId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                OrderItemDto dto = getItemDto(resultSet);
                orderItemDtoList.add(dto);
            }
            log.info("Successfully fetched {} order items for order ID: {}", orderItemDtoList.size(), orderId);
        } catch (SQLException e) {
            log.error("Failed to fetch order items for order ID: {}", orderId, e);
            throw new RuntimeException("Failed to fetch order items for order ID " + orderId, e);
        }
        return orderItemDtoList;
    }


    private static OrderItemDto getItemDto(ResultSet resultSet) throws SQLException {
        OrderItemDto dto = new OrderItemDto();
        dto.setId(resultSet.getLong("id"));
        dto.setBookId(resultSet.getLong("book_id"));
        dto.setQuantity(resultSet.getInt("quantity"));
        dto.setPrice(resultSet.getBigDecimal("price"));
        dto.setOrderId(resultSet.getLong("order_id"));
        return dto;
    }

}
