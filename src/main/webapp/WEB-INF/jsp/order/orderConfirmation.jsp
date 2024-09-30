<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <title><spring:message code="orderConfirmation.title"/></title>
</head>
<body>
<jsp:include page="../navbar.jsp"/>

<h1><spring:message code="orderConfirmation.header"/></h1>

<p><spring:message code="orderConfirmation.you_order"/> <c:out value="${order.id}"/>.</p>

<h2><spring:message code="orderConfirmation.order_details"/></h2>
<table>
    <tr>
        <th><spring:message code="orderConfirmation.book_title"/></th>
        <th><spring:message code="orderConfirmation.quantity"/></th>
        <th><spring:message code="orderConfirmation.price"/></th>
        <th><spring:message code="orderConfirmation.total"/></th>
    </tr>
    <c:forEach var="item" items="${order.items}">
        <tr>
            <td><c:out value="${item.book.title}"/></td>
            <td><c:out value="${item.quantity}"/></td>
            <td><c:out value="${item.price}"/></td>
            <td><c:out value="${item.price * item.quantity}"/></td>
        </tr>
    </c:forEach>
    <tr>
        <td colspan="3" style="text-align:right;"><strong><spring:message code="orderConfirmation.total_cost"/></strong></td>
        <td><strong><c:out value="${order.cost}"/></strong></td>
    </tr>
</table>
</body>
</html>
