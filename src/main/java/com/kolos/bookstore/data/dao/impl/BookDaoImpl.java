package com.kolos.bookstore.data.dao.impl;

import com.kolos.bookstore.data.dao.BookDao;
import com.kolos.bookstore.data.dto.BookDto;
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
public class BookDaoImpl implements BookDao {


    private static final String GET_BOOK_BY_ID = """
            SELECT b.id, b.author, b.isbn, b.title, b.genre, b.year, b.pages, b.price, covers.cover_name, b.deleted
            FROM books b
            JOIN covers ON b.cover_id = covers.id
            WHERE b.id = ? AND  b.deleted = false""";


    private static final String GET_ALL_PAGE = """
            SELECT b.id, b.title, b.author, b.genre, b.year, b.isbn, b.pages, b.price, covers.cover_name
            FROM books b 
            JOIN covers ON b.cover_id = covers.id
            WHERE b.deleted = false
            ORDER BY b.id 
            LIMIT :limit OFFSET :offset""";


    private static final String ADD_NEW_BOOK = """
            INSERT INTO books (title, author, genre , year, isbn, pages, price, cover_id, deleted)
            VALUES (?, ?, ?, ?, ?, ?, ?, (SELECT id FROM covers WHERE cover_name = ?), false)""";

    private static final String UPDATE_BOOK = """
            UPDATE books\s
            SET title = :title, author = :author, genre = :genre, year = :year, isbn = :isbn, pages = :pages, price = :price,
            cover_id = (SELECT id FROM covers WHERE cover_name = :cover_name)
            WHERE id = :id AND deleted = false""";


    private static final String DELETE_BOOK = """
            UPDATE books
            SET deleted = true
            WHERE id = ?""";

    private static final String FIND_BY_ISBN = """
            SELECT b.id, b.title, b.author, b.genre, b.year, b.isbn, b.pages, b.price, c.cover_name
            FROM books b
            JOIN covers c ON b.cover_id = c.id
            WHERE b.isbn = ? AND b.deleted = false""";

    private static final String FIND_BY_AUTHOR = """
            SELECT b.id, b.title, b.author, b.genre, b.year, b.isbn, b.pages, b.price, covers.cover_name
            FROM books b JOIN covers ON b.cover_id = covers.id
            WHERE b.author = :author AND b.deleted = false
            ORDER BY b.id 
            LIMIT :limit OFFSET :offset
            """;

    private static final String COUNT_ALL = """
            SELECT COUNT(b.id) as totalCount
            FROM books b
            WHERE b.deleted = false""";

    private static final String COUNT_ALL_SEARCH = """
            SELECT COUNT(b.id) as totalCount
            FROM books b
            WHERE b.deleted = false AND b.title LIKE ?""";


    private static final String GET_ALL_PAGE_SEARCH = """
            SELECT b.id, b.title, b.author, b.genre, b.year, b.isbn, b.pages, b.price, covers.cover_name
            FROM books b 
            JOIN covers ON b.cover_id = covers.id
            WHERE b.deleted = false AND b.title LIKE :searchMessage
            ORDER BY b.id 
            LIMIT :limit OFFSET :offset""";


    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Override
    public BookDto save(BookDto dto) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(ADD_NEW_BOOK, new String[]{"id"});
            ps.setString(1, dto.getTitle());
            ps.setString(2, dto.getAuthor());
            ps.setString(3, dto.getGenre());
            ps.setInt(4, dto.getYear());
            ps.setString(5, dto.getIsbn());
            ps.setInt(6, dto.getPages());
            ps.setBigDecimal(7, dto.getPrice());
            ps.setString(8, dto.getCover().name());
            return ps;
        }, keyHolder);
        Number key = keyHolder.getKey();
        if (key != null) {
            return find(key.longValue());
        }
        return null;
    }


    @Override
    public BookDto findByIsbn(String isbn) {
        List<BookDto> book = jdbcTemplate.query(FIND_BY_ISBN, this::mapRow, isbn);
        if (book.isEmpty()) {
            return null;
        }
        return book.getFirst();
    }


    @Override
    public List<BookDto> findByAuthor(String author, int offset, int limit) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("author", author);
        params.addValue("offset", offset);
        params.addValue("limit", limit);
        return namedParameterJdbcTemplate
                .queryForStream(FIND_BY_AUTHOR, params, this::mapRow)
                .toList();
    }

    @Override
    public int countAll() {
        return jdbcTemplate.queryForObject(COUNT_ALL, Integer.class);
    }

    @Override
    public int countAllSearch(String messages) {
        return jdbcTemplate.queryForObject(COUNT_ALL_SEARCH, Integer.class, "%" + messages + "%");
    }


    @Override
    public List<BookDto> findAllSearch(String searchMessage, int offset, int limit) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("searchMessage", "%" + searchMessage + "%");
        params.addValue("offset", offset);
        params.addValue("limit", limit);
        return namedParameterJdbcTemplate
                .queryForStream(GET_ALL_PAGE_SEARCH, params, this::mapRow)
                .toList();
    }


    @Override
    public List<BookDto> findAll(int limit, int offset) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("limit", limit);
        params.addValue("offset", offset);
        return namedParameterJdbcTemplate
                .queryForStream(GET_ALL_PAGE, params, this::mapRow)
                .toList();
    }

    @Override
    public BookDto update(BookDto dto) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("title", dto.getTitle());
        param.addValue("author", dto.getAuthor());
        param.addValue("genre", dto.getGenre());
        param.addValue("year", dto.getYear());
        param.addValue("isbn", dto.getIsbn());
        param.addValue("pages", dto.getPages());
        param.addValue("price", dto.getPrice());
        param.addValue("cover_name", dto.getCover().name());
        param.addValue("id", dto.getId());
        namedParameterJdbcTemplate.update(UPDATE_BOOK, param);
        return find(dto.getId());
    }

    @Override
    public boolean delete(Long id) {
        int updateRow = jdbcTemplate.update(DELETE_BOOK, id);
        return updateRow == 1;
    }

    @Override
    public BookDto find(Long id) {
        return jdbcTemplate.queryForObject(GET_BOOK_BY_ID, this::mapRow, id);
    }

    private BookDto mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        BookDto dto = new BookDto();
        dto.setId(resultSet.getLong("id"));
        dto.setTitle(resultSet.getString("title"));
        dto.setAuthor(resultSet.getString("author"));
        dto.setGenre(resultSet.getString("genre"));
        dto.setYear(resultSet.getInt("year"));
        dto.setIsbn(resultSet.getString("isbn"));
        dto.setPages(resultSet.getInt("pages"));
        dto.setPrice(resultSet.getBigDecimal("price"));
        dto.setCover(BookDto.Cover.valueOf(resultSet.getString("cover_name").toUpperCase()));
        return dto;
    }
}
