<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title><spring:message code="userCreateForm.title"/></title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
<jsp:include page="../navbar.jsp"/>

<h1><spring:message code="userCreateForm.header"/></h1>

<form:form action="/users/create" method="post" modelAttribute="userDto">
    <label for="firstName"><spring:message code="userCreateForm.firstName"/></label>
    <form:input path="firstName" type="text" id="firstName" />

    <label for="lastName"><spring:message code="userCreateForm.lastName"/></label>
    <form:input path="lastName" type="text" id="lastName" />

    <label for="email"><spring:message code="userCreateForm.email"/></label>
    <form:errors path="email" cssClass="error-message"/>
    <form:input path="email" type="text" id="email" />

    <label for="password"><spring:message code="userCreateForm.password"/></label>
    <form:errors path="password" cssClass="error-message"/>
    <form:input path="password" type="password" id="password" />

    <select id="role" name="role">
        <option value="USER"><spring:message code="userCreateForm.user"/></option>
        <option value="MANAGER"><spring:message code="userCreateForm.manager"/></option>
        <option value="ADMIN"><spring:message code="userCreateForm.admin"/></option>
    </select>

    <button type="submit" class="btn"><spring:message code="userCreateForm.create_button"/></button>
</form:form>

</body>
</html>
