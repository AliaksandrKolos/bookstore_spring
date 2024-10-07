<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <title><spring:message code="bookEditForm.title"/></title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">

</head>
<body>
<jsp:include page="../navbar.jsp"/>

<h1><spring:message code="bookEditForm.header"/></h1>
<form method="post" action="${pageContext.request.contextPath}/books/edit/${book.id}">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <label for="title"><spring:message code="bookEditForm.title"/></label>
    <input type="text" name="title" id="title" value="<c:out value="${book.title}"/>">

    <label for="author"><spring:message code="bookEditForm.author"/></label>
    <input type="text" name="author" id="author" value="<c:out value="${book.author}"/>">

    <label for="isbn"><spring:message code="bookEditForm.isbn"/></label>
    <input type="text" name="isbn" id="isbn" value="<c:out value="${book.isbn}"/>">

    <label for="genre"><spring:message code="bookEditForm.genre"/></label>
    <input type="text" name="genre" id="genre" value="<c:out value="${book.genre}"/>">

    <label for="year"><spring:message code="bookEditForm.year"/></label>
    <input type="number" name="year" id="year" value="<c:out value="${book.year}"/>">

    <label for="pages"><spring:message code="bookEditForm.pages"/></label>
    <input type="number" name="pages" id="pages" value="<c:out value="${book.pages}"/>">

    <label for="price"><spring:message code="bookEditForm.price"/></label>
    <input name="price" type="text" id="price" pattern="\d+(\.\d+)?" title="price" required value="<c:out value="${book.price}"/>">

    <select id="cover" name="cover">
        <option value="PAPERBACK"><spring:message code="bookEditForm.type_cover_1"/></option>
        <option value="HARDCOVER"><spring:message code="bookEditForm.type_cover_2"/></option>
        <option value="LEATHER"><spring:message code="bookEditForm.type_cover_3"/></option>
        <option value="DUST_JACKET"><spring:message code="bookEditForm.type_cover_4"/></option>
    </select>

    <button type="submit" class="btn"><spring:message code="bookEditForm.button_edit"/></button>
</form>




</body>
</html>

