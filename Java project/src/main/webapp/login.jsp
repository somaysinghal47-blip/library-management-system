<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - BiblioSphere</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

    <jsp:include page="/navbar.jsp" />

    <div class="container">
        <div class="card form-card">
            <h2 class="card-title" style="text-align: center; margin-bottom: 24px;">Sign In to BiblioSphere</h2>

            <c:if test="${param.loggedOut eq 'true'}">
                <div class="alert alert-info">
                    You have successfully logged out.
                </div>
            </c:if>

            <c:if test="${param.registered eq 'true'}">
                <div class="alert alert-success">
                    Account created successfully! Please sign in with your credentials.
                </div>
            </c:if>

            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger">
                    ${errorMessage}
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/login" method="post">
                <div class="form-group">
                    <label for="username">Username</label>
                    <input type="text" id="username" name="username" class="form-control" 
                           value="${enteredUsername != null ? enteredUsername : ''}" required autofocus>
                </div>

                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" class="form-control" required>
                </div>

                <button type="submit" class="btn btn-primary btn-block" style="margin-top: 10px;">Sign In</button>
            </form>

            <div style="margin-top: 24px; padding-top: 16px; border-top: 1px solid var(--border); font-size: 0.9rem; text-align: center;">
                <p>Don't have an account? <a href="${pageContext.request.contextPath}/register" style="color: var(--primary); font-weight: 600;">Register here</a></p>
            </div>

            <!-- Demo helper box -->
            <div style="margin-top: 16px; background: #f8fafc; border: 1px dashed var(--border); border-radius: var(--radius-sm); padding: 12px; font-size: 0.82rem; color: var(--text-muted);">
                <strong>Default Accounts:</strong><br>
                &bull; <em>Admin:</em> username: <code>admin</code> | password: <code>admin123</code><br>
                &bull; <em>Patron:</em> username: <code>patron</code> | password: <code>user123</code>
            </div>
        </div>
    </div>

    <footer>
        <p>&copy; 2026 BiblioSphere &bull; Core Java MVC Library Management System</p>
    </footer>

</body>
</html>
