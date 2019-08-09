<%--
  Created by IntelliJ IDEA.
  User: 32871
  Date: 2019/8/7
  Time: 15:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>更改用户</title>
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
<form action="" method="post">

    <input type="hidden" name="id" value="${userInfo.id}"><br/>
    昵称<input type="text" name="username" value="${userInfo.username}"><br/>
    密码<input type="text" name="password" value="${userInfo.password}"><br/>
    邮箱<input type="text" name="email" value="${userInfo.email}"><br/>
    电话<input type="text" name="phone" value="${userInfo.phone}"><br/>
    权限<input type="text" name="role" value="${userInfo.role}"><br/>
    密保问题<input type="text" name="question" value="${userInfo.question}"><br/>
    密保答案<input type="text" name="answer" value="${userInfo.answer}"><br/>
    <input type="submit"  value="修改"><br/>

</form>
</body>
</html>
