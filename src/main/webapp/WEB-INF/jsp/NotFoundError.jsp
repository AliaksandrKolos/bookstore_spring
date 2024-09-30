<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<html>
<head>
    <title><spring:message code="error.404.title"/></title>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<h1><spring:message code="error.404.header"/></h1>
<p><spring:message code="error.404.message"/></p>
<c:if test="${not empty errorMessage}">
    <p><spring:message code="error.details"/><c:out value="${errorMessage}"/></p>
</c:if>
</body>
</html>
