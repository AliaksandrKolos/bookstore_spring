<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
    <title><fmt:message key="orders.title"/></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/style.css' />">
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<h1>${orders.size() > 0 ? 'Your orders:' : 'No orders yet'}</h1>
<div class="pagination">
    <a href="controller?command=orders&page=1"><fmt:message key="orders.first"/></a>
    <a href="controller?command=orders&page=<c:out value="${page <= 1 ? 1 : page - 1}"/>"><fmt:message
            key="orders.prev"/></a>
    <span class="current-page"><c:out value="${page}"/></span>
    <a href="controller?command=orders&page=<c:out value="${totalPages > page ? page + 1 : page}"/>"><fmt:message
            key="orders.next"/></a>
    <a href="controller?command=orders&page=<c:out value="${totalPages}"/>"><fmt:message key="orders.last"/></a>
</div>

<table>
    <tr>
        <th>#</th>
        <th><fmt:message key="orders.id"/></th>
        <th><fmt:message key="orders.status"/></th>
        <th><fmt:message key="orders.user_email"/></th>
        <th><fmt:message key="orders.total_cost"/></th>
    </tr>

    <c:forEach items="${orders}" var="order" varStatus="counter">
        <tr>
            <td><c:out value="${counter.count}"/></td>
            <td><a href="controller?command=order&id=<c:out value="${order.id}"/>"><c:out value="${order.id}"/></a></td>
            <td><c:out value="${order.status}"/></td>
            <td><c:out value="${order.user.email}"/></td>
            <td><c:out value="${order.cost}"/></td>
            <td>
                <form action="controller" method="post">
                    <input type="hidden" name="id" value="${order.id}"/>
                    <select name="status" onchange="this.form.submit()">
                        <option value="">Select Status</option>
                        <option value="PENDING" <c:if test="${order.status == 'PENDING'}">selected</c:if>>PENDING
                        </option>
                        <option value="PAID" <c:if test="${order.status == 'PAID'}">selected</c:if>>PAID</option>
                        <option value="DELIVERED" <c:if test="${order.status == 'DELIVERED'}">selected</c:if>>
                            DELIVERED
                        </option>
                        <option value="CANCELLED" <c:if test="${order.status == 'CANCELLED'}">selected</c:if>>
                            CANCELLED
                        </option>
                    </select>
                    <input type="hidden" name="command" value="change_order_status"/>
                </form>
            </td>
        </tr>
    </c:forEach>

    <c:if test="${not empty dataError}">
        <c:forEach items="${dataError}" var="error">
            <p><c:out value="${error}"/></p>
        </c:forEach>
    </c:if>
</table>
</body>
</html>
