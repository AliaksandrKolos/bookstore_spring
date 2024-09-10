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
    <title><fmt:message key="userEditForm.title"/></title>
    <link rel="stylesheet" type="text/css" href="..css/style.css">

</head>
<body>
<jsp:include page="../navbar.jsp"/>

<h1><fmt:message key="userEditForm.header"/></h1>
<form method="post" action=controller?command=userEditCommand>

    <input name="id" type="hidden" value="${requestScope.user.id}">
    <input type="text" placeholder="<fmt:message key="userEditForm.firstName"/>" name="firstName" value="<c:out value="${user.firstName}"/>">
    <input type="text" placeholder="<fmt:message key="userEditForm.lastName"/>" name="lastName" value="<c:out value="${user.lastName}"/>">
    <input type="text" placeholder="<fmt:message key="userEditForm.email"/>" name="email" value="<c:out value="${user.email}"/>">
    <input type="text" placeholder="<fmt:message key="userEditForm.password"/>" name="password" value="<c:out value="${user.password}"/>">
    <input type="text" placeholder="<fmt:message key="userEditForm.role"/>" name="role" value="<c:out value="${user.role.toString()}"/>">
    <button type="submit" class="btn"><fmt:message key="userEditForm.edit_button"/></button>

</form>
</body>
</html>
