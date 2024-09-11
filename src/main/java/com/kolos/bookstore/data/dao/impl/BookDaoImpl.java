package com.kolos.bookstore.data.dao.impl;

import com.kolos.bookstore.data.connection.ConnectionManager;
import com.kolos.bookstore.data.dao.BookDao;
import com.kolos.bookstore.data.dto.BookDto;
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
            LIMIT ? OFFSET ?""";


    private static final String ADD_NEW_BOOK = """
            INSERT INTO books (title, author, genre , year, isbn, pages, price, cover_id, deleted)
            VALUES (?, ?, ?, ?, ?, ?, ?, (SELECT id FROM covers WHERE cover_name = ?), false)""";

    private static final String UPDATE_BOOK = """
            UPDATE books SET title = ?, author = ?, genre = ?, year = ?, isbn = ?, pages = ?, price = ?, cover_id = (SELECT id FROM covers)
            WHERE cover_name = ?)
            WHERE id = ? AND b.deleted = false""";

    private static final String DELETE_BOOK = """
            UPDATE books
            SET deleted = true
            WHERE id = ?""";

    private static final String FIND_BY_ISBN = """
            SELECT b.id, b.title, b.author, b.genre, b.year, b.isbn, b.pages, b.price, b.cover_id
            FROM books b JOIN covers ON b.cover_id = covers.id
            WHERE b.isbn = ? AND b.deleted = false""";

    private static final String FIND_BY_AUTHOR = """
            SELECT b.id, b.title, b.author, b.genre, b.year, b.isbn, b.pages, b.price, b.cover_id
            FROM books b JOIN covers ON b.cover_id = covers.id
            WHERE b.author = ? AND b.deleted = false
            ORDER BY b.id 
            LIMIT ? OFFSET ?
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
            WHERE b.deleted = false AND b.title LIKE ?
            ORDER BY b.id 
            LIMIT ? OFFSET ?""";


    private final ConnectionManager connectionManager;

    @Override
    public BookDto findByIsbn(String isbn) {
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ISBN);
            preparedStatement.setString(1, isbn);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return createFullBook(resultSet);
            }
        } catch (SQLException e) {
            throw new NotFoundException("Cannot get Book by ISBN");
        }
        return null;
    }

    @Override
    public List<BookDto> findByAuthor(String author, int offset, int limit) {
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_AUTHOR);
            preparedStatement.setString(1, author);
            preparedStatement.setInt(2, limit);
            preparedStatement.setInt(3, offset);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<BookDto> books = new ArrayList<>();
            while (resultSet.next()) {
                createFullBook(resultSet);
            }
            return books;

        } catch (SQLException e) {
            throw new NotFoundException("Cannot get Book by author");
        }
    }

    @Override
    public BookDto find(Long id) {
        try (Connection connection = connectionManager.getConnection()) {
            log.info("Connecting to the database...");
            PreparedStatement preparedStatement = connection.prepareStatement(GET_BOOK_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return createFullBook(resultSet);
            }
        } catch (SQLException e) {
            throw new NotFoundException("Failed to find book with id " + id);
        }
        return null;
    }


    @Override
    public int countAllSearch(String messages) {
        int totalCount = 0;
        String searchPattern = (messages == null || messages.trim().isEmpty()) ? "%" : "%" + messages.trim() + "%";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(COUNT_ALL_SEARCH)) {
            preparedStatement.setString(1, messages);
            log.info("Connecting to the database and executing COUNT_ALL query...");
            preparedStatement.setString(1, searchPattern);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
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
    public List<BookDto> findAllSearch(String searchMessage, int offset, int limit) {
        log.debug("Executing query with MESSAGE SEARCH '{}' LIMIT {} and OFFSET {}", searchMessage, limit, offset);
        String searchPattern = (searchMessage == null || searchMessage.trim().isEmpty()) ? "%" : "%" + searchMessage.trim() + "%";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_PAGE_SEARCH)) {
            log.info("Connecting to the database...");
            statement.setString(1, searchPattern);
            statement.setInt(2, limit);
            statement.setInt(3, offset);

            try (ResultSet resultSet = statement.executeQuery()) {
                List<BookDto> books = new ArrayList<>();
                while (resultSet.next()) {
                    createShortBook(resultSet, books);
                }
                return books;
            }
        } catch (SQLException e) {
            log.error("Error finding all books", e);
            throw new NotFoundException("Error finding all books", e);
        }
    }

    @Override
    public List<BookDto> findAll(int limit, int offset) {
        log.debug("Executing query with LIMIT {} and OFFSET {}", limit, offset);
        try (Connection connection = connectionManager.getConnection()) {
            log.info("Connecting to the database...");
            PreparedStatement statement = connection.prepareStatement(GET_ALL_PAGE);
            statement.setInt(1, limit);
            statement.setInt(2, offset);
            ResultSet resultSet = statement.executeQuery();
            List<BookDto> books = new ArrayList<>();
            while (resultSet.next()) {
                createShortBook(resultSet, books);
            }
            return books;
        } catch (SQLException e) {
            log.error("Error finding all books", e);
            throw new NotFoundException("Error finding all books");
        }
    }


    @Override
    public BookDto save(BookDto dto) {
        try (Connection connection = connectionManager.getConnection()) {
            log.info("Connecting to the database...");
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_NEW_BOOK, Statement.RETURN_GENERATED_KEYS);
            setBookParameters(dto, preparedStatement);
            preparedStatement.executeUpdate();
            log.info("Created new entity " + dto.getId());
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                Long id = resultSet.getLong("id");
                return find(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error creating new entity");
        }
        return null;
    }


    @Override
    public BookDto update(BookDto dto) {
        try (Connection connection = connectionManager.getConnection()) {
            log.info("Connecting to the database...");
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BOOK);
            setBookParameters(dto, preparedStatement);
            preparedStatement.setLong(8, dto.getId());
            preparedStatement.executeUpdate();
            log.info("Successfully updated the dto");
            return find(dto.getId());

        } catch (SQLException e) {
            throw new RuntimeException("Update failed");
        }
    }

    @Override
    public boolean delete(Long id) {
        log.info("Connecting to the database...");
        try (Connection connection = connectionManager.getConnection()) {
            log.info("Connecting to the database...");
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BOOK);
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Delete failed");
        }
    }

    private static BookDto createFullBook(ResultSet resultSet) throws SQLException {
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

    private static void setBookParameters(BookDto dto, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, dto.getTitle());
        preparedStatement.setString(2, dto.getAuthor());
        preparedStatement.setString(3, dto.getGenre());
        preparedStatement.setInt(4, dto.getYear());
        preparedStatement.setString(5, dto.getIsbn());
        preparedStatement.setInt(6, dto.getPages());
        preparedStatement.setBigDecimal(7, dto.getPrice());
        preparedStatement.setString(8, dto.getCover().name());
    }

    private static void createShortBook(ResultSet resultSet, List<BookDto> books) throws SQLException {
        BookDto book = new BookDto();
        book.setId(resultSet.getLong("id"));
        book.setTitle(resultSet.getString("title"));
        book.setAuthor(resultSet.getString("author"));
        book.setPages(resultSet.getInt("pages"));
        books.add(book);
    }
}
