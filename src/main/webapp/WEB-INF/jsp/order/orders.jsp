<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html >
<head>
    <meta charset="UTF-8">
    <title><spring:message code="orders.title"/></title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
<jsp:include page="../navbar.jsp"/>

<h1>${orders.size() > 0 ? 'Your orders:' : 'No orders yet'}</h1>

<div class="pagination">
    <a href="${pageContext.request.contextPath}/orders/getAll?page=0&size=${param.size}">
        <spring:message code="orders.first"/>
    </a>

    <a href="${pageContext.request.contextPath}/orders/getAll?page=${page <= 0 ? 0 : page - 1}&size=${param.size}">
        <spring:message code="orders.prev"/>
    </a>

    <span class="current-page"><c:out value="${page + 1}"/></span>

    <a href="${pageContext.request.contextPath}/orders/getAll?page=${page < totalPages - 1 ? page + 1 : totalPages - 1}&size=${param.size}">
        <spring:message code="orders.next"/>
    </a>

    <a href="${pageContext.request.contextPath}/orders/getAll?page=${totalPages - 1}&size=${param.size}">
        <spring:message code="orders.last"/>
    </a>
</div>



<table>
    <thead>
    <tr>
        <th>#</th>
        <th><spring:message code="orders.id"/></th>
        <th><spring:message code="orders.status"/></th>
        <th><spring:message code="orders.user_email"/></th>
        <th><spring:message code="orders.total_cost"/></th>
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
                <form action="${pageContext.request.contextPath}/orders/change_status" method="post">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
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
