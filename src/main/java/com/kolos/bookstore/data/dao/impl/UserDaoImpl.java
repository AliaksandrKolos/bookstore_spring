package com.kolos.bookstore.data.dao.impl;

import com.kolos.bookstore.data.dao.UserDao;
import com.kolos.bookstore.data.dto.UserDto;
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
import java.sql.Types;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository
public class UserDaoImpl implements UserDao {


    private static final String FIND_BY_LASTNAME = """
            SELECT u.id, u.email, u."password", u.first_name, u.last_name, roles.role_name, u.deleted
            FROM users u
            JOIN roles ON u.role_id = roles.id
            WHERE LOWER(u.last_name) = LOWER(:last_name) AND u.deleted = false
            ORDER BY u.id
            LIMIT :limit OFFSET :offset
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
            LIMIT :limit OFFSET :offset
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
            SET email = :email, password = :password, first_name = :first_name, last_name = :last_name, role_id = (SELECT id FROM roles WHERE role_name = :role_name)
            WHERE id = :id AND deleted = false
            """;


    private static final String GET_USER_BY_ID = """
            SELECT u.id, u.email, u.password, u.first_name, u.last_name, r.role_name
            FROM users u 
            JOIN roles r ON u.role_id = r.id
            WHERE u.id = ? AND u.deleted = false
            """;

    private static final String COUNT_ALL = """
            SELECT COUNT(u.id) as totalCount
            FROM users u
            WHERE u.deleted = false""";

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Override
    public List<UserDto> findByLastName(String lastName, int limit, int offset) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("last_name", lastName);
        params.addValue("limit", limit);
        params.addValue("offset", offset);
        return namedParameterJdbcTemplate
                .queryForStream(FIND_BY_LASTNAME, params, this::mapRow)
                .toList();
    }


    @Override
    public List<UserDto> findAll(int limit, int offset) {
        MapSqlParameterSource paramMap = new MapSqlParameterSource();
        paramMap.addValue("limit", limit);
        paramMap.addValue("offset", offset);
        return namedParameterJdbcTemplate.
                queryForStream(GET_ALL_USERS_PAGES, paramMap, this::mapRow)
                .toList();
    }


    @Override
    public int countAll() {
        return jdbcTemplate.queryForObject(COUNT_ALL, Integer.class);
    }

    @Override
    public UserDto save(UserDto dto) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(ADD_NEW_USER, new String[]{"id"});
            ps.setString(1, dto.getEmail());
            ps.setString(2, dto.getPassword());
            ps.setString(3, dto.getFirstName());
            ps.setString(4, dto.getLastName());
            return ps;
        }, keyHolder);
        Number key = keyHolder.getKey();
        if (key != null) {
            return find(key.longValue());
        }
        return null;
    }

    @Override
    public UserDto findByEmail(String email) {
        List<UserDto> user = jdbcTemplate.query(FIND_BY_EMAIL, this::mapRow, email);
        if (user.isEmpty()) {
            return null;
        }
        return user.getFirst();
    }

    @Override
    public UserDto find(Long id) {
        return jdbcTemplate.queryForObject(GET_USER_BY_ID, this::mapRow, id);
    }

    @Override
    public UserDto update(UserDto dto) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("email", dto.getEmail(), Types.VARCHAR);
        params.addValue("password", dto.getPassword(), Types.VARCHAR);
        params.addValue("first_name", dto.getFirstName(), Types.VARCHAR);
        params.addValue("last_name", dto.getLastName(), Types.VARCHAR);
        params.addValue("role_name", dto.getRole().name(), Types.VARCHAR);
        params.addValue("id", dto.getId(), Types.BIGINT);

        namedParameterJdbcTemplate.update(UPDATE_USER, params);
        return find(dto.getId());
    }


    @Override
    public boolean delete(Long id) {
        int rowUpdated = jdbcTemplate.update(DELETE_USER, id);
        return rowUpdated == 1;
    }


    private UserDto mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        UserDto dto = new UserDto();
        dto.setId(resultSet.getLong("id"));
        dto.setEmail(resultSet.getString("email"));
        dto.setPassword(resultSet.getString("password"));
        dto.setFirstName(resultSet.getString("first_name"));
        dto.setLastName(resultSet.getString("last_name"));
        dto.setRole(UserDto.Role.valueOf(resultSet.getString("role_name").toUpperCase()));
        return dto;
    }


}
