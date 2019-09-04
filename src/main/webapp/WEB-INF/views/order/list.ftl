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
                            <th>订单编号</th>
                            <th>用户昵称</th>
                            <th>手机号</th>
                            <th>地址</th>
                            <th>金额</th>
                            <th>订单状态</th>
                            <th>支付方式</th>
                            <th>运费</th>
                            <th>创建时间</th>
                            <th colspan="2">操作</th>
                        </tr>
                        </thead>
                        <tbody>

                        <#list orderList as orderDTO>
                        <tr>
                            <td>${orderDTO.orderNo}</td>
                            <td>${orderDTO.receiverName}</td>
                            <td>${orderDTO.phone}</td>
                            <td>${orderDTO.statusDesc}</td>
                            <td>${orderDTO.payment}</td>
                            <#if orderDTO.status==0>
                                <td>已取消</td>
                            </#if>
                            <#if orderDTO.status==10>
                                <td>未付款</td>
                            </#if>
                            <#if orderDTO.status==20>
                                <td>已付款</td>
                            </#if>
                            <#if orderDTO.status==40>
                                <td>已发货</td>
                            </#if>
                            <#if orderDTO.status==50>
                                <td>交易成功</td>
                            </#if>
                            <#if orderDTO.status==60>
                                <td>交易关闭</td>
                            </#if>
                            <#if orderDTO.paymentType == 1>
                                <td>支付宝支付</td>
                            </#if>
                            <td>${orderDTO.postage}</td>
                            <td>${orderDTO.createTime?string('yyyy-MM-dd HH:mm:ss')}</td>
                            <td><a href="/order/detail/${orderDTO.orderNo}">详情</a></td>
                            <td>
                                <#if orderDTO.status == 20>
                                    <a href="/order/send/${orderDTO.orderNo}">发货</a>
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
                        <li><a href="/order/list/${currentPage - 1}/${size}">上一页</a></li>
                    </#if>

                    <#list 1..conn as index>
                        <#if currentPage == index>
                            <li class="disabled"><a href="#">${index}</a></li>
                        <#else>
                            <li><a href="/order/find/${index}/${size}">${index}</a></li>
                        </#if>
                    </#list>

                    <#if currentPage gte conn>
                        <li class="disabled"><a href="#">下一页</a></li>
                    <#else>
                        <li><a href="/order/find/${currentPage + 1}/${size}">下一页</a></li>
                    </#if>
                    </ul>
                </div>
            </div>
        </div>
    </div>

</div>


<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

</body>
</html>