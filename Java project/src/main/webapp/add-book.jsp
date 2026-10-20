<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Book - BiblioSphere</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

    <jsp:include page="/navbar.jsp" />

    <div class="container">
        <div class="card form-card">
            <h2 class="card-title" style="margin-bottom: 20px;">Add New Book</h2>

            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger">
                    ${errorMessage}
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/books" method="post">
                <input type="hidden" name="action" value="insert">

                <div class="form-group">
                    <label for="title">Book Title</label>
                    <input type="text" id="title" name="title" class="form-control" 
                           placeholder="e.g. Design Patterns" 
                           value="${param.title != null ? param.title : ''}" required autofocus>
                </div>

                <div class="form-group">
                    <label for="author">Author</label>
                    <input type="text" id="author" name="author" class="form-control" 
                           placeholder="e.g. Erich Gamma" 
                           value="${param.author != null ? param.author : ''}" required>
                </div>

                <div class="form-group">
                    <label for="availableCopies">Available Copies</label>
                    <input type="number" id="availableCopies" name="availableCopies" class="form-control" 
                           min="0" step="1" 
                           value="${param.availableCopies != null ? param.availableCopies : '1'}" required>
                </div>

                <div style="display: flex; gap: 12px; margin-top: 24px;">
                    <button type="submit" class="btn btn-primary" style="flex: 1;">Save Book</button>
                    <a href="${pageContext.request.contextPath}/books" class="btn btn-secondary">Cancel</a>
                </div>
            </form>
        </div>
    </div>

    <footer>
        <p>&copy; 2026 BiblioSphere &bull; Core Java MVC Library Management System</p>
    </footer>

</body>
</html>
