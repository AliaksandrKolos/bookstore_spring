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
<html>
<head>
    <link rel="stylesheet" type="text/css" href="../css/style.css">
    <title><fmt:message key="cart.title"/></title>
</head>
<body>
<jsp:include page="navbar.jsp"/>

<c:choose>
    <c:when test="${sessionScope.cart == null || sessionScope.cart.isEmpty()}">
        <h1><fmt:message key="cart.cart_empty"/></h1>
    </c:when>
    <c:otherwise>
        <h1><fmt:message key="cart.you_cart"/></h1>

        <c:set var="totalPrice" value="0"/>

        <table>
            <tr>
                <th><fmt:message key="cart.title_book"/></th>
                <th><fmt:message key="cart.count"/></th>
                <th><fmt:message key="cart.price"/></th>
                <th><fmt:message key="cart.result"/></th>
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
                <td><strong><fmt:message key="cart.total_cost"/></strong></td>
                <td><strong>${totalPrice}</strong></td>
            </tr>

            <c:if test="${sessionScope.user != null}">
                <td>
                    <form method="post" action="controller?command=orderCreateCommand">
                        <input type="submit" value="<fmt:message key="cart.create_order"/>">
                    </form>
                </td>
            </c:if>
            <c:if test="${sessionScope.user == null}">
                <td>
                    <form method="post" action="controller?command=loginFormCommand">
                        <input type="submit" value="<fmt:message key="cart.create_order"/>">
                    </form>
                </td>
            </c:if>


        </table>
    </c:otherwise>
</c:choose>
</body>
</html>
