<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:if test="${sessionScope.lang != null}">
    <fmt:setBundle basename="messages"/>
    <fmt:setLocale value="${sessionScope.lang}"/>
</c:if>
<c:if test="${sessionScope.lang =='ru'}">
    <fmt:setBundle basename="messages_ru"/>
    <fmt:setLocale value="ru"/>
</c:if>
<c:if test="${sessionScope.lang =='de'}">
    <fmt:setBundle basename="messages_de"/>
    <fmt:setLocale value="de"/>
</c:if>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="../css/style.css">
    <title><fmt:message key="users.title"/></title>
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<h1><fmt:message key="users.header"/></h1>

<c:if test="${users.isEmpty()}">
    <h2><fmt:message key="users.no_users"/></h2>
</c:if>

<c:if test="${!users.isEmpty()}">

    <form method="get" action="/search_lastName">
        <input type="text" placeholder="<fmt:message key='users.search'/>" name="lastName" value="${param.lastName}" />
        <input type="hidden" name="page" value="${page != null ? page : 1}" />
        <input type="hidden" name="page_size" value="${page_size != null ? page_size : 5}" />
        <button type="submit" class="btn"><fmt:message key="users.search"/></button>
    </form>

    <div class="pagination">
        <a href="/users/search_lastName?page=0&page_size=${param.page_size}&lastName=${param.lastName}">
            <fmt:message key="users.first"/>
        </a>
        <a href="/users/search_lastName?page=${page <= 0 ? 0 : page - 1}&page_size=${param.page_size}&lastName=${param.lastName}">
            <fmt:message key="users.prev"/>
        </a>
        <span class="current-page">${page + 1}</span>
        <a href="/users/search_lastName?page=${page < totalPages - 1 ? page + 1 : totalPages - 1}&page_size=${param.page_size}&lastName=${param.lastName}">
            <fmt:message key="users.next"/>
        </a>
        <a href="/users/search_lastName?page=${totalPages - 1}&page_size=${param.page_size}&lastName=${param.lastName}">
            <fmt:message key="users.last"/>
        </a>
    </div>

<%--    <div class="pagination">--%>
<%--        <a href="${pageContext.request.contextPath}/books/getAll?page=0&page_size=${param.page_size}">--%>
<%--            <fmt:message key="books.first"/>--%>
<%--        </a>--%>

<%--        <a href="${pageContext.request.contextPath}/books/getAll?page=${page <= 0 ? 0 : page - 1}&page_size=${param.page_size}">--%>
<%--            <fmt:message key="books.prev"/>--%>
<%--        </a>--%>

<%--        <c:out value="${page + 1}"/>--%>

<%--        <a href="${pageContext.request.contextPath}/books/getAll?page=${page < totalPages - 1 ? page + 1 : totalPages - 1}&page_size=${param.page_size}">--%>
<%--            <fmt:message key="books.next"/>--%>
<%--        </a>--%>

<%--        <a href="${pageContext.request.contextPath}/books/getAll?page=${totalPages - 1}&page_size=${param.page_size}">--%>
<%--            <fmt:message key="books.last"/>--%>
<%--        </a>--%>
<%--    </div>--%>


    <table>
        <thead>
        <tr>
            <th><fmt:message key="users.id"/></th>
            <th><fmt:message key="users.fist_name"/></th>
            <th><fmt:message key="users.last_name"/></th>
            <th><fmt:message key="users.email"/></th>
            <th><fmt:message key="users.action"/></th>
            <th><fmt:message key="users.action"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${users}" var="user">
            <tr>
                <td>${user.id}</td>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td><a href="/users/${user.id}">${user.email}</a></td>
                <td><a href="/users/edit/${user.id}"><fmt:message key="users.edit"/></a></td>
                <td>
                    <form method="post" action="/users/delete/${user.id}">
                        <input type="submit" value="<fmt:message key='users.delete'/>">
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</c:if>
</body>
</html>
