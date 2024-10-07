<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <title><spring:message code="index.title"/></title>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<h1><spring:message code="index.heading"/></h1>
<h1><spring:message code="index.welcome"/> ${sessionScope.user != null ? user.email : "Guest"} </h1>
<img src="/images/00.jpg" alt="library">

</body>
</html>
