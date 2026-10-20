<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar">
    <div class="navbar-inner">
        <a href="${pageContext.request.contextPath}/" class="brand">
            <span class="brand-icon">📚</span>
            <span>BiblioSphere</span>
        </a>
        <ul class="nav-links">
            <li><a href="${pageContext.request.contextPath}/books">Catalog</a></li>

            <c:choose>
                <c:when test="${not empty sessionScope.currentUser}">
                    <c:if test="${sessionScope.currentUser.admin}">
                        <li><a href="${pageContext.request.contextPath}/books?action=new">+ Add Book</a></li>
                    </c:if>
                    <li>
                        <span class="user-badge">
                            👤 ${sessionScope.currentUser.username}
                            <span class="role-badge ${sessionScope.currentUser.admin ? 'admin' : ''}">
                                ${sessionScope.currentUser.role}
                            </span>
                        </span>
                    </li>
                    <li><a href="${pageContext.request.contextPath}/logout" class="btn btn-secondary btn-sm">Logout</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="${pageContext.request.contextPath}/login">Login</a></li>
                    <li><a href="${pageContext.request.contextPath}/register" class="btn btn-primary btn-sm">Register</a></li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
</nav>
