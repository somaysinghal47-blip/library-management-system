<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - BiblioSphere</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

    <jsp:include page="/navbar.jsp" />

    <div class="container">
        <div class="card form-card">
            <h2 class="card-title" style="text-align: center; margin-bottom: 24px;">Create Patron Account</h2>

            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger">
                    ${errorMessage}
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/register" method="post">
                <div class="form-group">
                    <label for="username">Username</label>
                    <input type="text" id="username" name="username" class="form-control" 
                           value="${enteredUsername != null ? enteredUsername : ''}" required autofocus>
                </div>

                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" class="form-control" required>
                </div>

                <div class="form-group">
                    <label for="confirmPassword">Confirm Password</label>
                    <input type="password" id="confirmPassword" name="confirmPassword" class="form-control" required>
                </div>

                <button type="submit" class="btn btn-primary btn-block" style="margin-top: 10px;">Register</button>
            </form>

            <div style="margin-top: 24px; padding-top: 16px; border-top: 1px solid var(--border); font-size: 0.9rem; text-align: center;">
                <p>Already have an account? <a href="${pageContext.request.contextPath}/login" style="color: var(--primary); font-weight: 600;">Sign in here</a></p>
            </div>
        </div>
    </div>

    <footer>
        <p>&copy; 2026 BiblioSphere &bull; Core Java MVC Library Management System</p>
    </footer>

</body>
</html>
