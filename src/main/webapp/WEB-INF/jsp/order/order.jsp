<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!doctype html>
<html>
<head>
    <title><spring:message code="order.title"/></title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<h2><spring:message code="order.header"/></h2>
<table>
    <tr>
        <th><spring:message code="order.id"/></th>
        <td><c:out value="${order.id}"/></td>
    </tr>
    <tr>
        <th><spring:message code="order.user_email"/></th>
        <td><c:out value="${order.user.email}"/></td>
    </tr>
    <tr>
        <th>Status<spring:message code="order.status"/></th>
        <td><c:out value="${order.status}"/></td>
    </tr>
    <tr>
        <th><spring:message code="order.total_cost"/></th>
        <td><c:out value="${order.cost}"/></td>
    </tr>
</table>

<h2><spring:message code="order.order_items"/></h2>
<table class="all">
    <tr>
        <th>#</th>
        <th><spring:message code="order.book_title"/></th>
        <th><spring:message code="order.quantity"/></th>
        <th><spring:message code="order.price"/></th>
    </tr>
    <c:forEach items="${order.items}" var="item" varStatus="counter">
        <tr>
            <td><c:out value="${counter.count}"/></td>
            <td><c:out value="${item.book.title}"/></td>
            <td><c:out value="${item.quantity}"/></td>
            <td><c:out value="${item.price}"/></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>

