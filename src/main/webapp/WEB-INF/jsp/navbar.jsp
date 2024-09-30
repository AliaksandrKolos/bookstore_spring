<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>



<html>
<head>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <title><spring:message code="navbar.title"/></title>
</head>
<body>
<header align="center">
    <ul>
        <c:choose>
            <c:when test="${sessionScope.user == null}">
                <li><a href="${pageContext.request.contextPath}/"><spring:message code="navbar.home"/></a></li>
                <li><a href="${pageContext.request.contextPath}/users/registration"><spring:message code="navbar.sign_up"/></a></li>
                <li><a href="${pageContext.request.contextPath}/login"><spring:message code="navbar.sign_in"/></a></li>
            </c:when>
            <c:otherwise>
                <li><a href="${pageContext.request.contextPath}/"><spring:message code="navbar.home"/></a></li>
                <li><a href="${pageContext.request.contextPath}/logOut"><spring:message code="navbar.log_out"/></a></li>

                <c:if test="${sessionScope.user.role == 'ADMIN' || sessionScope.user.role == 'MANAGER'}">
                    <li><a href="${pageContext.request.contextPath}/users/getAll?page=0&size=5"><spring:message code="navbar.users"/></a></li>
                    <li><a href="${pageContext.request.contextPath}/books/create"><spring:message code="navbar.add_book"/></a></li>
                    <li><a href="${pageContext.request.contextPath}/orders/getAll?page=0&size=5"><spring:message code="navbar.all_orders"/></a></li>
                </c:if>
                <li><a href="${pageContext.request.contextPath}/cart"><spring:message code="navbar.cart"/></a></li>
                <li><a href="${pageContext.request.contextPath}/orders/orders_user/${sessionScope.user.id}?page=0&size=5"><spring:message code="navbar.my_order"/></a></li>
            </c:otherwise>
        </c:choose>
        <li><a href="${pageContext.request.contextPath}/books/getAll?page=0&size=5"><spring:message code="navbar.all_books"/></a></li>
        <form id="languageForm" method="get" action="${pageContext.request.contextPath}/changeLanguage">
            <select name="lang" onchange="document.getElementById('languageForm').submit()">
                <option value="en" ${sessionScope.lang == 'en' ? 'selected' : ''}>English</option>
                <option value="ru" ${sessionScope.lang == 'ru' ? 'selected' : ''}>Русский</option>
                <option value="de" ${sessionScope.lang == 'de' ? 'selected' : ''}>Deutsch</option>
            </select>
        </form>


    </ul>
</header>
</body>
</html>
