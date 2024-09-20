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
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <title><fmt:message key="navbar.title"/></title>
</head>
<body>
<header align="center">
    <ul>
        <c:choose>
            <c:when test="${sessionScope.user == null}">
                <li><a href="${pageContext.request.contextPath}/"><fmt:message key="navbar.home"/></a></li>
                <li><a href="${pageContext.request.contextPath}/users/registration"><fmt:message key="navbar.sign_up"/></a></li>
                <li><a href="${pageContext.request.contextPath}/login"><fmt:message key="navbar.sign_in"/></a></li>
            </c:when>
            <c:otherwise>
                <li><a href="${pageContext.request.contextPath}/"><fmt:message key="navbar.home"/></a></li>
                <li><a href="${pageContext.request.contextPath}/logOut"><fmt:message key="navbar.log_out"/></a></li>

                <c:if test="${sessionScope.user.role == 'ADMIN' || sessionScope.user.role == 'MANAGER'}">
                    <li><a href="${pageContext.request.contextPath}/users/getAll?page=1&page_size=5"><fmt:message key="navbar.users"/></a></li>
                    <li><a href="${pageContext.request.contextPath}/books/create"><fmt:message key="navbar.add_book"/></a></li>
                    <li><a href="${pageContext.request.contextPath}/orders/getAll?page=1&page_size=5"><fmt:message key="navbar.all_orders"/></a></li>
                </c:if>
                <li><a href="${pageContext.request.contextPath}/cart"><fmt:message key="navbar.cart"/></a></li>
                <li><a href="${pageContext.request.contextPath}/orders/orders_user/${sessionScope.user.id}"><fmt:message key="navbar.my_order"/></a></li>
            </c:otherwise>
        </c:choose>
        <li><a href="${pageContext.request.contextPath}/books/getAll?page=1&page_size=5"><fmt:message key="navbar.all_books"/></a></li>
        <li><a href="${pageContext.request.contextPath}/changeLanguage?lang=en">English</a></li>
        <li><a href="${pageContext.request.contextPath}/changeLanguage?lang=ru">Русский</a></li>
        <li><a href="${pageContext.request.contextPath}/changeLanguage?lang=de">Deutsch</a></li>
    </ul>
</header>
</body>
</html>
