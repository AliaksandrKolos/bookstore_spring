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
    <title><fmt:message key="error.404.title"/></title>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<h1><fmt:message key="error.404.header"/></h1>
<p><fmt:message key="error.404.message"/></p>
<c:if test="${not empty errorMessage}">
    <p><fmt:message key="error.details"/><c:out value="${errorMessage}"/></p>
</c:if>
</body>
</html>
