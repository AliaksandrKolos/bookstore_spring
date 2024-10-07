<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <title><spring:message code="userRegistrationForm.title"/></title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<h1><spring:message code="userRegistrationForm.header"/></h1>
<form method="post" action="${pageContext.request.contextPath}/users/registration">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <input type="text" placeholder="<spring:message code='userRegistrationForm.email'/>" name="email" />
    <input type="password" placeholder="<spring:message code='userRegistrationForm.password'/>" name="password" minlength="1" required />
    <button type="submit" class="btn"><spring:message code="userRegistrationForm.registration_button"/></button>

    <c:if test="${not empty dataError}">
        <p><c:out value="${dataError}"/></p>
    </c:if>

</form>
</body>
</html>
