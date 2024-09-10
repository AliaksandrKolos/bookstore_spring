<%@ page contentType="text/html;charset=UTF-8" %>
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
    <link rel="stylesheet" type="text/css" href="../css/style.css">
    <title><fmt:message key="orderConfirmation.title"/></title>
</head>
<body>
<jsp:include page="../navbar.jsp"/>

<h1><fmt:message key="orderConfirmation.header"/></h1>

<p><fmt:message key="orderConfirmation.you_order"/> <c:out value="${order.id}"/>.</p>

<h2><fmt:message key="orderConfirmation.order_details"/></h2>
<table>
    <tr>
        <th><fmt:message key="orderConfirmation.book_title"/></th>
        <th><fmt:message key="orderConfirmation.quantity"/></th>
        <th><fmt:message key="orderConfirmation.price"/></th>
        <th><fmt:message key="orderConfirmation.total"/></th>
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
        <td colspan="3" style="text-align:right;"><strong><fmt:message key="orderConfirmation.total_cost"/></strong></td>
        <td><strong><c:out value="${order.cost}"/></strong></td>
    </tr>
</table>
</body>
</html>
