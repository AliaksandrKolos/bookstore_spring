package com.kolos.bookstore.data.dao.impl;

import com.kolos.bookstore.data.connection.ConnectionManager;
import com.kolos.bookstore.data.dao.UserDao;
import com.kolos.bookstore.data.dto.UserDto;
import com.kolos.bookstore.service.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository
public class UserDaoImpl implements UserDao {


    private static final String FIND_BY_LASTNAME = """
        SELECT u.id, u.email, u."password", u.first_name, u.last_name, roles.role_name, u.deleted
        FROM users u
        JOIN roles ON u.role_id = roles.id
        WHERE LOWER(u.last_name) = LOWER(?) AND u.deleted = false
        ORDER BY u.id
        LIMIT ? OFFSET ?
        """;


    private static final String FIND_BY_EMAIL = """
            SELECT u.id, u.email, u."password", u.first_name, u.last_name, roles.role_name
            FROM users u
            JOIN roles ON u.role_id = roles.id
            WHERE u.email = ? AND u.deleted = false
            """;

    public static final String GET_ALL_USERS_PAGES = """
            SELECT u.id, u.email, u."password", u.first_name, u.last_name, roles.role_name
            FROM users u
            JOIN roles ON u.role_id = roles.id
            WHERE u.deleted = false
            ORDER BY u.id
            LIMIT ? OFFSET ?
            """;


    public static final String ADD_NEW_USER = """
            INSERT INTO users (email, "password", first_name, last_name, role_id, deleted) 
            VALUES (?, ?, ?, ?, (SELECT id FROM roles WHERE  role_name = 'USER'), false)
            """;

    private static final String DELETE_USER = """
            UPDATE users 
            SET deleted = true
            WHERE id = ?
            """;

    private static final String UPDATE_USER = """
            UPDATE users 
            SET email = ?, password = ?, first_name = ?, last_name = ?, role_id = (SELECT id FROM roles WHERE role_name = ?)
            WHERE id = ? AND deleted = false
            """;


    private static final String GET_USER_BY_ID = """
            SELECT u.id, u.email, u.password, u.first_name, u.last_name, r.role_name
            FROM users u 
            JOIN roles r ON u.role_id = r.id
            WHERE u.id = ? AND u.deleted = false
            """;

    private static final String COUNT_ALL = """
            SELECT COUNT(u.id) as totalCount
            FROM books u
            WHERE u.deleted = false""";

    private final ConnectionManager connectionManager;


    @Override
    public UserDto findByEmail(String email) {
        try (Connection connection = connectionManager.getConnection()) {
            log.info("Connecting to the database...");
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_EMAIL);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            log.debug("Request to search user by EMAIL completed successfully.");
            if (resultSet.next()) {
                return createFullUserDto(resultSet);
            }

        } catch (SQLException e) {
            throw new NotFoundException("Could not find user with email: " + email);
        }
        return null;
    }

    @Override
    public List<UserDto> findByLastName(String lastName, int limit, int offset) {
        List<UserDto> users = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection()) {
            log.info("Connecting to the database...");
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_LASTNAME);
            preparedStatement.setString(1, lastName);
            preparedStatement.setInt(2, limit);
            preparedStatement.setInt(3, offset);
            ResultSet resultSet = preparedStatement.executeQuery();
            log.debug("Request to search user by LASTNAME completed successfully.");
            while (resultSet.next()) {
                users.add(createFullUserDto(resultSet));
            }
        } catch (SQLException e) {
            throw new NotFoundException("Could not find user with last name: " + lastName);
        }
        return users;
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
    public UserDto find(Long id) {
        try (Connection connection = connectionManager.getConnection()) {
            log.info("Connecting to the database...");
            PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_ID);
            log.debug("Executing query...");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return createFullUserDto(resultSet);
            }
        } catch (SQLException e) {
            throw new NotFoundException("Could not find user with id: " + id);
        }
        return null;
    }

    @Override
    public List<UserDto> findAll(int limit, int offset) {
        try (Connection connection = connectionManager.getConnection()) {
            log.info("Connecting to the database...");
            PreparedStatement statement = connection.prepareStatement(GET_ALL_USERS_PAGES);
            statement.setInt(1, limit);
            statement.setInt(2, offset);
            ResultSet resultSet = statement.executeQuery();
            log.debug("Executing query...");
            List<UserDto> users = new ArrayList<>();
            while (resultSet.next()) {
                UserDto dto = createFullUserDto(resultSet);
                users.add(dto);
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public UserDto save(UserDto dto) {
        try (Connection connection = connectionManager.getConnection()) {
            log.info("Connecting to the database...");
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_NEW_USER, Statement.RETURN_GENERATED_KEYS);
            log.debug("Executing query...");
            setUserCreateParameters(dto, preparedStatement);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                Long id = resultSet.getLong("id");
                return find(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException("Error creating dto");
    }


    @Override
    public UserDto update(UserDto dto) {
        try (Connection connection = connectionManager.getConnection()) {
            log.info("Connecting to the database...");
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER);
            log.debug("Executing query...");
            setUserUpdateParameters(dto, preparedStatement);
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
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER);
            log.debug("Executing query...");
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Error while deleting user");
        }
    }

    private static UserDto createFullUserDto(ResultSet resultSet) throws SQLException {
        UserDto dto = new UserDto();
        dto.setId(resultSet.getLong("id"));
        dto.setEmail(resultSet.getString("email"));
        dto.setPassword(resultSet.getString("password"));
        dto.setFirstName(resultSet.getString("first_name"));
        dto.setLastName(resultSet.getString("last_name"));
        dto.setRole(UserDto.Role.valueOf(resultSet.getString("role_name")));
        return dto;
    }


    private static void setUserUpdateParameters(UserDto dto, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, dto.getEmail());
        preparedStatement.setString(2, dto.getPassword());
        preparedStatement.setString(3, dto.getFirstName());
        preparedStatement.setString(4, dto.getLastName());
        preparedStatement.setString(5, dto.getRole().name());
        preparedStatement.setLong(6, dto.getId());
    }

    private static void setUserCreateParameters(UserDto dto, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, dto.getEmail());
        preparedStatement.setString(2, dto.getPassword());
        preparedStatement.setString(3, dto.getFirstName());
        preparedStatement.setString(4, dto.getLastName());
    }
}
