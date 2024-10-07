<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="../css/style.css">
    <title><spring:message code="books.title"/></title>
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<h1><spring:message code="books.header"/></h1>

<c:if test="${books == null || books.isEmpty()}">
    <h2><spring:message code="books.no_books"/></h2>
</c:if>

<c:if test="${books != null && !books.isEmpty()}">
    <form method="get" action="${pageContext.request.contextPath}/books/search_title">
        <input type="text" placeholder="<spring:message code='books.search'/>" name="title" value="${param.title}" />
        <input type="hidden" name="page" value="${param.page != null ? param.page : 1}" />
        <input type="hidden" name="size" value="${param.size != null ? param.size : 5}" />

        <button type="submit" class="btn"><spring:message code="books.search"/></button>
    </form>

    <div class="pagination">
        <a href="${pageContext.request.contextPath}/books/search_title?title=${param.title}&page=0&size=${param.size}">
            <spring:message code="books.first"/>
        </a>

        <a href="${pageContext.request.contextPath}/books/search_title?title=${param.title}&page=${page <= 0 ? 0 : page - 1}&size=${param.size}">
            <spring:message code="books.prev"/>
        </a>

        <c:out value="${page + 1}"/>

        <a href="${pageContext.request.contextPath}/books/search_title?title=${param.title}&page=${page < totalPages - 1 ? page + 1 : totalPages - 1}&size=${param.size}">
            <spring:message code="books.next"/>
        </a>

        <a href="${pageContext.request.contextPath}/books/search_title?title=${param.title}&page=${totalPages - 1}&size=${param.size}">
            <spring:message code="books.last"/>
        </a>
    </div>



    <table>
        <thead>
        <tr>
            <th><spring:message code="books.id"/></th>
            <th><spring:message code="books.title_book"/></th>
            <th><spring:message code="books.author"/></th>
            <c:if test="${sessionScope.user.role eq 'MANAGER' || sessionScope.user.role eq 'ADMIN'}">
                <th><spring:message code="books.action"/></th>
                <th><spring:message code="books.action"/></th>
            </c:if>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${books}" var="book">
            <tr>
                <td><c:out value="${book.id}"/></td>
                <td><a href="${pageContext.request.contextPath}/books/${book.id}"><c:out value="${book.title}"/></a></td>
                <td><c:out value="${book.author}"/></td>
                <td>
                    <form method="post" action="${pageContext.request.contextPath}/books/addCart" class="form-inline">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                        <input type="hidden" name="bookId" value="<c:out value="${book.id}"/>">
                        <input type="number" name="quantity" value="1" min="1">
                        <input type="submit" value="<spring:message code='books.add_cart'/>">
                    </form>
                </td>
                <c:if test="${sessionScope.user.role eq 'MANAGER' || sessionScope.user.role eq 'ADMIN'}">
                    <td><a href="${pageContext.request.contextPath}/books/edit/${book.id}">Edit</a></td>
                    <td>
                        <form method="post" action="${pageContext.request.contextPath}/books/delete/${book.id}">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                            <input type="submit" value="<spring:message code='books.button_delete'/>">
                        </form>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>

</body>
</html>
