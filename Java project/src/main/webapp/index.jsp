<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>BiblioSphere - Simple Library Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

    <jsp:include page="/navbar.jsp" />

    <div class="container">
        <section class="hero">
            <h1>Welcome to BiblioSphere</h1>
            <p>A lightweight, streamlined Library Management System built with Core Java Servlets, JSP, JDBC, and MySQL using the MVC architecture.</p>
            <div style="display: flex; gap: 12px; justify-content: center; flex-wrap: wrap;">
                <a href="${pageContext.request.contextPath}/books" class="btn btn-primary">📖 Browse Book Catalog</a>
                <c:if test="${empty sessionScope.currentUser}">
                    <a href="${pageContext.request.contextPath}/login" class="btn btn-secondary">🔐 Sign In</a>
                    <a href="${pageContext.request.contextPath}/register" class="btn btn-secondary">📝 Create Account</a>
                </c:if>
            </div>
        </section>

        <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(280px, 1fr)); gap: 20px; margin-top: 10px;">
            <div class="card">
                <div class="brand-icon" style="margin-bottom: 14px;">🔍</div>
                <h2 class="card-title">Search & Explore</h2>
                <p style="color: var(--text-muted); margin-bottom: 16px;">
                    Search for books in real-time by book title or author name. Check instant stock availability.
                </p>
                <a href="${pageContext.request.contextPath}/books" class="btn btn-secondary btn-sm">Explore Catalog &rarr;</a>
            </div>

            <div class="card">
                <div class="brand-icon" style="margin-bottom: 14px;">👥</div>
                <h2 class="card-title">Patron Features</h2>
                <p style="color: var(--text-muted); margin-bottom: 16px;">
                    Register as a library patron, securely log into your account, and keep track of book availability.
                </p>
                <a href="${pageContext.request.contextPath}/register" class="btn btn-secondary btn-sm">Join Library &rarr;</a>
            </div>

            <div class="card">
                <div class="brand-icon" style="margin-bottom: 14px;">⚡</div>
                <h2 class="card-title">Librarian Tools</h2>
                <p style="color: var(--text-muted); margin-bottom: 16px;">
                    Admins can effortlessly add new inventory, update stock counts, and maintain the library collection.
                </p>
                <a href="${pageContext.request.contextPath}/login" class="btn btn-secondary btn-sm">Admin Login &rarr;</a>
            </div>
        </div>
    </div>

    <footer>
        <p>&copy; 2026 BiblioSphere &bull; Core Java MVC Library Management System</p>
    </footer>

</body>
</html>
