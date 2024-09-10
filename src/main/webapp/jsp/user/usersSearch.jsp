<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
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
    <link rel="stylesheet" type="text/css" href="../css/style.css">
    <title><fmt:message key="users.title"/></title>
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<h1><fmt:message key="users.header"/></h1>

<c:if test="${requestScope.users.isEmpty()}">
    <h2><fmt:message key="users.no_users"/></h2>
</c:if>

<c:if test="${!requestScope.users.isEmpty()}">

    <form method="get" action="controller">
        <input type="text" placeholder="<fmt:message key='users.search'/>" name="lastName" value="${param.lastName}" />
        <input type="hidden" name="command" value="users_search_last_name"/>
        <input type="hidden" name="page" value="${param.page != null ? param.page : 1}" />
        <input type="hidden" name="page_size" value="${param.page_size != null ? param.page_size : 5}" />

        <button type="submit" class="btn"><fmt:message key="users.search"/></button>
    </form>



    <div class="pagination">
        <a href="controller?command=users_search_last_name&page=1&page_size=<c:out value='${param.page_size}'/>&lastName=<c:out value='${param.lastName}'/>">
            <fmt:message key="users.first"/>
        </a>
        <a href="controller?command=users_search_last_name&page=<c:out value='${page <= 1 ? 1 : page - 1}'/>&page_size=<c:out value='${param.page_size}'/>&lastName=<c:out value='${param.lastName}'/>">
            <fmt:message key="users.prev"/>
        </a>
        <span class="current-page"><c:out value="${page}"/></span>
        <a href="controller?command=users_search_last_name&page=<c:out value='${totalPages > page ? page + 1 : page}'/>&page_size=<c:out value='${param.page_size}'/>&lastName=<c:out value='${param.lastName}'/>">
            <fmt:message key="users.next"/>
        </a>
        <a href="controller?command=users_search_last_name&page=<c:out value='${totalPages}'/>&page_size=<c:out value='${param.page_size}'/>&lastName=<c:out value='${param.lastName}'/>">
            <fmt:message key="users.last"/>
        </a>
    </div>




    <table>
        <thead>
        <tr>
            <th><fmt:message key="users.id"/></th>
            <th><fmt:message key="users.fist_name"/></th>
            <th><fmt:message key="users.last_name"/></th>
            <th><fmt:message key="users.email"/></th>
            <th><fmt:message key="users.action"/></th>
            <th><fmt:message key="users.action"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${users}" var="user">
            <tr>
                <td><c:out value=""/>${user.id}</td>
                <td><c:out value="${user.firstName}"/></td>
                <td><c:out value="${user.lastName}"/></td>
                <td><a href="controller?command=user&id=<c:out value="${user.id}"/>"><c:out value=""/>${user.email}</a></td>
                <td><a href="controller?command=user_edit_form&id=<c:out value="${user.id}"/>"><fmt:message key="users.edit"/></a></td>
                <td>
                    <form method="post" action="controller?command=user_delete&id=<c:out value="${user.id}"/>">
                        <input type="submit" value="<fmt:message key="users.delete"/>">
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</c:if>
</body>
</html>