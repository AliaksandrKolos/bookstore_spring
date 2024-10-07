<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <title><spring:message code="login_form.title"/></title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">

</head>
<body>


<h1><spring:message code="login_form.header"/></h1>
<jsp:include page="navbar.jsp"/>
<c:if test="${param.error != null}">
    <p style="color: red">Invalid data</p>
</c:if>
<c:if test="${param.logout != null}">
    <p style="color:#4a7610;">Successfully logged out</p>
</c:if>

<form method="post" action="${pageContext.request.contextPath}/login">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <input type="text" placeholder="<spring:message code="login_form.email"/>" name="username">
    <input type="password" placeholder="<spring:message code="login_form.password"/>" name="password">
    <button type="submit" class="btn"><spring:message code="login_form.login"/></button>
</form>
</body>
</html>