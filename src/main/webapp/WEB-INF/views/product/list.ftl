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
                            <th>描述</th>
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
                            <td><img height="100" width="100" src="/up111/${productInfo.mainImage}" alt="" onclick="window.open(this.src)"></td>
                            <td><img height="100" width="100" src="/up111/${productInfo.subImages}" alt="" onclick="window.open(this.src)"></td>
                            <td>${productInfo.price}</td>
                            <td>${productInfo.stock}</td>
                            <td>${productInfo.detail}</td>
                            <td>${productInfo.categoryId}</td>
                            <td>${productInfo.createTime?string('yyyy-MM-dd HH:mm:ss')}</td>
                            <td>${productInfo.updateTime?string('yyyy-MM-dd HH:mm:ss')}</td>
                            <td>${productInfo.status}</td>
                            <td><a href="/user/product/update/${productInfo.id}">修改</a>
                                <a href="/user/product/delete/${productInfo.id}">删除</a>
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
                        <li><a href="/user/product/find/${currentPage-1}/${size}">上一页</a></li>
                    </#if>

                    <#list 1..conn as index>
                        <#if currentPage == index>
                            <li class="disabled"><a href="#">${index}</a></li>
                        <#else>
                            <li><a href="/user/product/find/${index}/${size}">${index}</a></li>
                        </#if>
                    </#list>

                    <#if currentPage gte conn>
                        <li class="disabled"><a href="#">下一页</a></li>
                    <#else>
                        <li><a href="/user/product/find/${currentPage + 1}/${size}">下一页</a></li>
                    </#if>
                    </ul>
                </div>
            </div>
        </div>
    </div>

</div>
</body>
</html>