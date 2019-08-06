<%--
  Created by IntelliJ IDEA.
  User: 32871
  Date: 2019/8/3
  Time: 10:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>后台管理</title>
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
            border: 1px solid red;
            border-collapse: collapse;
            text-align: center;
        }

        td {
            height: 50px;
            width: 100px;
            border: 1px solid #c3c3c3;

        }
        input {
            width: 100px;
            height: 50px;
            margin: 0;
            padding: 0;
        }
        .btn {
            background-image:  linear-gradient(LightSteelBlue,SlateGray,LightSteelBlue);
            border: 0;
            cursor: pointer;
            color: #c3c3c3;
        }
        caption {
            font-weight: 700;
            font-size: 30px;
            margin-top: 0;
        }
        .inf {
            color: white;
        }
        .form-login {
            position: absolute;
            top: 20%;
            left: 30%;
        }
        form {
            padding: 0px;
            margin: 0px;
        }
    </style>
</head>
<body>
<div class="form-login">
    <table cellpadding="0" cellpadding="0" class="inf">
        <caption>欢迎，${userinf.username}登陆</caption>

        <tr>
            <td>email</td>
            <td>username</td>
            <td>password</td>
            <td>question</td>
            <td>answer</td>
            <td>修改</td>
            <td>删除</td>
        </tr>

    </table>
</div>

</body>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript">

    function requestData() {
        $.ajax({
            url:"/queryAllUser",
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
        var src="";
        for(var i=0;i<data.length;i++){
            src='<tr><td name="email">' + data[i].email+'</td><td>'
                + data[i].username + '</td><td>'
                + data[i].password +'</td><td>'
                + data[i].question + '</td><td>'
                + data[i].answer + "</td><td>"
                +'<form action="http://localhost:8081/turn" method="post">' +
                '<input type="submit" value="修改" class="btn update">' +
                '<input type="hidden" value="'+data[i].id+'" name="userid">' +
                '<input type="hidden" value="'+data[i].email+'" name="email">' +
                '<input type="hidden" value="'+data[i].username+'" name="username">' +
                '<input type="hidden" value="'+data[i].password+'" name="password">' +
                '<input type="hidden" value="'+data[i].question+'" name="question">' +
                '<input type="hidden" value="'+data[i].answer+'" name="answer"></form></td><td>'
                +'<form action="http://localhost:8081/delete" method="post"><input type="hidden" value="'+data[i].id+'" name="userid"><input type="submit" value="删除" class="btn delete" data='+data[i].id+'></form></td></tr>';
            $(".inf").append(src);
        }
    }
</script>
</html>
