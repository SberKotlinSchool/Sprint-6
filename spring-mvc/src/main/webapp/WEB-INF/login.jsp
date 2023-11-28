<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
    <head>
        <title>Login page</title>
    </head>
    <body>
    <h1>Login page</h1>

    <c:if test="${param.loginError}">
        <div><span style="color: red">Wrong username or password</span></div><br/>
    </c:if>

    <form action="login" method="post">
        <label for="username">Username</label>:
        <input type="text" id="username" name="username" autofocus="autofocus"/><br/>
        <label for="password">Password</label>:
        <input type="password" id="password" name="password"/><br/>
        <input type="submit" value="Log in"/>
    </form>
    </body>
</html>
