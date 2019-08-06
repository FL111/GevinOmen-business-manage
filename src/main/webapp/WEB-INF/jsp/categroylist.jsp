<%--
  Created by IntelliJ IDEA.
  User: 32871
  Date: 2019/8/6
  Time: 15:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
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
    <c:forEach items="${categroylist}" var="categroy"></c:forEach>
    <tr>
        <td>${category.id}</td>
        <td>${category.name}</td>
        <td>${category.parentid}</td>
        <td>${category.status}</td>
        <td>${category.create_time}</td>
        <td>${category.update_time}</td>
        <td><a href="update/${category.id}">修改</a>/
            <<a href="">删除</a>
        </td>
    </tr>
</table>

</body>
</html>
