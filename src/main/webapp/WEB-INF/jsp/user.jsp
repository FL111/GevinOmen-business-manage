<%--
  Created by IntelliJ IDEA.
  User: 32871
  Date: 2019/8/3
  Time: 16:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户个人信息</title>
    <style>
        * {
            font-family: "montserrat",sans-serif;
        }
        body {
            margin: 0;
            padding: 0;
            background: #333;
        }
        .form-check {
            position: absolute;
            top: 14%;
            left: 39%;
            color: white;
        }
        span {
            display: block;
            text-align: center;
            width: 400px;
            height: 50px;
            font-size: 26px;
            padding-top: 12px;
        }
        input {
            width: 400px;
            height: 50px;
            font-size: 25px;
            border-radius: 6px;
            border: 1px solid white;
        }
        .btn {
            margin: 10px 20px 0px 0px;
            width: 190px;
            height: 50px;
            color: white;
            background-color: #FF8200;
        }
    </style>
</head>
<body>
    <div class="form-check">
        <form action="/update" method="post">
            <span>用户名</span>
            <input type="text" name="username" id="username"/>
            <span>密码</span>
            <input type="password" name="password" id="password"/>
            <span>密码问题</span>
            <input type="text" name="question" id="question"/>
            <span>密码问题答案</span>
            <input type="text" name="answer" id="answer"/>
            <span>邮箱</span>
            <input type="hidden" value="" name="userid" id="userid">
            <input type="text" name="email"  id="email"/><br>
            <input type="submit" value="更新" class="btn">
            <form action="/cancel" method="post"><input type="submit" value="取消" class="btn"></form>
        </form>
    </div>
</body>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript">

    function requestData() {
        $.ajax({
            url:"/getUser",
            type:"post",
            dataType:"json",
            success:function (data) {
                showData(data);
            },
            error:function (msg) {
                alert("ajax连接异常："+msg);
            }
        });
    };
    window.onload=requestData;
</script>
<script>
    function showData(data) {
        $("#userid").val(data[0].id);
        $("#username").val(data[0].username);
        $("#password").val(data[0].password);
        $("#question").val(data[0].question);
        $("#answer").val(data[0].answer);
        $("#email").val(data[0].email);
    }
</script>
</html>
