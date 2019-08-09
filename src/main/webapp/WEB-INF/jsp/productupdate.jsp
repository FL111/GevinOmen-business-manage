<%--
  Created by IntelliJ IDEA.
  User: 32871
  Date: 2019/8/7
  Time: 10:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>修改商品信息</title>
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

    <input type="hidden" name="id" value="${product.id}"><br/>
    商品类别<select name="categoryId">
    <option value="${product.categoryId}" selected="selected"></option>
    <c:forEach items="${categorylist}" var="category">
        <option value="${category.id}">${category.name}</option>
    </c:forEach>
</select><br/>
    商品名称<input type="text" name="name" value="${product.name}"><br/>
    副标题:<input type="text" name="subtitle" value="${product.subtitle}"><br/>
    主图:<input type="file" name="picture" value="${product.mainImage}"><br/>
    附图:<input type="file" name="picture1" value="${product.subImages}"><br/>
    商品详情:<input type="text" name="detail" value="${product.detail}"><br/>
    商品价格:<input type="text" name="price" value="${product.price}"><br/>
    商品库存:<input type="text" name="stock" value="${product.stock}"><br/>
    <input type="submit"  value="修改"><br/>

</form>
</body>
</html>
