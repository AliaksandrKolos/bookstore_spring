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
    <title><fmt:message key="userRegistrationForm.title"/></title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<h1><fmt:message key="userRegistrationForm.header"/></h1>
<form method="post" action="${pageContext.request.contextPath}/users/registration">
    <input type="text" placeholder="<fmt:message key='userRegistrationForm.email'/>" name="email" />
    <input type="password" placeholder="<fmt:message key='userRegistrationForm.password'/>" name="password" minlength="1" required />
    <button type="submit" class="btn"><fmt:message key="userRegistrationForm.registration_button"/></button>

    <c:if test="${not empty dataError}">
        <p><c:out value="${dataError}"/></p>
    </c:if>
</form>
</body>
</html>
