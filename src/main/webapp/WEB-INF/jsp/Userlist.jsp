<%--
  Created by IntelliJ IDEA.
  User: 32871
  Date: 2019/8/6
  Time: 14:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
    <style>
        * {
            font-family: "montserrat",sans-serif;
        }
        body {
            margin: 0;
            padding: 0;
            background: #333;
        }
        table {
            border: 1px solid white;
            border-collapse: collapse;
            text-align: center;
            color: white;
        }

        td {
            height: 50px;
            width: 100px;
            border: 1px solid #c3c3c3;

        }
        a {
            color: white;
            text-decoration: none;
        }
    </style>
</head>
<body>
欢迎，${user.username}登陆
<table>
    <thead>
    <th>用户ID</th>
    <th>用户昵称</th>
    <th>用户密码</th>
    <th>用户邮箱</th>
    <th>用户手机</th>
    <th>密保问题</th>
    <th>密保答案</th>
    <th>用户权限</th>
    <th>创建时间</th>
    <th>更新时间</th>
    <th>操作</th>
    </thead>
    <c:forEach items="${userlist}" var="user1">
        <tr>
            <td>${user1.id}</td>
            <td>${user1.username}</td>
            <td>${user1.password}</td>
            <td>${user1.email}</td>
            <td>${user1.phone}</td>
            <td>${user1.question}</td>
            <td>${user1.answer}</td>
            <td>${user1.role}</td>
            <td>${user1.createTime}</td>
            <td>${user1.updateTime}</td>
            <td><a href="update/${user1.id}">修改</a>/
                <a href="delete/${user1.id}">删除</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
