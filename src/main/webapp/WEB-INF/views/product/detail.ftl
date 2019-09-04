<html>
<#include "common/header.ftl">

<body>
<div id="wrapper" class="toggled">

    <#--边栏sidebar-->
    <#include "common/nav.ftl">


    <#--主要内容content-->
    <div id="page-content-wrapper">
        <div class="container">
            <div class="row clearfix">
                <div class="col-md-4 column">
                    <table class="table table-bordered" style=" position: absolute; margin-left: 122%;">
                    <img src="http://img.cdn.imbession.top/${product.mainImage}" style="float: right;">
                    <thead>
                    <tr>
                        <th>商品ID</th>
                        <th>名称</th>
                        <th>单价</th>
                        <th>库存</th>
                        <th>状态</th>
                        <th>创建时间</th>
                        <th>更新时间</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>${product.id}</td>
                        <td>${product.name}</td>
                        <td>${product.price}</td>
                        <td>${product.stock}</td>
                        <#if product.status == 1>
                            <td>在售</td>
                        <#else>
                            <td>已下架</td>
                        </#if>

                        <td>${product.createTime?string('yyyy-MM-dd HH:mm:ss')}</td>
                        <td>${product.updateTime?string('yyyy-MM-dd HH:mm:ss')}</td>
                    </tr>
                    </tbody>
                    </table>
                </div>

                <#--订单详情表数据-->
                <span>
                    ${product.detail}
                </span>

                <#--操作-->
                <#--<div class="col-md-12 column">-->
                <#--<#if orderDTO.getOrderStatusEnum().message == "新订单">-->
                <#--<a href="/sell/seller/order/finish?orderId=${orderDTO.orderId}" type="button" class="btn btn-default btn-primary">完结订单</a>-->
                <#--<a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}" type="button" class="btn btn-default btn-danger">取消订单</a>-->
                <#--</#if>-->
                <#--</div>-->
        </div>
    </div>
</div>
</div>

</body>
</html>