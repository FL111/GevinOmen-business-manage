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
                    <form role="form" method="post" action="/business/user/product/insert" enctype="multipart/form-data">
                        <div class="form-group">
                            <label>名称</label>
                            <input name="name" type="text" class="form-control"}"/>
                        </div>
                        <div class="form-group">
                            <label>副标题</label>
                            <input name="subtitle" type="text" class="form-control"}"/>
                        </div>
                        <div class="form-group">
                            <label>价格</label>
                            <input name="price" type="text" class="form-control"}"/>
                        </div>
                        <div class="form-group">
                            <label>库存</label>
                            <input name="stock" type="number" class="form-control"}"/>
                        </div>
                        <div class="form-group">
                            <label>状态码</label>
                            <input name="status" type="number" class="form-control"}"/>
                        </div>
                        <div class="form-group">
                            <label>描述</label>
                            <input name="detail" type="text" class="form-control"}"/>
                        </div>
                        <div class="form-group">
                            <label>图片</label>
                            <div class="localImag">
                                <input id="input-id-1" type="file" name="picture" onchange="javascript:setImagePreview();" class="fil">
                                <img id="pic" src="" alt="图片在此显示" style="display:inline-block; height: 150px; width: 180px;" onclick="window.open(this.src)">
                                <p class="help-block">支持jpg、jpeg、png、gif格式，大小不超过1M</p>
                            </div>
                        </div>
                        <div class="form-group">
                            <label>附图</label>
                            <div class="">
                                <input id="input-id" type="file"  accept="image/*"  name="picture1" multiple>
                                <p class="help-block">支持图片格式，大小不超过1M</p>
                            </div>
                        </div>
                        <div class="form-group">
                            <label>类目</label>
                            <select name="categoryId" class="form-control">
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
<script type="text/javascript">
function setImagePreview(avalue) {
    var docObj=document.getElementById("input-id-1");
    var imgObjPreview=document.getElementById("pic");
    if(docObj.files &&docObj.files[0])
    {
        imgObjPreview.style.display = 'block';
        imgObjPreview.style.width = '150px';
        imgObjPreview.style.height = '180px';
        imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);
    }
    else
    {
        docObj.select();
        var imgSrc = document.selection.createRange().text;
        var localImagId = document.getElementById("localImag");
        localImagId.style.width = "150px";
        localImagId.style.height = "180px";
        try{
            localImagId.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
            localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
        }
        catch(e)
        {
            alert("您上传的图片格式不正确，请重新选择!");
            return false;
        }
        imgObjPreview.style.display = 'none';
        document.selection.empty();
    }
    return true;
}
</script>

</body>
</html>