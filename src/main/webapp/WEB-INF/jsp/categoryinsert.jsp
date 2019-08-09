<%--
  Created by IntelliJ IDEA.
  User: 32871
  Date: 2019/8/7
  Time: 11:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>增加类别</title>
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
<form action="/user/category/insert" method="post">
    名称<input type="text" name="name"><br/>
    父类id
    <select name="parentId">
        <c:forEach items="${categorylist}" var="category">
            <option value="${category.id}">${category.name}</option>
        </c:forEach>
    </select><br/>
    类别状态:<input type="text" name="status"><br/>
    <input type="submit"  value="提交"><br/>
</form>
</body>
</html>
