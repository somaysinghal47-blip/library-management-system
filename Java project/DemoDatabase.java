import java.sql.*;

public class DemoDatabase {

    private static final String DB_URL = "jdbc:h2:./bibliosphere_demo;MODE=MySQL;DATABASE_TO_LOWER=TRUE";
    private static final String USER = "sa";
    private static final String PASS = "";

    public static void main(String[] args) {
        System.out.println("==========================================================================================");
        System.out.println("                   BIBLIOSPHERE - DEMO DATABASE INITIALIZATION                            ");
        System.out.println("==========================================================================================\n");

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            // 1. Drop existing tables if present
            stmt.execute("DROP TABLE IF EXISTS books");
            stmt.execute("DROP TABLE IF EXISTS users");

            // 2. Create 'users' table
            stmt.execute("CREATE TABLE users (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "username VARCHAR(50) NOT NULL UNIQUE, " +
                    "password VARCHAR(255) NOT NULL, " +
                    "role VARCHAR(20) NOT NULL DEFAULT 'USER'" +
                    ")");

            // 3. Create 'books' table
            stmt.execute("CREATE TABLE books (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "title VARCHAR(255) NOT NULL, " +
                    "author VARCHAR(255) NOT NULL, " +
                    "available_copies INT NOT NULL DEFAULT 1" +
                    ")");

            // 4. Insert Demo Users
            stmt.execute("INSERT INTO users (username, password, role) VALUES " +
                    "('admin', 'admin123', 'ADMIN'), " +
                    "('saumya', 'pass2026', 'ADMIN'), " +
                    "('patron', 'user123', 'USER'), " +
                    "('alice', 'alice123', 'USER'), " +
                    "('bob_reader', 'bob123', 'USER')");

            // 5. Insert Demo Books
            stmt.execute("INSERT INTO books (title, author, available_copies) VALUES " +
                    "('The Great Gatsby', 'F. Scott Fitzgerald', 4), " +
                    "('To Kill a Mockingbird', 'Harper Lee', 3), " +
                    "('1984', 'George Orwell', 5), " +
                    "('Clean Code: Agile Software Craftsmanship', 'Robert C. Martin', 2), " +
                    "('Effective Java (3rd Edition)', 'Joshua Bloch', 3), " +
                    "('Introduction to Algorithms (CLRS)', 'Thomas H. Cormen', 1), " +
                    "('Design Patterns: Elements of Reusable OO Software', 'Erich Gamma et al.', 2), " +
                    "('The Pragmatic Programmer', 'Andrew Hunt and David Thomas', 4), " +
                    "('Artificial Intelligence: A Modern Approach', 'Stuart Russell et al.', 0), " +
                    "('Database System Concepts', 'Abraham Silberschatz', 2)");

            System.out.println("[SUCCESS] Database 'bibliosphere_demo' initialized with tables 'users' and 'books'!\n");

            // Display 'users' table
            System.out.println("+-----+----------------------+----------------------+----------------------+");
            System.out.println("|                            TABLE: users                                  |");
            System.out.println("+-----+----------------------+----------------------+----------------------+");
            System.out.printf("| %-3s | %-20s | %-20s | %-20s |%n", "ID", "USERNAME", "PASSWORD", "ROLE");
            System.out.println("+-----+----------------------+----------------------+----------------------+");

            try (ResultSet rs = stmt.executeQuery("SELECT * FROM users ORDER BY id ASC")) {
                while (rs.next()) {
                    System.out.printf("| %-3d | %-20s | %-20s | %-20s |%n",
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("role"));
                }
            }
            System.out.println("+-----+----------------------+----------------------+----------------------+\n");

            // Display 'books' table
            System.out.println("+-----+----------------------------------------------------+----------------------+------+");
            System.out.println("|                                   TABLE: books                                           |");
            System.out.println("+-----+----------------------------------------------------+----------------------+------+");
            System.out.printf("| %-3s | %-50s | %-20s | %-4s |%n", "ID", "BOOK TITLE", "AUTHOR", "COPIES");
            System.out.println("+-----+----------------------------------------------------+----------------------+------+");

            try (ResultSet rs = stmt.executeQuery("SELECT * FROM books ORDER BY id ASC")) {
                while (rs.next()) {
                    String title = rs.getString("title");
                    if (title.length() > 50) {
                        title = title.substring(0, 47) + "...";
                    }
                    String author = rs.getString("author");
                    if (author.length() > 20) {
                        author = author.substring(0, 17) + "...";
                    }
                    int qty = rs.getInt("available_copies");
                    System.out.printf("| %-3d | %-50s | %-20s | %-4d |%n",
                            rs.getInt("id"),
                            title,
                            author,
                            qty);
                }
            }
            System.out.println("+-----+----------------------------------------------------+----------------------+------+\n");

            // Display Summary Metrics
            try (ResultSet rsUsers = stmt.executeQuery("SELECT COUNT(*) AS total_users FROM users");
                 Statement stmt2 = conn.createStatement();
                 ResultSet rsBooks = stmt2.executeQuery("SELECT COUNT(*) AS total_books, SUM(available_copies) AS total_copies FROM books");
                 Statement stmt3 = conn.createStatement();
                 ResultSet rsOut = stmt3.executeQuery("SELECT COUNT(*) AS out_of_stock FROM books WHERE available_copies = 0")) {

                rsUsers.next();
                rsBooks.next();
                rsOut.next();

                System.out.println("DATABASE SUMMARY METRICS:");
                System.out.println("-------------------------");
                System.out.println(" -> Total Registered Accounts : " + rsUsers.getInt("total_users"));
                System.out.println(" -> Total Book Titles         : " + rsBooks.getInt("total_books"));
                System.out.println(" -> Total Physical Inventory  : " + rsBooks.getInt("total_copies") + " copies");
                System.out.println(" -> Currently Out of Stock    : " + rsOut.getInt("out_of_stock") + " title(s)");
            }

            System.out.println("\n==========================================================================================");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
