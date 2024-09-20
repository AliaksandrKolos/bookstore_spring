<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="ordersUser.title"/></title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
<jsp:include page="../navbar.jsp"/>

<h1>${orders.size() > 0 ? 'Your orders:' : 'No orders yet'}</h1>

<c:if test="${not empty dataError}">
    <div class="error-message">
        <p><c:out value="${dataError}"/></p>
    </div>
</c:if>

<div class="pagination">
    <a href="orders/orders_user/${sessionScope.user.id}&page=1"><fmt:message key="orders.first"/></a>
    <a href="orders/orders_user/${sessionScope.user.id}&page=${page <= 1 ? 1 : page - 1}"><fmt:message key="orders.prev"/></a>
    <span class="current-page"><c:out value="${page}"/></span>
    <a href="orders/orders_user/${sessionScope.user.id}&page=${totalPages > page ? page + 1 : page}"><fmt:message key="orders.next"/></a>
    <a href="orders/orders_user/${sessionScope.user.id}&page=${totalPages}"><fmt:message key="orders.last"/></a>
</div>

<table>
    <tr>
        <th>#</th>
        <th><fmt:message key="ordersUser.id"/></th>
        <th><fmt:message key="ordersUser.status"/></th>
        <th><fmt:message key="ordersUser.total_cost"/></th>
        <th><fmt:message key="ordersUser.action"/></th>
    </tr>
    <c:forEach items="${orders}" var="order" varStatus="counter">
        <tr>
            <td><c:out value="${counter.count}"/></td>
            <td><a href="<c:url value='/orders/${order.id}'/>"><c:out value="${order.id}"/></a></td>
            <td><c:out value="${order.status}"/></td>
            <td><c:out value="${order.cost}"/></td>
            <c:if test="${order.status.name() != 'CANCELLED'}">
                <td>
                    <form method="post" action="<c:url value='/orders/cancelOrder/${order.id}'/>">
                        <input type="submit" value="<fmt:message key="ordersUser.order_cancel"/>">
                    </form>
                </td>
            </c:if>
        </tr>
    </c:forEach>
</table>
</body>
</html>
