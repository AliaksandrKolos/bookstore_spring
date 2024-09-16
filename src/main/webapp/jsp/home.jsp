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
    <fmt:setLocale value="ru"/>
</c:if>


<html>
<head>
    <title><fmt:message key="index.title"/></title>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<h1><fmt:message key="index.heading"/></h1>
<h1><fmt:message key="index.welcome"/> ${sessionScope.user != null ? user.email : "Guest"} </h1>
<img src="images/lib.png" alt="library">
</body>
</html>
