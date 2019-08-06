<%--
  Created by IntelliJ IDEA.
  User: 32871
  Date: 2019/7/27
  Time: 9:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注册</title>
</head>
<body>
<form action="http://localhost:8081/register" method="post">
    <span>用户名:</span>
    <br/>
    <input type="text" name="username"/>
    <br/>
    <span>密码:</span>
    <br/>
    <input type="password" name="password"/>
    <br/>
    <span>密码问题:</span>
    <br/>
    <input type="text" name="question"/>
    <br/>
    <span>密码问题答案:</span>
    <br/>
    <input type="text" name="answer"/>
    <br/>
    <span>邮箱:</span>
    <br/>
    <input type="text" name="email"/>
    <br/>
    <input type="submit" value="注册">
</form>
</body>
</html>
