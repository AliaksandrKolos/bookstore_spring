<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="../css/style.css">
    <title><spring:message code="users.title"/></title>
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<h1><spring:message code="users.header"/></h1>

<c:if test="${users.isEmpty()}">
    <h2><spring:message code="users.no_users"/></h2>
</c:if>

<c:if test="${!users.isEmpty()}">

    <form method="get" action="/search_lastName">
        <input type="text" placeholder="<spring:message code='users.search'/>" name="lastName" value="${param.lastName}" />
        <input type="hidden" name="page" value="${page != null ? page : 1}" />
        <input type="hidden" name="size" value="${size != null ? size : 5}" />
        <button type="submit" class="btn"><spring:message code="users.search"/></button>
    </form>

    <div class="pagination">
        <a href="/users/search_lastName?page=0&size=${param.size}&lastName=${param.lastName}">
            <spring:message code="users.first"/>
        </a>
        <a href="/users/search_lastName?page=${page <= 0 ? 0 : page - 1}&size=${param.size}&lastName=${param.lastName}">
            <spring:message code="users.prev"/>
        </a>
        <span class="current-page">${page + 1}</span>
        <a href="/users/search_lastName?page=${page < totalPages - 1 ? page + 1 : totalPages - 1}&size=${param.size}&lastName=${param.lastName}">
            <spring:message code="users.next"/>
        </a>
        <a href="/users/search_lastName?page=${totalPages - 1}&size=${param.size}&lastName=${param.lastName}">
            <spring:message code="users.last"/>
        </a>
    </div>

    <table>
        <thead>
        <tr>
            <th><spring:message code="users.id"/></th>
            <th><spring:message code="users.fist_name"/></th>
            <th><spring:message code="users.last_name"/></th>
            <th><spring:message code="users.email"/></th>
            <th><spring:message code="users.action"/></th>
            <th><spring:message code="users.action"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${users}" var="user">
            <tr>
                <td>${user.id}</td>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td><a href="/users/${user.id}">${user.email}</a></td>
                <td><a href="/users/edit/${user.id}"><spring:message code="users.edit"/></a></td>
                <td>
                    <form method="post" action="/users/delete/${user.id}">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                        <input type="submit" value="<spring:message code='users.delete'/>">
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</c:if>
</body>
</html>
