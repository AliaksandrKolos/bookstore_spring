<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <title><spring:message code="book.title"/></title>
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<h1><spring:message code="book.header"/></h1>

<c:if test="${requestScope.books.isEmpty()}">
    <h2><spring:message code="book.no_book"/></h2>
</c:if>
<c:if test="${!requestScope.books.isEmpty()}">
    <table>
        <th><spring:message code="book.id"/></th>
        <th><spring:message code="book.author"/></th>
        <th><spring:message code="book.isbn"/></th>
        <th><spring:message code="book.title_book"/></th>
        <th><spring:message code="book.genre"/></th>
        <th><spring:message code="book.year"/></th>
        <th><spring:message code="book.pages"/></th>
        <th><spring:message code="book.price"/></th>
        <th><spring:message code="book.cover"/></th>
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

