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
    <title><fmt:message key="bookEditForm.title"/></title>
    <link rel="stylesheet" type="text/css" href="..css/style.css">

</head>
<body>
<jsp:include page="../navbar.jsp"/>

<h1><fmt:message key="bookEditForm.header"/></h1>
<form method="post" action=controller?command=book_edit>
    <input type="hidden"  name="id" value=""<c:out value="${book.id}"/>">
    <input type="text" placeholder="<fmt:message key="bookEditForm.title"/>" name="title" value="<c:out value="${book.title}"/>">
    <input type="text" placeholder="<fmt:message key="bookEditForm.author"/>" name="author" value="<c:out value="${book.author}"/>">
    <input type="text" placeholder="<fmt:message key="bookEditForm.isbn"/>" name="isbn" value="<c:out value="${book.isbn}"/>">
    <input type="text" placeholder="<fmt:message key="bookEditForm.genre"/>" name="genre" value="<c:out value="${book.genre}"/>">
    <input type="number" placeholder="<fmt:message key="bookEditForm.year"/>" name="year" value="<c:out value="${book.year}"/>">
    <input type="number"  placeholder="<fmt:message key="bookEditForm.pages"/>" name="pages" value="<c:out value="${book.pages}"/>">
    <input name="price" type="text" placeholder="<fmt:message key="bookEditForm.price"/>" pattern="\d+(\.\d+)?" title="price" required value="<c:out value="${book.price}"/>">
    <select id="cover" name="cover">
        <option value="PAPERBACK"><fmt:message key="bookEditForm.type_cover_1"/></option>
        <option value="HARDCOVER"><fmt:message key="bookEditForm.type_cover_2"/></option>
        <option value="LEATHER"><fmt:message key="bookEditForm.type_cover_3"/></option>
        <option value="DUST_JACKET"><fmt:message key="bookEditForm.type_cover_4"/></option>
    </select>
    <button type="submit" class="btn"><fmt:message key="bookEditForm.button_edit"/></button>

</form>
</body>
</html>

