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
    <link rel="stylesheet" type="text/css" href="..css/style.css">
    <title><fmt:message key="user.title"/></title>
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<table>
    <th><fmt:message key="user.id"/></th>
    <th><fmt:message key="user.fist_name"/></th>
    <th><fmt:message key="user.last_name"/></th>
    <th><fmt:message key="user.email"/></th>
    <th><fmt:message key="user.tole"/></th>
    <tbody>
    <tr>
        <td><c:out value=""/>${user.id}</td>
        <td><c:out value="${user.firstName}"/></td>
        <td><c:out value="${user.lastName}"/></td>
        <td><c:out value="${user.email}"/></td>
        <td><c:out value="${user.role}"/></td>
    </tr>
    </tbody>
</table>
</body>
</html>

