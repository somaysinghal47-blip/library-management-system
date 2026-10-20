-- ======================================================
-- Simple Library Management System (BiblioSphere)
-- Database Initialization Script
-- ======================================================

-- 1. Create database if it does not exist
CREATE DATABASE IF NOT EXISTS simple_library
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_unicode_ci;

USE simple_library;

-- 2. Drop existing tables if re-initializing (child/dependent tables first if any)
DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS users;

-- 3. Create 'users' table
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'USER'
) ENGINE=InnoDB;

-- 4. Create 'books' table
CREATE TABLE books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    available_copies INT NOT NULL DEFAULT 1
) ENGINE=InnoDB;

-- 5. Insert Initial Sample Users
-- Roles: 'ADMIN' (Librarian) and 'USER' (Patron)
INSERT INTO users (username, password, role) VALUES 
('admin', 'admin123', 'ADMIN'),
('patron', 'user123', 'USER'),
('alice', 'alice123', 'USER');

-- 6. Insert Initial Sample Books
INSERT INTO books (title, author, available_copies) VALUES
('The Great Gatsby', 'F. Scott Fitzgerald', 4),
('To Kill a Mockingbird', 'Harper Lee', 3),
('1984', 'George Orwell', 5),
('Clean Code: A Handbook of Agile Software Craftsmanship', 'Robert C. Martin', 2),
('Effective Java', 'Joshua Bloch', 3),
('Introduction to Algorithms', 'Thomas H. Cormen', 1),
('Design Patterns: Elements of Reusable Object-Oriented Software', 'Erich Gamma et al.', 2),
('The Pragmatic Programmer', 'Andrew Hunt and David Thomas', 4);
