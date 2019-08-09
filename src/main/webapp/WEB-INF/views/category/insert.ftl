
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
                    <form role="form" method="post" action="/user/category/insert">
                        <div class="form-group">
                            <label>名字</label>
                            <input name="name" type="text" class="form-control"/>
                        </div>
                        <div class="form-group">
                            <label>type</label>
                            <input name="status" type="number" class="form-control"/>
                        </div>
                        <div>
                            <label>父类id</label>
                            <select name="parentId">
                                <#list categorylist as category>
                                    <option value="${category.id}">${category.name}</option>
                                </#list>
                            </select>
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