# BiblioSphere - Simple Library Management System (Web Project)

A lightweight, beginner-friendly web application for managing library books and patrons. Built with **Core Java (Servlets, JSP, JDBC)** and **MySQL** following the **Model-View-Controller (MVC)** architectural pattern.

Developed by **Saumya Singhal**  
GitHub: [@somaysinghal47-blip](https://github.com/somaysinghal47-blip)  
Repository: [library-management-system](https://github.com/somaysinghal47-blip/library-management-system)

---

## 🏗️ Architecture (MVC)

BiblioSphere strictly separates concerns according to the MVC pattern:

```text
                  +-----------------------------------------+
                  |              User / Browser             |
                  +--------------------+--------------------+
                                       | HTTP Requests / Responses
                                       v
                  +--------------------+--------------------+
                  |        Controller (Servlet)             |
                  |  LoginServlet, BookServlet, etc.        |
                  +---------+--------------------+----------+
                            |                    |
            +---------------+                    +---------------+
            v                                                    v
+-----------------------+                            +-----------------------+
|      Model (DAO)      |                            |       View (JSP)      |
|   BookDAO, UserDAO    |                            |     books.jsp, etc.   |
+-----------+-----------+                            +-----------------------+
            |
            v
+-----------------------+
|    MySQL Database     |
|   (simple_library)    |
+-----------------------+
```

1. **Model (`com.library.model`, `com.library.dao`, `com.library.util`)**:
   - `Book.java` & `User.java`: Plain Java Objects (POJOs) representing domain entities.
   - `BookDAO.java` & `UserDAO.java`: Encapsulates all SQL queries (JDBC PreparedStatements) for safe CRUD operations.
   - `DatabaseConnection.java`: Manages MySQL JDBC connection pooling/lifecycle.
2. **View (`src/main/webapp/*.jsp`)**:
   - Clean HTML5 + CSS interfaces using JSTL tags.
   - `books.jsp`: Search bar, stock badge indicators, responsive book table.
   - `login.jsp` & `register.jsp`: Authentication forms with validation feedback.
   - `add-book.jsp` & `edit-book.jsp`: Admin forms for catalog management.
3. **Controller (`com.library.controller`)**:
   - `BookServlet.java`: Routes requests for listing, searching, creating, editing, and deleting books.
   - `LoginServlet.java` & `LogoutServlet.java`: Handles user authentication and session management.
   - `RegisterServlet.java`: Processes patron account creation.

---

## 📂 Project Structure

```text
Java project/
├── pom.xml                                    # Maven dependencies & build configuration
├── README.md                                  # Documentation and run instructions
├── database/
│   └── schema.sql                             # Database creation & sample data script
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── library/
        │           ├── controller/            # Servlets (Controllers)
        │           │   ├── BookServlet.java
        │           │   ├── LoginServlet.java
        │           │   ├── LogoutServlet.java
        │           │   └── RegisterServlet.java
        │           ├── dao/                   # Data Access Objects (JDBC)
        │           │   ├── BookDAO.java
        │           │   └── UserDAO.java
        │           ├── model/                 # Plain Java Entities (Models)
        │           │   ├── Book.java
        │           │   └── User.java
        │           └── util/                  # Helper Utilities
        │               └── DatabaseConnection.java
        └── webapp/                            # Web Resources (Views)
            ├── WEB-INF/
            │   └── web.xml                    # Servlet deployment descriptor
            ├── css/
            │   └── style.css                  # Modern responsive stylesheet
            ├── add-book.jsp                   # Add new book form (Admin)
            ├── books.jsp                      # Catalog list & search
            ├── edit-book.jsp                  # Edit book details form (Admin)
            ├── index.jsp                      # Welcome / landing page
            ├── login.jsp                      # Login form
            ├── navbar.jsp                     # Reusable navigation bar
            └── register.jsp                   # Patron registration form
```

---

## ⚙️ Prerequisites

- **Java Development Kit (JDK):** JDK 8 or higher (Java 8, 11, 17, or 21)
- **Database:** MySQL Server 5.7 or 8.x
- **Build Tool / Web Server:** Apache Maven and/or Apache Tomcat 8.5/9.x

---

## 🚀 Setup & Installation

### Step 1: Initialize the MySQL Database
1. Open your MySQL client (e.g. MySQL Workbench, phpMyAdmin, or MySQL CLI):
   ```bash
   mysql -u root -p
   ```
2. Run the provided SQL script:
   ```sql
   source database/schema.sql;
   ```

### Step 2: Configure Database Credentials (if needed)
By default, the connection in `DatabaseConnection.java` connects to:
- **URL:** `jdbc:mysql://localhost:3306/simple_library`
- **User:** `root`
- **Password:** `""` (blank)

If your local MySQL root password is not empty, set environment variables `DB_USER` and `DB_PASSWORD` or edit `DatabaseConnection.java`.

---

## 🏃 Running the Application

### Option A: Using Maven Embedded Tomcat (Fastest)
From this directory, run:
```bash
mvn tomcat7:run
```
Then open your browser to: **`http://localhost:8080/`**

### Option B: Building a WAR file for Standalone Tomcat
1. Build the WAR package:
   ```bash
   mvn clean package
   ```
2. Copy `target/simple-library-system.war` into your Tomcat `webapps/` folder.
3. Start Tomcat and visit: **`http://localhost:8080/simple-library-system/`**

---

## 🔑 Default Accounts

| Username | Password | Role | Permissions |
| :--- | :--- | :--- | :--- |
| **`admin`** | `admin123` | **ADMIN** (Librarian) | Full access: Add, Edit, Delete, Search, and View books |
| **`patron`** | `user123` | **USER** (Patron) | View catalog, Search books by title & author |
| **`alice`** | `alice123` | **USER** (Patron) | View catalog, Search books by title & author |

---

## 👤 Author & Acknowledgements

- **Author:** **Saumya Singhal**
- **GitHub Profile:** [@somaysinghal47-blip](https://github.com/somaysinghal47-blip)
- **Repository:** [somaysinghal47-blip/library-management-system](https://github.com/somaysinghal47-blip/library-management-system)
