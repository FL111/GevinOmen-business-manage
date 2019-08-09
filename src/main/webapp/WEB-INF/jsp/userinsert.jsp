<%--
  Created by IntelliJ IDEA.
  User: 32871
  Date: 2019/8/7
  Time: 15:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>增加用户</title>
    <style>
        * {
            font-family: "montserrat",sans-serif;
        }
        body {
            margin: 0;
            padding: 0;
            background: #333;
        }

    </style>
</head>
<body>
<form action="/user/insert" method="post">
昵称<input type="text" name="username" ><br/>
密码<input type="text" name="password" ><br/>
邮箱<input type="text" name="email" ><br/>
密保问题<input type="text" name="question"><br/>
密保答案<input type="text" name="answer"><br/>
<input type="submit"  value="提交"><br/>
</form>
</body>
</html>
