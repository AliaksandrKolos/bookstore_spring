<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <title><spring:message code="user.title"/></title>
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<table>
    <th><spring:message code="user.id"/></th>
    <th><spring:message code="user.fist_name"/></th>
    <th><spring:message code="user.last_name"/></th>
    <th><spring:message code="user.email"/></th>
    <th><spring:message code="user.tole"/></th>
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

