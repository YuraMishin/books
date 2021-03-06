package com.mishinyura.books.dao;

/**
 * Class SqlQueries.
 * Implements Sql Queries storage.
 *
 * @author Mishin Yura (mishin.inbox@gmail.com)
 * @since 17.05.2022
 */
public final class SqlQueries {
    /**
     * Constructor.
     */
    private SqlQueries() {
    }

    /**
     * Insert a book into Books Table.
     */
    public static final String INSERT_BOOK = """
            INSERT INTO books (title) VALUES (?);
            """;

    /**
     * Find all books.
     */
    public static final String FIND_ALL_BOOKS = """
            SELECT id, title, created_at, updated_at, version
            FROM books
            """;

    /**
     * Find book by id.
     */
    public static final String FIND_BOOK_BY_ID = """
            SELECT id, title, created_at, updated_at, version
            FROM books
            WHERE id = ?;
            """;

    /**
     * Find book by title.
     */
    public static final String FIND_BOOK_BY_TITLE = """
            SELECT id, title, created_at, updated_at, version
            FROM books
            WHERE title = ?;
            """;

    /**
     * Update book by id.
     */
    public static final String UPDATE_BOOK_BY_ID = """
            UPDATE books
            SET title = ?, updated_at = now(), version = version + 1
            WHERE id = ?;
            """;

    /**
     * Delete book by id.
     */
    public static final String DELETE_BOOK_BY_ID = """
            DELETE FROM books
            WHERE id = ?;
            """;

    /**
     * Get all books between two dates.
     */
    public static final String GET_BOOKS_BETWEEN_TWO_DATES = """
            SELECT id, title, created_at, updated_at, version
            FROM books
            WHERE created_at BETWEEN ? AND ?;
            """;
}
