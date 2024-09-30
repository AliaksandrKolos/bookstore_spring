<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="../css/style.css">
    <title><spring:message code="cart.title"/></title>
</head>
<body>
<jsp:include page="navbar.jsp"/>

<c:choose>
    <c:when test="${sessionScope.cart == null || sessionScope.cart.isEmpty()}">
        <h1><spring:message code="cart.cart_empty"/></h1>
    </c:when>
    <c:otherwise>
        <h1><spring:message code="cart.you_cart"/></h1>

        <c:set var="totalPrice" value="0"/>

        <table>
            <tr>
                <th><spring:message code="cart.title_book"/></th>
                <th><spring:message code="cart.count"/></th>
                <th><spring:message code="cart.price"/></th>
                <th><spring:message code="cart.result"/></th>
            </tr>

            <c:forEach var="entry" items="${sessionScope.cart}">
                <tr>
                    <td><c:out value="${entry.key.title}"/></td>
                    <td><c:out value="${entry.value}"/></td>
                    <td><c:out value="${entry.key.price}"/></td>
                    <td><c:out value="${entry.key.price * entry.value}"/></td>
                </tr>

                <c:set var="totalPrice" value="${totalPrice + (entry.key.price * entry.value)}"/>
            </c:forEach>

            <tr>
                <td><strong><spring:message code="cart.total_cost"/></strong></td>
                <td><strong>${totalPrice}</strong></td>
            </tr>

            <c:if test="${sessionScope.user != null}">
                <td>
                    <form method="post" action="orders/order/create">
                        <input type="submit" value="<spring:message code="cart.create_order"/>">
                    </form>
                </td>
            </c:if>
            <c:if test="${sessionScope.user == null}">
                <td>
                    <form method="post" action="login">
                        <input type="submit" value="<spring:message code="cart.create_order"/>">
                    </form>
                </td>
            </c:if>


        </table>
    </c:otherwise>
</c:choose>
</body>
</html>
