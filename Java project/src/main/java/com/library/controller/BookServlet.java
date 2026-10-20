package com.library.controller;

import com.library.dao.BookDAO;
import com.library.model.Book;
import com.library.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Controller handling all book-related operations:
 * Listing, Searching, Adding, Updating, and Deleting books.
 */
@WebServlet(name = "BookServlet", urlPatterns = {"/books"})
public class BookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BookDAO bookDAO;

    @Override
    public void init() {
        bookDAO = new BookDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "new":
                showNewForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteBook(request, response);
                break;
            case "list":
            default:
                listBooks(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "insert":
                insertBook(request, response);
                break;
            case "update":
                updateBook(request, response);
                break;
            default:
                doGet(request, response);
                break;
        }
    }

    /**
     * Lists all books or filters them by search query.
     */
    private void listBooks(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String searchQuery = request.getParameter("search");
        List<Book> books;

        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            books = bookDAO.searchBooks(searchQuery.trim());
            request.setAttribute("searchQuery", searchQuery.trim());
        } else {
            books = bookDAO.getAllBooks();
        }

        request.setAttribute("bookList", books);
        request.getRequestDispatcher("/books.jsp").forward(request, response);
    }

    /**
     * Displays the Add Book form (Admin only).
     */
    private void showNewForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        if (!isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/books?error=unauthorized");
            return;
        }
        request.getRequestDispatcher("/add-book.jsp").forward(request, response);
    }

    /**
     * Displays the Edit Book form pre-populated with current details (Admin only).
     */
    private void showEditForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        if (!isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/books?error=unauthorized");
            return;
        }

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Book existingBook = bookDAO.getBookById(id);
            if (existingBook != null) {
                request.setAttribute("book", existingBook);
                request.getRequestDispatcher("/edit-book.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/books?error=notfound");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/books?error=invalidId");
        }
    }

    /**
     * Handles adding a new book into the database (Admin only).
     */
    private void insertBook(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        if (!isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/books?error=unauthorized");
            return;
        }

        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String copiesStr = request.getParameter("availableCopies");

        if (title == null || title.trim().isEmpty() ||
            author == null || author.trim().isEmpty() ||
            copiesStr == null || copiesStr.trim().isEmpty()) {
            request.setAttribute("errorMessage", "All fields are required.");
            request.getRequestDispatcher("/add-book.jsp").forward(request, response);
            return;
        }

        try {
            int copies = Integer.parseInt(copiesStr.trim());
            if (copies < 0) {
                request.setAttribute("errorMessage", "Available copies cannot be negative.");
                request.getRequestDispatcher("/add-book.jsp").forward(request, response);
                return;
            }

            Book newBook = new Book(title.trim(), author.trim(), copies);
            boolean success = bookDAO.addBook(newBook);
            if (success) {
                response.sendRedirect(request.getContextPath() + "/books?msg=added");
            } else {
                request.setAttribute("errorMessage", "Failed to add book. Database error.");
                request.getRequestDispatcher("/add-book.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Copies must be a valid integer.");
            request.getRequestDispatcher("/add-book.jsp").forward(request, response);
        }
    }

    /**
     * Handles updating an existing book (Admin only).
     */
    private void updateBook(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        if (!isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/books?error=unauthorized");
            return;
        }

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String title = request.getParameter("title");
            String author = request.getParameter("author");
            String copiesStr = request.getParameter("availableCopies");

            if (title == null || title.trim().isEmpty() ||
                author == null || author.trim().isEmpty() ||
                copiesStr == null || copiesStr.trim().isEmpty()) {
                request.setAttribute("errorMessage", "All fields are required.");
                Book book = new Book(id, title, author, 0);
                request.setAttribute("book", book);
                request.getRequestDispatcher("/edit-book.jsp").forward(request, response);
                return;
            }

            int copies = Integer.parseInt(copiesStr.trim());
            if (copies < 0) {
                request.setAttribute("errorMessage", "Available copies cannot be negative.");
                Book book = new Book(id, title, author, 0);
                request.setAttribute("book", book);
                request.getRequestDispatcher("/edit-book.jsp").forward(request, response);
                return;
            }

            Book book = new Book(id, title.trim(), author.trim(), copies);
            boolean success = bookDAO.updateBook(book);
            if (success) {
                response.sendRedirect(request.getContextPath() + "/books?msg=updated");
            } else {
                request.setAttribute("errorMessage", "Failed to update book. Please try again.");
                request.setAttribute("book", book);
                request.getRequestDispatcher("/edit-book.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/books?error=invalidId");
        }
    }

    /**
     * Deletes a book from the database (Admin only).
     */
    private void deleteBook(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        if (!isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/books?error=unauthorized");
            return;
        }

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            bookDAO.deleteBook(id);
            response.sendRedirect(request.getContextPath() + "/books?msg=deleted");
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/books?error=invalidId");
        }
    }

    /**
     * Helper to verify if the currently logged-in user is an Admin/Librarian.
     */
    private boolean isAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("currentUser");
            return user != null && user.isAdmin();
        }
        return false;
    }
}
