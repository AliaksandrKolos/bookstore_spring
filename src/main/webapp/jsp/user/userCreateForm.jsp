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
    <title><fmt:message key="userCreateForm.title"/></title>
    <link rel="stylesheet" type="text/css" href="..css/style.css">

</head>
<body>
<jsp:include page="../navbar.jsp"/>

<h1><fmt:message key="userCreateForm.header"/></h1>
<form method="post" action=controller?command=user_create>
    <input type="text" placeholder="<fmt:message key="userCreateForm.firstName"/>" name="firstName">
    <input type="text" placeholder="<fmt:message key="userCreateForm.lastName"/>" name="lastName">
    <input type="text" placeholder="<fmt:message key="userCreateForm.email"/>" name="email">
    <input type="text" placeholder="<fmt:message key="userCreateForm.password"/>" name="password">
    <select id="role" name="role">
        <option value="USER"><fmt:message key="userCreateForm.user"/></option>
        <option value="MANAGER"><fmt:message key="userCreateForm.manager"/></option>
        <option value="ADMIN"><fmt:message key="userCreateForm.admin"/></option>
    </select>
    <button type="submit" class="btn"><fmt:message key="userCreateForm.create_button"/></button>

</form>
</body>
</html>
