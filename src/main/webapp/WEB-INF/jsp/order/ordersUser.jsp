<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html>
<head>
    <title><spring:message code="ordersUser.title"/></title>
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
    <a href="${pageContext.request.contextPath}/orders/orders_user/${sessionScope.user.id}?page=0&size=${param.size}">
        <spring:message code="orders.first"/>
    </a>

    <a href="${pageContext.request.contextPath}/orders/orders_user/${sessionScope.user.id}?page=${page <= 0 ? 0 : page - 1}&size=${param.size}">
        <spring:message code="orders.prev"/>
    </a>

    <span class="current-page"><c:out value="${page + 1}"/></span>

    <a href="${pageContext.request.contextPath}/orders/orders_user/${sessionScope.user.id}?page=${page < totalPages - 1 ? page + 1 : totalPages - 1}&size=${param.size}">
        <spring:message code="orders.next"/>
    </a>

    <a href="${pageContext.request.contextPath}/orders/orders_user/${sessionScope.user.id}?page=${totalPages - 1}&size=${param.size}">
        <spring:message code="orders.last"/>
    </a>
</div>



<table>
    <tr>
        <th>#</th>
        <th><spring:message code="ordersUser.id"/></th>
        <th><spring:message code="ordersUser.status"/></th>
        <th><spring:message code="ordersUser.total_cost"/></th>
        <th><spring:message code="ordersUser.action"/></th>
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
                        <input type="submit" value="<spring:message code="ordersUser.order_cancel"/>">
                    </form>
                </td>
            </c:if>
        </tr>
    </c:forEach>
</table>
</body>
</html>
