package com.mishinyura.books.dao;

import com.mishinyura.books.db.SqlQueries;
import com.mishinyura.books.entity.BookV1;
import com.mishinyura.books.exception.DaoException;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class BookDaoImpl.
 * Implements Book DAO.
 *
 * @author Mishin Yura (mishin.inbox@gmail.com)
 * @since 17.05.2022
 */
@Slf4j
public class BookDaoImpl implements Dao<Long, BookV1> {
    /**
     * INSTANCE.
     */
    private static final BookDaoImpl INSTANCE = new BookDaoImpl();

    /**
     * BookEntityMapper.
     */
    public static final BookEntityMapper MAPPER = new BookEntityMapper();

    /**
     * Constructor.
     */
    private BookDaoImpl() {
    }

    /**
     * @return INSTANCE
     */
    public static BookDaoImpl getInstance() {
        return INSTANCE;
    }

    /**
     * Method saves book.
     *
     * @param book BookV1
     * @param conn Connection
     * @return BookV1
     */
    public BookV1 save(final BookV1 book, final Connection conn) {
        try (PreparedStatement ps = conn.prepareStatement(SqlQueries.INSERT_BOOK, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, book.getTitle());
            log.info("{} row(s) inserted", ps.executeUpdate());
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    book.setId(keys.getLong("id"));
                }
            }
            return book;
        } catch (SQLException e) {
            log.error(
                    "Exception caught while performing save book: {}",
                    e.getMessage()
            );
            throw new DaoException(e);
        }
    }

    /**
     * Method gets all books.
     *
     * @param conn Connection
     * @return List<BookV1>
     */
    public List<BookV1> findAll(final Connection conn) {
        try (PreparedStatement ps = conn.prepareStatement(SqlQueries.FIND_ALL_BOOKS)) {
            List<BookV1> books = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    books.add(MAPPER.map(rs));
                }
            }
            return books;
        } catch (SQLException e) {
            log.error(
                    "Exception caught while performing find all: {}",
                    e.getMessage()
            );
            throw new DaoException(e);
        }
    }

    /**
     * Method finds book by id.
     *
     * @param id   Id
     * @param conn Connection
     * @return Optional<BookV1>
     */
    public Optional<BookV1> findById(final Long id, final Connection conn) {
        try (PreparedStatement ps = conn.prepareStatement(SqlQueries.FIND_BOOK_BY_ID)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            BookV1 book = null;
            if (rs.next()) {
                book = MAPPER.map(rs);
            }
            return Optional.ofNullable(book);

        } catch (SQLException e) {
            log.error(
                    "Exception caught while performing find by id: {}",
                    e.getMessage()
            );
            throw new DaoException(e);
        }
    }
}