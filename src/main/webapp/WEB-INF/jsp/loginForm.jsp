<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <title><spring:message code="login_form.title"/></title>
    <link rel="stylesheet" type="text/css" href="css/style.css">

</head>
<body>
<jsp:include page="navbar.jsp"/>

<h1><spring:message code="login_form.header"/></h1>
<form method="post" action=login>
    <input type="text" placeholder="<spring:message code="login_form.email"/>" name="email">
    <input type="password" placeholder="<spring:message code="login_form.password"/>" name="password">
    <button type="submit" class="btn"><spring:message code="login_form.login"/></button>

    <c:if test="${not empty dataError}">
        <p><c:out value="${dataError}"/></p>
    </c:if>



</form>
</body>
</html>