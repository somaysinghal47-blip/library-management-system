package com.library.dao;

import com.library.model.Book;
import com.library.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for Book entity.
 * Provides CRUD and search operations on the books table.
 */
public class BookDAO {

    /**
     * Retrieves all books from the database.
     * @return List of Book objects
     */
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT id, title, author, available_copies FROM books ORDER BY id DESC";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Book book = mapRowToBook(rs);
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.close(rs, stmt, conn);
        }
        return books;
    }

    /**
     * Searches books by title or author matching the query string.
     * @param query Search keyword
     * @return List of matching Book objects
     */
    public List<Book> searchBooks(String query) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT id, title, author, available_copies FROM books " +
                     "WHERE LOWER(title) LIKE ? OR LOWER(author) LIKE ? ORDER BY id DESC";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            String pattern = "%" + (query == null ? "" : query.trim().toLowerCase()) + "%";
            stmt.setString(1, pattern);
            stmt.setString(2, pattern);

            rs = stmt.executeQuery();
            while (rs.next()) {
                books.add(mapRowToBook(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.close(rs, stmt, conn);
        }
        return books;
    }

    /**
     * Finds a book by its primary key ID.
     * @param id Book ID
     * @return Book object if found, null otherwise
     */
    public Book getBookById(int id) {
        String sql = "SELECT id, title, author, available_copies FROM books WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRowToBook(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.close(rs, stmt, conn);
        }
        return null;
    }

    /**
     * Adds a new book to the database.
     * @param book Book to insert
     * @return true if successful, false otherwise
     */
    public boolean addBook(Book book) {
        String sql = "INSERT INTO books (title, author, available_copies) VALUES (?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setInt(3, book.getAvailableCopies());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DatabaseConnection.close(stmt, conn);
        }
    }

    /**
     * Updates an existing book.
     * @param book Book with updated attributes and target ID
     * @return true if updated, false otherwise
     */
    public boolean updateBook(Book book) {
        String sql = "UPDATE books SET title = ?, author = ?, available_copies = ? WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setInt(3, book.getAvailableCopies());
            stmt.setInt(4, book.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DatabaseConnection.close(stmt, conn);
        }
    }

    /**
     * Deletes a book by its ID.
     * @param id Book ID to delete
     * @return true if deleted, false otherwise
     */
    public boolean deleteBook(int id) {
        String sql = "DELETE FROM books WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DatabaseConnection.close(stmt, conn);
        }
    }

    /**
     * Helper method to map ResultSet row to a Book object.
     */
    private Book mapRowToBook(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setId(rs.getInt("id"));
        book.setTitle(rs.getString("title"));
        book.setAuthor(rs.getString("author"));
        book.setAvailableCopies(rs.getInt("available_copies"));
        return book;
    }
}
