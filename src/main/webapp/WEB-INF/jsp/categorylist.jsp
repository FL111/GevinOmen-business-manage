<%--
  Created by IntelliJ IDEA.
  User: 32871
  Date: 2019/8/6
  Time: 15:25
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
<table>
    <thead>
    <th>类别ID</th>
    <th>类别名称</th>
    <th>所属父类</th>
    <th>类别状态</th>
    <th>创建时间</th>
    <th>修改时间</th>
    <th>操作</th>
    </thead>
    <c:forEach items="${categorylist}" var="category">
        <tr>
            <td>${category.id}</td>
            <td>${category.name}</td>
            <td>${category.parentId}</td>
            <td>${category.status}</td>
            <td>${category.createTime}</td>
            <td>${category.updateTime}</td>
            <td><a href="update/${category.id}">修改</a>/
                <a href="delete/${category.id}">删除</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
