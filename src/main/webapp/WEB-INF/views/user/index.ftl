<html>
<#include "common/header.ftl">

<body>
<div id="wrapper" class="toggled">

<#--边栏sidebar-->
<#include "common/nav.ftl">

<#--主要内容content-->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <form role="form" method="post" action="/business/user/update/${userInfo.id}">
                        <div class="form-group">
                            <label>昵称</label>
                            <input name="username" type="text" class="form-control" value="${(userInfo.username)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>密码</label>
                            <input name="password" type="text" class="form-control" value="${(userInfo.password)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>邮箱</label>
                            <input name="email" type="text" class="form-control" value="${(userInfo.email)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>电话</label>
                            <input name="phone" type="number" class="form-control" value="${(userInfo.phone)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>密保问题</label>
                            <input name="question" type="text" class="form-control" value="${(userInfo.question)!''}"/>
                        </div>

                        <div class="form-group">
                            <label>密保答案</label>
                            <input name="answer" type="text" class="form-control" value="${(userInfo.answer)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>权限</label>
                            <input name="role" type="hidden" class="form-control" value="${(userInfo.role)!''}" />
                        </div>
                        <button type="submit" class="btn btn-default">提交</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

</div>
</body>
</html>