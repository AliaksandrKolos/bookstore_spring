<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
<html >
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="orders.title"/></title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
<jsp:include page="../navbar.jsp"/>

<h1>${orders.size() > 0 ? 'Your orders:' : 'No orders yet'}</h1>

<div class="pagination">
    <a href="<c:url value='/orders/getAll?page=1&page_size=${param.page_size}'/>">
        <fmt:message key="orders.first"/>
    </a>
    <a href="<c:url value='/orders/getAll?page=${page <= 1 ? 1 : page - 1}&page_size=${param.page_size}'/>">
        <fmt:message key="orders.prev"/>
    </a>
    <span class="current-page"><c:out value="${page}"/></span>
    <a href="<c:url value='/orders/getAll?page=${totalPages > page ? page + 1 : page}&page_size=${param.page_size}'/>">
        <fmt:message key="orders.next"/>
    </a>
    <a href="<c:url value='/orders/getAll?page=${totalPages}&page_size=${param.page_size}'/>">
        <fmt:message key="orders.last"/>
    </a>
</div>

<table>
    <thead>
    <tr>
        <th>#</th>
        <th><fmt:message key="orders.id"/></th>
        <th><fmt:message key="orders.status"/></th>
        <th><fmt:message key="orders.user_email"/></th>
        <th><fmt:message key="orders.total_cost"/></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="order" items="${orders}" varStatus="counter">
        <tr>
            <td><c:out value="${counter.count}"/></td>
            <td><a href="<c:url value='/orders/${order.id}'/>"><c:out value="${order.id}"/></a></td>
            <td><c:out value="${order.status}"/></td>
            <td><c:out value="${order.user.email}"/></td>
            <td><c:out value="${order.cost}"/></td>
            <td>
                <form action="<c:url value='/orders/change_status'/>" method="post">
                    <input type="hidden" name="id" value="${order.id}"/>
                    <select name="status" onchange="this.form.submit()">
                        <option value="">Select Status</option>
                        <option value="PENDING" <c:if test="${order.status == 'PENDING'}">selected</c:if>>PENDING</option>
                        <option value="PAID" <c:if test="${order.status == 'PAID'}">selected</c:if>>PAID</option>
                        <option value="DELIVERED" <c:if test="${order.status == 'DELIVERED'}">selected</c:if>>DELIVERED</option>
                        <option value="CANCELLED" <c:if test="${order.status == 'CANCELLED'}">selected</c:if>>CANCELLED</option>
                    </select>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<c:if test="${not empty dataError}">
    <c:forEach var="error" items="${dataError}">
        <p>"><c:out value="${error}"/></p>
    </c:forEach>
</c:if>

</body>
</html>
