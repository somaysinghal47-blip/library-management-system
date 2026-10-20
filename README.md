# ☕ Advanced Java Projects & BiblioSphere Library Management System

> **A Comprehensive Enterprise & Practical Java Repository featuring the BiblioSphere Web Application (Servlets, JSP, JDBC, MySQL) alongside Core Advanced Java Experiments (MVC, Swing, Sockets, RMI, JDBC).**

👨‍💻 **Author:** **Saumya Singhal**  
🔗 **GitHub:** [@somaysinghal47-blip](https://github.com/somaysinghal47-blip)  
📦 **Repository:** [somaysinghal47-blip/library-management-system](https://github.com/somaysinghal47-blip/library-management-system)

---

## 📑 Table of Contents

1. [Repository Overview](#-repository-overview)
2. [🌟 Flagship Project: BiblioSphere (Library Management System)](#-flagship-project-bibliosphere-library-management-system)
   - [Project Summary & Objectives](#project-summary--objectives)
   - [MVC Architecture Flow](#mvc-architecture-flow)
   - [Key Capabilities & Role-Based Access](#key-capabilities--role-based-access)
   - [Database Schema (`simple_library`)](#database-schema-simple_library)
   - [Project Structure](#project-structure)
   - [Setup & How to Run](#setup--how-to-run)
   - [Default Test Accounts](#default-test-accounts)
3. [📂 Core Advanced Java Experiments](#-core-advanced-java-experiments)
   - [1. 🧮 Calculator (Swing MVC Architecture)](#1--calculator-swing-mvc-architecture)
   - [2. 🌐 Client-Server Socket Programming](#2--client-server-socket-programming)
   - [3. 🗄️ JDBC (MySQL Database Connectivity)](#3-️-jdbc-mysql-database-connectivity)
   - [4. 📡 RMI (Remote Method Invocation)](#4--rmi-remote-method-invocation)
   - [5. 📝 Student Registration Form (Swing GUI)](#5--student-registration-form-swing-gui)
4. [🛠️ Prerequisites & Technology Stack](#️-prerequisites--technology-stack)
5. [👤 Author & License](#-author--license)

---

## 🧭 Repository Overview

This repository showcases advanced enterprise and practical concepts in Java software engineering. It bridges standard academic coursework with production-ready web application development:

```text
library-management-system/
├── Java project/             → 🌟 BiblioSphere Web Application (Servlets, JSP, JDBC, MySQL, Maven)
├── Calculator/               → 🧮 MVC-based GUI Calculator (Java Swing)
├── clientServer/             → 🌐 TCP Socket Programming (Client-Server networking)
├── JDBC/                     → 🗄️ Java Database Connectivity with MySQL
├── RMI/                      → 📡 Remote Method Invocation (Distributed Computing)
├── StudentRegistrationForm/  → 📝 Swing GUI Form with Field Validation
└── README.md                 → Master documentation by Saumya Singhal
```

---

## 🌟 Flagship Project: BiblioSphere (Library Management System)

### Project Summary & Objectives

**BiblioSphere** is a lightweight, full-featured web application engineered to manage library book catalogs, patron accounts, and inventory stock. 

Built in **Core Java** following the classic **Model-View-Controller (MVC)** architectural pattern, BiblioSphere demonstrates fundamental enterprise web patterns without the opacity of heavyweight frameworks:
* Clean separation of concerns between Views (JSP), Business Control (Java Servlets), and Data Persistence (DAOs via JDBC).
* Relational schema hosted in MySQL with parameterized `PreparedStatement` interfaces to prevent SQL injection vulnerabilities.
* Stateful user session tracking via `HttpSession` for role-based authentication (`ADMIN` vs. `USER`).

---

### MVC Architecture Flow

```
                     ┌────────────────────────┐
                     │   User / Web Browser   │
                     └───────────┬────────────┘
                                 │ HTTP Requests (GET/POST)
                                 ▼
      ┌─────────────────────────────────────────────────────┐
      │                  CONTROLLER LAYER                   │
      │                                                     │
      │ • BookServlet      (/books)                         │
      │ • LoginServlet     (/login)                         │
      │ • LogoutServlet    (/logout)                        │
      │ • RegisterServlet  (/register)                      │
      └────────────┬───────────────────────────┬────────────┘
                   │ Forward Data              │ DAO Queries
                   ▼                           ▼
      ┌─────────────────────────┐ ┌─────────────────────────┐
      │       VIEW LAYER        │ │       MODEL LAYER       │
      │                         │ │                         │
      │ • index.jsp             │ │ • Entities:             │
      │ • books.jsp             │ │   Book.java, User.java  │
      │ • login.jsp             │ │ • DAOs:                 │
      │ • register.jsp          │ │   BookDAO.java          │
      │ • add-book.jsp          │ │   UserDAO.java          │
      │ • edit-book.jsp         │ │ • DB Connection:        │
      │ • navbar.jsp, style.css │ │   DatabaseConnection    │
      └─────────────────────────┘ └────────────┬────────────┘
                                               │ JDBC (SQL)
                                               ▼
                                  ┌─────────────────────────┐
                                  │     MySQL DATABASE      │
                                  │    (simple_library)     │
                                  └─────────────────────────┘
```

#### Layer Responsibilities:
1. **Model Layer (`com.library.model`, `com.library.dao`, `com.library.util`)**:
   - `Book.java` & `User.java`: POJO domain objects.
   - `BookDAO.java`: Performs CRUD operations (`getAllBooks`, `searchBooks`, `addBook`, `updateBook`, `deleteBook`).
   - `UserDAO.java`: Handles user authentication (`authenticate`) and registration (`register`, `isUsernameTaken`).
   - `DatabaseConnection.java`: Manages the MySQL JDBC connection lifecycle and auto-close utilities.
2. **Controller Layer (`com.library.controller`)**:
   - `BookServlet.java`: Routes catalog browsing, keyword searches, and admin modifications.
   - `LoginServlet.java`: Authenticates user credentials and establishes HTTP sessions.
   - `LogoutServlet.java`: Terminates active user sessions safely.
   - `RegisterServlet.java`: Validates and registers new patrons.
3. **View Layer (`src/main/webapp/`)**:
   - Responsive JSPs with JSTL core tag rendering.
   - Tailored modern stylesheet (`style.css`) with zero external CDN dependencies for offline speed.

---

### Key Capabilities & Role-Based Access

* **For Patrons (Members):**
  - Self-service registration & secure login.
  - Live catalog search by book title or author keyword.
  - Real-time stock status badges (`In Stock (X)` vs `Out of Stock`).
* **For Librarians (Admins):**
  - Secured administrative capabilities.
  - Add new books to the catalog with copy tracking.
  - Update book titles, authors, or adjust physical stock.
  - Delete books with confirmation prompts.
  - Protected endpoints: Unauthorized non-admin users cannot access modify/delete routes.

---

### Database Schema (`simple_library`)

The database consists of two normalized tables:

#### 1. `users` Table
```sql
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'USER'
);
```

#### 2. `books` Table
```sql
CREATE TABLE books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    available_copies INT NOT NULL DEFAULT 1
);
```

---

### Project Structure

```text
Java project/
├── pom.xml                                    # Maven configuration & Tomcat plugin
├── README.md                                  # Sub-module documentation
├── database/
│   └── schema.sql                             # SQL DDL & initial seed records
└── src/
    └── main/
        ├── java/com/library/
        │   ├── controller/
        │   │   ├── BookServlet.java           # Catalog routing, search & CRUD
        │   │   ├── LoginServlet.java          # Authentication controller
        │   │   ├── LogoutServlet.java         # Session cleanup
        │   │   └── RegisterServlet.java       # Patron registration
        │   ├── dao/
        │   │   ├── BookDAO.java               # Book JDBC queries
        │   │   └── UserDAO.java               # User JDBC queries
        │   ├── model/
        │   │   ├── Book.java                  # Book entity POJO
        │   │   └── User.java                  # User entity POJO
        │   └── util/
        │       └── DatabaseConnection.java    # JDBC connection manager
        └── webapp/
            ├── WEB-INF/web.xml                # Web deployment descriptor
            ├── css/style.css                  # Custom responsive CSS
            ├── index.jsp                      # Landing page
            ├── login.jsp                      # Login form
            ├── register.jsp                   # Registration form
            ├── books.jsp                      # Catalog grid & search
            ├── add-book.jsp                   # Add book form (Admin)
            ├── edit-book.jsp                  # Edit book form (Admin)
            └── navbar.jsp                     # Reusable header component
```

---

### Setup & How to Run

1. **Database Setup:**
   Run the schema script in your MySQL console:
   ```sql
   source "Java project/database/schema.sql";
   ```
2. **Configure Database Credentials:**
   If your MySQL root password is non-empty, edit `DatabaseConnection.java` or set environment variables `DB_USER` and `DB_PASSWORD`.
3. **Run using Maven Embedded Tomcat (Fastest):**
   ```bash
   cd "Java project"
   mvn tomcat7:run
   ```
   Open your browser to: **`http://localhost:8080/`**
4. **Run in Eclipse / IntelliJ IDEA:**
   - Import `Java project/` as an Existing Maven Project.
   - Run on Server (Apache Tomcat 8.5/9.x).

---

### Default Test Accounts

| Username | Password | Role | Permissions |
| :--- | :--- | :--- | :--- |
| **`admin`** | `admin123` | **ADMIN** | Full control: Add, edit, delete books, search & view |
| **`patron`** | `user123` | **USER** | Patron: Browse catalog, search by title and author |
| **`alice`** | `alice123` | **USER** | Secondary patron account |

---

## 📂 Core Advanced Java Experiments

### 1. 🧮 Calculator (Swing MVC Architecture)
* **Directory:** `Calculator/`
* **Description:** A desktop GUI calculator built strictly using the **Model-View-Controller (MVC)** design pattern in Java Swing.
* **Key Components:**
  - `CalculatorModel.java`: Implements core arithmetic computation logic.
  - `CalculatorView.java`: Builds the graphical interface using `JFrame`, `JTextField`, and `JButton`.
  - `CalculatorController.java`: Listens to user interactions and bridges the View and Model.
  - `Main.java`: Application launcher.
* **To Run:**
  ```bash
  cd Calculator
  javac *.java
  java Main
  ```

---

### 2. 🌐 Client-Server Socket Programming
* **Directory:** `clientServer/`
* **Description:** Demonstrates bidirectional TCP socket communication over `localhost:5000`.
* **Key Components:**
  - `server.java`: Instantiates a `ServerSocket`, listens on port 5000, receives the client message, and responds.
  - `client.java`: Connects via `Socket`, transmits a request, and displays the server response.
* **To Run:**
  ```bash
  cd clientServer
  # Terminal 1:
  javac server.java
  java server

  # Terminal 2:
  javac client.java
  java client
  ```

---

### 3. 🗄️ JDBC (MySQL Database Connectivity)
* **Directory:** `JDBC/`
* **Description:** Demonstrates connecting Java applications directly to MySQL using JDBC type-4 drivers. Executes table creation, data insertion with `PreparedStatement`, and record queries with `ResultSet`.
* **Key Components:**
  - `JdbcDemo.java`: Connects to MySQL, inserts student records, and queries the database.
  - `App.java`: Auxiliary helper demo.
* **To Run:**
  ```bash
  cd JDBC
  javac JdbcDemo.java
  java -cp ".;mysql-connector-j-8.3.0.jar" JdbcDemo
  ```

---

### 4. 📡 RMI (Remote Method Invocation)
* **Directory:** `RMI/`
* **Description:** Distributed computing in Java allowing a client to invoke remote methods residing on a server process across the Java Virtual Machine boundary.
* **Key Components:**
  - `calculator.java`: Remote interface extending `java.rmi.Remote`.
  - `calculatorImpl.java`: Implements remote operations extending `UnicastRemoteObject`.
  - `server.java`: Binds the remote service in the `rmiregistry`.
  - `client.java`: Looks up the stub using `Naming.lookup()` and calls remote methods.
* **To Run:**
  ```bash
  cd RMI
  javac *.java
  rmiregistry &
  java server
  java client
  ```

---

### 5. 📝 Student Registration Form (Swing GUI)
* **Directory:** `StudentRegistrationForm/`
* **Description:** Comprehensive desktop form application with full field-level validation and dialog confirmations.
* **Features:**
  - Inputs for Name, Roll Number, Gender (radio group), Branch selection, and Terms acceptance.
  - Input validation with `JOptionPane` error alerts.
  - Reset and Submit actions with confirmation dialogs.
* **To Run:**
  ```bash
  cd StudentRegistrationForm
  javac StudentRegistration.java
  java StudentRegistration
  ```

---

## 🛠️ Prerequisites & Technology Stack

| Tool / Technology | Minimum Version | Notes |
| :--- | :--- | :--- |
| **Java JDK** | 8 or higher (8, 11, 17, 21) | Required for compilation and runtime |
| **MySQL Server** | 5.7 or 8.0+ | Relational database storage |
| **MySQL Connector/J**| 8.x | Type 4 JDBC driver |
| **Apache Tomcat** | 8.5 or 9.x | Servlet 4.0 / JSP 2.3 container |
| **Apache Maven** | 3.6+ | Automated dependency management |
| **Git** | 2.x | Distributed version control |

---

## 👤 Author & License

* **Developer:** **Saumya Singhal**
* **GitHub:** [@somaysinghal47-blip](https://github.com/somaysinghal47-blip)
* **Project Repository:** [library-management-system](https://github.com/somaysinghal47-blip/library-management-system)
* **License:** Open Source for educational, academic, and evaluation purposes.
