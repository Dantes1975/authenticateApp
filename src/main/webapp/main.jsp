<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="by.it_academy.util.ApplicationConstant" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>
        ${requestScope.title}
    </title>
</head>
<body>

<form action="<%= ApplicationConstant.LOGOUT_KEY %>" method="post" >
    <input type="submit" name="action" value="<%= ApplicationConstant.LOGOUT_PAGE_TITLE %>">
</form>

<p>User login = ${sessionScope.user.login}</p>
<p>User password = ${sessionScope.user.password}</p>
<p>User role =  ${sessionScope.user.role}</p>

<c:if test="${sessionScope.user.role == 'ADMIN'}">

    <form action="<%=ApplicationConstant.ADD_KEY%>" method="post">
        login <input type="text" name="login"/>
        <br>
        password <input type="password" name="password"/>
        <br>
        role <select name="role">
        <option>ADMIN</option>
        <option>USER</option>
    </select>
        <br>
        <input type="submit" name="action" value="<%=ApplicationConstant.ADD_PAGE_TITLE%>">
    </form>
    <br>
    <br>
    <c:forEach items="${sessionScope.users}" var="user">
        <p> User: ${user.login} Password ${user.password} Role ${user.role}</p>
    </c:forEach>
    <form action="<%=ApplicationConstant.DELETE_KEY%>" method="post">
        Login <input type="text" name="login"/>
        Password <input type="text" name="password"/>
        <input type="submit" name="action" value="<%=ApplicationConstant.DELETE_PAGE_TITLE%>"/>
    </form>
</c:if>
</body>
</html>
