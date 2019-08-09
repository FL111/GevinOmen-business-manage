<%--
  Created by IntelliJ IDEA.
  User: 32871
  Date: 2019/8/7
  Time: 11:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>增加商品</title>
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
<form action="/user/product/insert" method="post" enctype="multipart/form-data">

    商品类别<select name="categoryId">
    <c:forEach items="${categorylist}" var="category">
        <option value="${category.id}">${category.name}</option>
    </c:forEach>
</select>
    商品名称<input type="text" name="name" ><br/>
    副标题:<input type="text" name="subtitle" ><br/>
    主图:<input type="file" name="picture"><br/>
    附图:<input type="file" name="picture1" ><br/>
    商品状态:<input type="text" name="status" ><br/>
    商品详情:<input type="text" name="detail" ><br/>
    商品价格:<input type="text" name="price" ><br/>
    商品库存:<input type="text" name="stock"><br/>
    <input type="submit"  value="修改"><br/>

</form>
</body>
</html>
