<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <title><spring:message code="userEditForm.title"/></title>
    <link rel="stylesheet" type="text/css" href="../css/style.css">
</head>
<body>

<jsp:include page="../navbar.jsp"/>

<h1><spring:message code="userEditForm.header"/></h1>

<form method="post" action="${pageContext.request.contextPath}/users/edit/${user.id}">
    <input type="text" placeholder="<spring:message code='userEditForm.firstName'/>" name="firstName" value="${user.firstName}">
    <input type="text" placeholder="<spring:message code='userEditForm.lastName'/>" name="lastName" value="${user.lastName}">
    <input type="text" placeholder="<spring:message code='userEditForm.email'/>" name="email" value="${user.email}">
    <input type="password" placeholder="<spring:message code='userEditForm.password'/>" name="password" value="${user.password}">
    <input type="text" placeholder="<spring:message code='userEditForm.role'/>" name="role" value="${user.role}">

    <button type="submit" class="btn"><spring:message code="userEditForm.edit_button"/></button>

</form>
</body>
</html>
