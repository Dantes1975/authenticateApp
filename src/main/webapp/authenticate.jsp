<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>
        ${requestScope.title}
    </title>
</head>
<body>

<a href="<c:url value="${requestScope.ahref}" /> ">
    ${requestScope.ahref}
</a>

<jsp:include page="template/error_tmpl.jsp" />

<h2>${requestScope.title} form </h2>
<jsp:include page="template/form_tmpl.jsp">
    <jsp:param name="title" value="${requestScope.title}" />
    <jsp:param name="action" value="${requestScope.action}" />
</jsp:include>


</body>
</html>
