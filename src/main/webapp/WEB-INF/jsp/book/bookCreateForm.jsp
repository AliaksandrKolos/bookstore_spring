<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<head>
    <title><spring:message code="bookCreateForm.title"/></title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">

</head>
<body>
<jsp:include page="../navbar.jsp"/>

<h1><spring:message code="bookCreateForm.header"/></h1>
<form:form action="/books/create" method="post" modelAttribute="bookDto">

    <label for="title"><spring:message code='bookCreateForm.title'/></label>
    <form:input path="title" type="text" id="title" />

    <label for="author"><spring:message code='bookCreateForm.author'/></label>
    <form:input path="author" type="text" id="author" />

    <label for="isbn"><spring:message code='bookCreateForm.isbn'/></label>
    <form:errors path="isbn" cssStyle="color: red" />
    <form:input path="isbn" type="text" id="isbn" />

    <label for="genre"><spring:message code='bookCreateForm.genre'/></label>
    <form:input path="genre" type="text" id="genre" />

    <label for="year"><spring:message code='bookCreateForm.year'/></label>
    <form:input path="year" type="number" id="year" />

    <label for="pages"><spring:message code='bookCreateForm.pages'/></label>
    <form:input path="pages" type="number" id="pages" />

    <label for="price"><spring:message code='bookCreateForm.price'/></label>
    <form:input path="price" type="text" id="price" />

    <label for="cover"><spring:message code="bookCreateForm.select_cover"/></label>
    <select id="cover" name="cover">
        <option value="PAPERBACK"><spring:message code="bookCreateForm.type_cover_1"/></option>
        <option value="HARDCOVER"><spring:message code="bookCreateForm.type_cover_2"/></option>
        <option value="LEATHER"><spring:message code="bookCreateForm.type_cover_3"/></option>
        <option value="DUST_JACKET"><spring:message code="bookCreateForm.type_cover_4"/></option>
    </select>

    <button type="submit" class="btn"><spring:message code="bookCreateForm.submit_create_book"/></button>
</form:form>


</body>
</html>

