<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Book Catalog - BiblioSphere</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

    <jsp:include page="/navbar.jsp" />

    <div class="container">
        <!-- Notification Alerts -->
        <c:if test="${param.msg eq 'added'}">
            <div class="alert alert-success">Book was successfully added to the catalog!</div>
        </c:if>
        <c:if test="${param.msg eq 'updated'}">
            <div class="alert alert-success">Book details have been successfully updated!</div>
        </c:if>
        <c:if test="${param.msg eq 'deleted'}">
            <div class="alert alert-success">Book was removed from the catalog.</div>
        </c:if>
        <c:if test="${param.error eq 'unauthorized'}">
            <div class="alert alert-danger">Access denied. Only Librarians/Admins can perform this action.</div>
        </c:if>
        <c:if test="${param.error eq 'notfound'}">
            <div class="alert alert-danger">The requested book was not found.</div>
        </c:if>

        <div class="toolbar">
            <!-- Search Form -->
            <form action="${pageContext.request.contextPath}/books" method="get" class="search-bar">
                <input type="hidden" name="action" value="list">
                <input type="text" name="search" class="form-control" 
                       placeholder="Search by title or author..." 
                       value="${searchQuery != null ? searchQuery : ''}">
                <button type="submit" class="btn btn-secondary">Search</button>
                <c:if test="${not empty searchQuery}">
                    <a href="${pageContext.request.contextPath}/books" class="btn btn-secondary">Clear</a>
                </c:if>
            </form>

            <!-- Admin Actions -->
            <c:if test="${sessionScope.currentUser != null && sessionScope.currentUser.admin}">
                <div>
                    <a href="${pageContext.request.contextPath}/books?action=new" class="btn btn-primary">
                        + Add New Book
                    </a>
                </div>
            </c:if>
        </div>

        <c:if test="${not empty searchQuery}">
            <p style="margin-bottom: 16px; color: var(--text-muted); font-size: 0.95rem;">
                Showing search results for: <strong>"${searchQuery}"</strong> (${bookList != null ? bookList.size() : 0} found)
            </p>
        </c:if>

        <!-- Books Table -->
        <div class="table-responsive">
            <table class="table">
                <thead>
                    <tr>
                        <th style="width: 60px;">ID</th>
                        <th>Title</th>
                        <th>Author</th>
                        <th style="width: 140px;">Availability</th>
                        <c:if test="${sessionScope.currentUser != null && sessionScope.currentUser.admin}">
                            <th style="width: 160px; text-align: right;">Actions</th>
                        </c:if>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${not empty bookList}">
                            <c:forEach var="book" items="${bookList}">
                                <tr>
                                    <td>#${book.id}</td>
                                    <td><strong>${book.title}</strong></td>
                                    <td>${book.author}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${book.availableCopies > 0}">
                                                <span class="badge badge-success">
                                                    In Stock (${book.availableCopies})
                                                </span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge badge-danger">Out of Stock</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <c:if test="${sessionScope.currentUser != null && sessionScope.currentUser.admin}">
                                        <td style="text-align: right;">
                                            <a href="${pageContext.request.contextPath}/books?action=edit&id=${book.id}" 
                                               class="btn btn-secondary btn-sm" style="margin-right: 4px;">Edit</a>
                                            <a href="${pageContext.request.contextPath}/books?action=delete&id=${book.id}" 
                                               class="btn btn-danger btn-sm" 
                                               onclick="return confirm('Are you sure you want to delete \'${book.title}\'?');">Delete</a>
                                        </td>
                                    </c:if>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="${(sessionScope.currentUser != null && sessionScope.currentUser.admin) ? 5 : 4}" 
                                    style="text-align: center; padding: 40px; color: var(--text-muted);">
                                    <p style="font-size: 1.1rem; margin-bottom: 8px;">No books found in the catalog.</p>
                                    <c:if test="${sessionScope.currentUser != null && sessionScope.currentUser.admin}">
                                        <a href="${pageContext.request.contextPath}/books?action=new" class="btn btn-primary btn-sm">Add the first book</a>
                                    </c:if>
                                </td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>
    </div>

    <footer>
        <p>&copy; 2026 BiblioSphere &bull; Core Java MVC Library Management System</p>
    </footer>

</body>
</html>
