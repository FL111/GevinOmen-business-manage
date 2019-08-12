<html>
<#include  "common/header.ftl">

<body>
<div id="wrapper" class="toggled">

    <#--边栏sidebar-->
    <#include "common/nav.ftl">

    <#--主要内容content-->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <table class="table table-bordered table-condensed">
                        <thead>
                        <tr>
                            <th>用户id</th>
                            <th>昵称</th>
                            <th>密码</th>
                            <th>邮箱</th>
                            <th>手机</th>
                            <th>密保问题</th>
                            <th>密保答案</th>
                            <th>权限</th>
                            <th>创建时间</th>
                            <th>修改时间</th>
                            <th>ip</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>

                        <#list userlist as user>
                        <tr>
                            <td>${user.id}</td>
                            <td>${user.username}</td>
                            <td>${user.password}</td>
                            <td>${user.email}</td>
                            <td>${user.phone}</td>
                            <td>${user.question}</td>
                            <td>${user.answer}</td>
                            <td>${user.role}</td>
                            <td>${user.createTime?string('yyyy-MM-dd HH:mm:ss')}</td>
                            <td>${user.updateTime?string('yyyy-MM-dd HH:mm:ss')}</td>
                            <td>${user.ip}</td>
                            <td><a href="update/${user.id}">修改</a>/
                                <a href="delete/${user.id}">删除</a></td>
                        </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>
                <div class="col-md-12 column">
                    <ul class="pagination pull-right">
                    <#if currentPage lte 1>
                        <li class="disabled"><a href="#">上一页</a></li>
                    <#else>
                        <li><a href="/user/find/${currentPage-1}/${size}">上一页</a></li>
                    </#if>

                    <#list 1..conn as index>
                        <#if currentPage == index>
                            <li class="disabled"><a href="#">${index}</a></li>
                        <#else>
                            <li><a href="/user/find/${index}/${size}">${index}</a></li>
                        </#if>
                    </#list>

                    <#if currentPage gte conn>
                        <li class="disabled"><a href="#">下一页</a></li>
                    <#else>
                        <li><a href="/user/find/${currentPage + 1}/${size}">下一页</a></li>
                    </#if>
                    </ul>
                </div>
            </div>
        </div>
    </div>

</div>
</body>
</html>