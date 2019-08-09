<%--
  Created by IntelliJ IDEA.
  User: 32871
  Date: 2019/8/7
  Time: 10:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>商品列表</title>
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
        a {
            color: white;
            text-decoration: none;
        }
        td {
            height: 50px;
            width: 100px;
            border: 1px solid #c3c3c3;

        }
    </style>
</head>
<body>
<table>
    <thead>
    <th>商品ID</th>
    <th>商品类别</th>
    <th>商品名称</th>
    <th>副标题</th>
    <th>主图</th>
    <th>副图</th>
    <th>商品描述</th>
    <th>商品价格</th>
    <th>商品库存</th>
    <th>状态</th>
    <th>创建时间</th>
    <th>更新时间</th>
    <th>操作</th>
    </thead>
    <c:forEach items="${productlist}" var="product">
        <tr>
            <td>${product.id}</td>
            <td>${product.categoryId}</td>
            <td>${product.name}</td>
            <td>${product.subtitle}</td>
            <td><img src="/up111/${product.mainImage}" height="200px" width="200px"></td>
            <td><img src="/up111/${product.subImages}" height="200px" width="200px"></td>
            <td>${product.detail}</td>
            <td>${product.price}</td>
            <td>${product.stock}</td>
            <td>${product.status}</td>
            <td>${product.createTime?string('yyyy-MM-dd HH:mm:ss')}</td>
            <td>${product.updateTime?string('yyyy-MM-dd HH:mm:ss')}</td>
            <td><a href="update/${product.id}">修改</a>/
                <a href="delete/${product.id}">删除</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
