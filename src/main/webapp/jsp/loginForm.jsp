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
    <title><fmt:message key="login_form.title"/></title>
    <link rel="stylesheet" type="text/css" href="css/style.css">

</head>
<body>
<jsp:include page="navbar.jsp"/>

<h1><fmt:message key="login_form.header"/></h1>
<form method="post" action=controller?command=user_login>
    <input type="text" placeholder="<fmt:message key="login_form.email"/>" name="email">
    <input type="password" placeholder="<fmt:message key="login_form.password"/>" name="password">
    <button type="submit" class="btn"><fmt:message key="login_form.login"/></button>

    <c:if test="${not empty dataError}">
        <c:forEach items="${dataError}" var="error">
            <p><c:out value="${error}"/></p>
        </c:forEach>
    </c:if>


</form>
</body>
</html>