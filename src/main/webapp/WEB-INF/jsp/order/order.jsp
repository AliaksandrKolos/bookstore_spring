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
<!doctype html>
<html>
<head>
    <title><fmt:message key="order.title"/></title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<h2><fmt:message key="order.header"/></h2>
<table>
    <tr>
        <th><fmt:message key="order.id"/></th>
        <td><c:out value="${order.id}"/></td>
    </tr>
    <tr>
        <th><fmt:message key="order.user_email"/></th>
        <td><c:out value="${order.user.email}"/></td>
    </tr>
    <tr>
        <th>Status<fmt:message key="order.status"/></th>
        <td><c:out value="${order.status}"/></td>
    </tr>
    <tr>
        <th><fmt:message key="order.total_cost"/></th>
        <td><c:out value="${order.cost}"/></td>
    </tr>
</table>

<h2><fmt:message key="order.order_items"/></h2>
<table class="all">
    <tr>
        <th>#</th>
        <th><fmt:message key="order.book_title"/></th>
        <th><fmt:message key="order.quantity"/></th>
        <th><fmt:message key="order.price"/></th>
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

