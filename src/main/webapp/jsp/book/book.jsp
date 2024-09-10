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
    <fmt:setLocale value="ru"/>
</c:if>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="..css/style.css">
    <title><fmt:message key="book.title"/></title>
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<h1><fmt:message key="book.header"/></h1>

<c:if test="${requestScope.books.isEmpty()}">
    <h2><fmt:message key="book.no_book"/></h2>
</c:if>
<c:if test="${!requestScope.books.isEmpty()}">
    <table>
        <th><fmt:message key="book.id"/></th>
        <th><fmt:message key="book.author"/></th>
        <th><fmt:message key="book.isbn"/></th>
        <th><fmt:message key="book.title_book"/></th>
        <th><fmt:message key="book.genre"/></th>
        <th><fmt:message key="book.year"/></th>
        <th><fmt:message key="book.pages"/></th>
        <th><fmt:message key="book.price"/></th>
        <th><fmt:message key="book.cover"/></th>
        <tbody>
        <tr>
            <td><c:out value="${book.id}"/></td>
            <td><c:out value="${book.author}"/></td>
            <td><c:out value="${book.isbn}"/></td>
            <td><c:out value="${book.title}"/></td>
            <td><c:out value="${book.genre}"/></td>
            <td><c:out value="${book.year}"/></td>
            <td><c:out value="${book.pages}"/></td>
            <td><c:out value="${book.price}"/></td>
            <td><c:out value="${book.cover.toString()}"/></td>
        </tr>
        </tbody>
    </table>
</c:if>
</body>
</html>

