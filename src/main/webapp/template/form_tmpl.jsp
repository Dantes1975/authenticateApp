
<form method="post" action="${requestScope.action}">
    <p>Login</p>
    <p><input type="text" name="login" placeholder="Please enter login"/></p>
    <p>Password</p>
    <p><input type="text" name="password" placeholder="Please enter password"/></p>
    <p><input type="submit" name="action" value="${requestScope.title}"/></p>
</form>