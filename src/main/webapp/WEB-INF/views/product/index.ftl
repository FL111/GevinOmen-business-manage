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
                    <form role="form" method="post" action="/user/product/update">
                        <div class="form-group">
                            <label>名称</label>
                            <input name="name" type="text" class="form-control" value="${(productInfo.name)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>副标题</label>
                            <input name="subtitle" type="text" class="form-control" value="${(productInfo.subtitle)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>价格</label>
                            <input name="price" type="text" class="form-control" value="${(productInfo.price)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>库存</label>
                            <input name="stock" type="number" class="form-control" value="${(productInfo.stock)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>状态码</label>
                            <input name="status" type="number" class="form-control" value="${(productInfo.status)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>描述</label>
                            <input name="detail" type="text" class="form-control" value="${(productInfo.detail)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>图片  当前图片：</label>
                            <img src="/up111/${productInfo.mainImage}" style="width: 100px; height: 100px; display: inline-block">
                            <input id="input-id" type="file" name="picture" value="重新上传">
                            <p class="help-block">支持jpg、jpeg、png、gif格式，大小不超过1M</p>
                        </div>
                        <div class="form-group">
                            <label>附图  当前图片：</label>
                            <#list imglist as img>
                                <img src="/up111/${img}" style="width: 100px; height: 100px; display: inline-block">
                            </#list>
                            <input id="input-id" type="file" name="picture1" value="重新上传">
                            <p class="help-block">支持jpg、jpeg、png、gif格式，大小不超过1M</p>
                        </div>
                        <div class="form-group">
                            <label>类目</label>
                            <select name="categoryType" class="form-control">
                                <option value="${productInfo.categoryId}">${productInfo.categoryId}</option>
                                <#list categorylist as category>
                                    <option value="${category.id}">${category.name}</option>
                                </#list>
                            </select>
                        </div>
                        <input hidden type="text" name="productId" value="${(productInfo.productId)!''}">
                        <button type="submit" class="btn btn-default">提交</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap-fileinput/4.4.8/js/fileinput.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap-fileinput/4.4.8/js/locales/zh.min.js"></script>

</body>
</html>