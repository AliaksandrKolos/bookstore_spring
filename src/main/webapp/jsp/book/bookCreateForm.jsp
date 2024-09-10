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
<head>
    <title><fmt:message key="bookCreateForm.title"/></title>
    <link rel="stylesheet" type="text/css" href="..css/style.css">

</head>
<body>
<jsp:include page="../navbar.jsp"/>

<h1><fmt:message key="bookCreateForm.header"/></h1>
<form method="post" action="controller?command=bookCreateCommand">
    <input type="text" placeholder="<fmt:message key="bookCreateForm.title"/>" name="title">
    <input type="text" placeholder="<fmt:message key="bookCreateForm.author"/>" name="author">
    <input type="text" placeholder="<fmt:message key="bookCreateForm.isbn"/>" name="isbn">
    <input type="text" placeholder="<fmt:message key="bookCreateForm.genre"/>" name="genre">
    <input type="number" placeholder="<fmt:message key="bookCreateForm.year"/>" name="year">
    <input type="number" placeholder="<fmt:message key="bookCreateForm.pages"/>" name="pages">
    <input name="price" type="text" placeholder="<fmt:message key="bookCreateForm.price"/>" pattern="\d+(\.\d+)?" title="price" required>

    <label for="cover"><fmt:message key="bookCreateForm.select_cover"/></label>
    <select id="cover" name="cover">
        <option value="PAPERBACK"><fmt:message key="bookCreateForm.type_cover_1"/></option>
        <option value="HARDCOVER"><fmt:message key="bookCreateForm.type_cover_2"/></option>
        <option value="LEATHER"><fmt:message key="bookCreateForm.type_cover_3"/></option>
        <option value="DUST_JACKET"><fmt:message key="bookCreateForm.type_cover_4"/></option>
    </select>

    <button type="submit" class="btn"><fmt:message key="bookCreateForm.submit_create_book"/></button>
</form>

</body>
</html>
