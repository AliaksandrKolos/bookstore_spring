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
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <title><fmt:message key="navbar.title"/></title>
</head>
<body>
<header align="center">
    <ul>
        <c:if test="${sessionScope.user == null}">
            <li><a href="controller?command=homeCommand"><fmt:message key="navbar.home"/></a></li>
            <li><a href="controller?command=UserRegistrationForm"><fmt:message key="navbar.sign_up"/></a></li>
            <li><a href="controller?command=loginFormCommand"><fmt:message key="navbar.sign_in"/></a></li>
            <li><a href="controller?command=booksCommand&page=${param.page != null ? param.page : 1}&page_size=${param.page_size != null ? param.page_size : 5}"><fmt:message key="navbar.all_books"/></a></li>
            <li><a href="controller?command=cartCommand"><fmt:message key="navbar.cart"/></a></li>
        </c:if>

        <c:if test="${sessionScope.user != null}">
            <li><a href="controller?command=homeCommand"><fmt:message key="navbar.home"/></a></li>
            <li><a href="controller?command=logOutCommand"><fmt:message key="navbar.log_out"/></a></li>
            <li><a href="controller?command=booksCommand&page=${param.page != null ? param.page : 1}&page_size=${param.page_size != null ? param.page_size : 5}"><fmt:message key="navbar.all_books"/></a></li>
            <li><a href="controller?command=usersCommand&page=${param.page != null ? param.page : 1}&page_size=${param.page_size != null ? param.page_size : 5}"><fmt:message key="navbar.users"/></a></li>


            <c:if test="${sessionScope.user.role.toString() == 'ADMIN'}">
                <li><a href="controller?command=bookCreateFormCommand"><fmt:message key="navbar.add_book"/></a></li>
            </c:if>
            <li><a href="controller?command=ordersCommand&page=${param.page != null ? param.page : 1}&page_size=${param.page_size != null ? param.page_size : 5}"><fmt:message key="navbar.all_orders"/></a></li>
            <li><a href="controller?command=cartCommand"><fmt:message key="navbar.cart"/></a></li>
            <li><a href="controller?command=ordersUserCommand&id=${sessionScope.user.id}"><fmt:message key="navbar.my_order"/></a></li>

        </c:if>
        <li><a href="controller?command=changeLanguageCommand&lang=en">English</a></li>
        <li><a href="controller?command=changeLanguageCommand&lang=ru">Русский</a></li>
        <li><a href="controller?command=changeLanguageCommand&lang=de">Deutsch</a></li>



    </ul>
</header>

</body>
</html>
