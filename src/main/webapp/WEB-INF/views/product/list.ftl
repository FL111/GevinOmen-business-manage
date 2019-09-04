<html>
<head>
    <meta charset="utf-8">
    <title>卖家后端管理系统</title>
    <link href="https://cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">
    <link href="/static/css/style.css" rel="stylesheet"  type="text/css">
    <link href="https://cdn.bootcss.com/bootstrap-fileinput/4.4.7/css/fileinput.min.css" media="all" rel="stylesheet" type="text/css" />
    <style>
        .container {
            width: 500px;
            height: 50px;
            margin: 100px auto;
        }

        .parent {
            width: 100%;
            height: 42px;
            top: 4px;
            position: relative;
        }

        .parent>input:first-of-type {
            /*输入框高度设置为40px, border占据2px，总高度为42px*/
            width: 380px;
            height: 40px;
            border: 1px solid #ccc;
            font-size: 16px;
            outline: none;
        }

        .parent>input:first-of-type:focus {
            border: 1px solid #317ef3;
            padding-left: 10px;
        }

        .parent>input:last-of-type {
            /*button按钮border并不占据外围大小，设置高度42px*/
            width: 100px;
            height: 40px;
            position: absolute;
            background: #317ef3;
            border: 1px solid #317ef3;
            color: #fff;
            font-size: 16px;
            outline: none;
        }
    </style>
</head>

<body>
<div id="wrapper" class="toggled">

    <#--边栏sidebar-->
    <#include "common/nav.ftl"/>
        <div class="container">
            <form action="/user/product/search" class="parent" method="post">
                <input type="text" name="keyword" value="${keyword}" placeholder="请输入要查询的关键字">
                <input type="hidden" name="pageNum" value="${currentPage}" >
                <input type="hidden" name="pageSize" value="${size}" >
                <input type="submit" value="搜索">
            </form>
        </div>

    <#--主要内容content-->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <table class="table table-bordered table-condensed">
                        <thead>
                        <tr>
                            <th>商品id</th>
                            <th>名称</th>
                            <th>副标题</th>
                            <th>图片</th>
                            <th>附图</th>
                            <th>单价</th>
                            <th>库存</th>
                            <th>类目</th>
                            <th>创建时间</th>
                            <th>修改时间</th>
                            <th>状态码</th>
                            <th colspan="2">操作</th>  <#--colspan="2"-->
                        </tr>
                        </thead>
                        <tbody>

                        <#list productlist as productInfo>
                        <tr>
                            <td>${productInfo.id}</td>
                            <td>${productInfo.name}</td>
                            <td>${productInfo.subtitle}</td>
                            <td><img height="100" width="100" src="http://img.cdn.imbession.top/${productInfo.mainImage}" alt="" onclick="window.open(this.src)" style="cursor: pointer"></td>
                            <td><img height="100" width="100" src="http://img.cdn.imbession.top/${productInfo.subImages}" alt="" onclick="window.open(this.src)" style="cursor: pointer"></td>
                            <td>${productInfo.price}</td>
                            <td>${productInfo.stock}</td>
                            <td>${productInfo.categoryId}</td>
                            <td>${productInfo.createTime?string('yyyy-MM-dd HH:mm:ss')}</td>
                            <td>${productInfo.updateTime?string('yyyy-MM-dd HH:mm:ss')}</td>
                            <td>${productInfo.status}</td>
                            <td><a href="/user/product/update/${productInfo.id}">修改</a>
                                <a href="/user/product/delete/${productInfo.id}">删除</a>
                                <a href="/user/product/detail/${productInfo.id}">详情</a>
                            </td>
                            <td>
                                <#if productInfo.status == "1">
                                    <a href="/user/product/status/${productInfo.id}/0">下架</a>
                                <#else>
                                    <a href="/user/product/status/${productInfo.id}/1">上架</a>
                                </#if>
                            </td>
                        </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>

            <#--分页-->
                <div class="col-md-12 column">
                    <ul class="pagination pull-right">
                    <#if currentPage lte 1>
                        <li class="disabled"><a href="#">上一页</a></li>
                    <#else>
                        <li><a href="${url}/${currentPage-1}/${size}">上一页</a></li>
                    </#if>

                    <#-- 以下为带省略号分页 -->
                    <#--第一页-->
                    <#if (conn > 0)>
                        <li <#if currentPage == 1>class="disabled"</#if>><a href="${url}/1/${size}" >1</a></li>
                    </#if>

                    <#--如果不只有一页-->
                    <#if (conn > 1)>
                    <#--如果当前页往前查3页不是第2页-->
                        <#if ((currentPage - 3) > 2)>
                            <li><span class="text">…</span></li>
                        </#if>

                    <#--当前页的前3页和后3页-->
                        <#list (currentPage - 3)..(currentPage + 3) as index>
                        <#--如果位于第一页和最后一页之间-->
                            <#if (index > 1) && (index < conn)>
                                <li <#if currentPage == index>class="disabled"</#if>><a href="${url}/${index}/${size}" >${index}</a></li>
                            </#if>
                        </#list>

                    <#--如果当前页往后查3页不是倒数第2页-->
                        <#if (currentPage + 3) < (conn - 1)>
                            <li><span class="text">…</span></li>
                        </#if>

                    <#--最后页-->
                        <li <#if currentPage == conn>class="disabled"</#if>><a href="${url}/${conn}/${size}" >${conn}</a></li>
                    </#if>


                    <#if currentPage gte conn>
                        <li class="disabled"><a href="#">下一页</a></li>
                    <#else>
                        <li><a href="${url}/${currentPage + 1}/${size}">下一页</a></li>
                    </#if>
                    </ul>
                </div>

            </div>
        </div>
    </div>

</div>
</body>
</html>