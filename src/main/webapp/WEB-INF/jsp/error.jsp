
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <jsp:include page="navbar.jsp"/>
    <title><spring:message code="error.title"/></title>
</head>
<body>
<h1><spring:message code="error.message"/></h1>
<div>${dataError != null ? dataError : "Somthing went wrong"}</div>
</body>
</html>
