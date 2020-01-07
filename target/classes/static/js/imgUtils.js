/**
 * 0 主图   店铺logo/商品主图/卡券主图
 * 1-4 店铺介绍图
 * 5-8 商品banner
 * 9-12 商品介绍图
 *
 * @type {number}
 */

var imgMark = 0;//logo图片/主图
//返回点击图片事件
function imgOnclick(obj) {
    imgMark = $(obj).attr("data-mark");
    return $("#file").click();
}

//上传图片
function uploadFile(obj) {
    //上传完得到id 或者路径 标志上传成功
    var data = new FormData($('#fileForm')[0]);
    $.ajax({
        url: "image/uploadFile",
        type: 'POST',
        data: data,
        dataType: 'JSON',
        cache: false,
        processData: false,
        contentType: false
    }).done(function (ret) {
        if(ret.code == 12){
            setPageImg(ret.imgWebUrl,ret.imgfdfsUrl,imgMark);
            imgMark = 0;
        }else{
            return myAlertSimple(ret.msg);
        }
    });
    //重置上传控件
    $("#fileForm").html("<input style=\"border: none\" type=\"file\" class=\"form-control\" name=\"file\" id='file' multiple=\"multiple\"  onchange=\"uploadFile(this)\">");
}


//图片上传/删除完成,处理页面显示
function setPageImg(imgWebPath,fdfsImgPath,imgMark) {
    var html = "";
    if (imgMark == 0) {
        html = "<div class='deleteImg0' onclick='deleteImg(this)' delete-mark='0'><img src='./img/delete.png' width='25px' height='25px'></div>"+
            "<img style='border: 1px solid #ccc' src='"+imgWebPath+"' width='80px' height='80px'  onmousemove=\"changeImgFileColor(this,0)\" onmouseout=\"changeImgFileColor(this,1)\">";
        $("#imgShowDiv").html(html);//显示图片
        $("#img").val(fdfsImgPath);//设置图片fdfs值
    }else{
        html = "<input type='hidden' value='' id='img"+imgMark+"' name='img"+imgMark+"'>"+
            "<div class='deleteImg' onclick='deleteImg(this)' delete-mark='"+imgMark+"'><img src='./img/delete1.png' width='25px' height='25px'></div>"+
            "<img src='"+imgWebPath+"' data-mark='"+imgMark+"' onclick='imgOnclick(this)'  onmousemove=\"changeImgFileColor(this,0)\" onmouseout=\"changeImgFileColor(this,1)\">";
        $("#imgDiv"+imgMark).html(html);//显示图片
        $("#img"+imgMark).val(fdfsImgPath);//设置图片fdfs值
    }
}

//删除图片
function deleteImg(obj) {
    var deleteMark = $(obj).attr("delete-mark");
    if(deleteMark == 0){
        setPageImg('./img/null.jpg','',deleteMark);
    }else{
        setPageImg('./img/upload.png','',deleteMark)
    }
}

//鼠标经过type=0和离开type=1上传图片框的时间#E8E8E8
function changeImgFileColor(obj,type) {
    //type 0进  1出
    if (type == 0) {//经过
//		$("#img_id"+id).css("background-color", "red");
//		$("#img_id"+id).css("background-color", "red");#696969
        $(obj).css("border", "2px solid blue");
    } else {
        $(obj).css("border", "2px solid #ccc");
    }
}


/**
 * sku图片处理
 * @param obj
 */
//删除sku图片
function deleteSkuImg(obj) {
    var deleteMark = $(obj).attr("delete-mark");
    $("#fdfsImg"+deleteMark).val("");//设置图片fdfs路径
    $("#webImg"+deleteMark).attr('src',"./img/upload.png");;//设置图片访问地址
    $("#skuDeleteImg"+deleteMark).hide();
}

var imgSkuMark = 0;//点击要上传的sku图片
//返回点击图片事件
function skuImgOnclick(obj) {
    imgSkuMark = $(obj).attr("data-mark");
    return $("#fileSkuForm").children().click();
}

//上传图片
function uploadFileSku(obj) {
    //上传完得到id 或者路径 标志上传成功
    var data = new FormData($('#fileSkuForm')[0]);
    $.ajax({
        url: "image/uploadFile",
        type: 'POST',
        data: data,
        dataType: 'JSON',
        cache: false,
        processData: false,
        contentType: false
    }).done(function (ret) {
        if(ret.code == 12){
            setPageSkuImg(ret.imgWebUrl,ret.imgfdfsUrl,imgSkuMark);
            imgSkuMark = 0;
        }else{
            return myAlertSimple(ret.msg);
        }
    });
    //重置上传控件
    $("#fileSkuForm").html("<input style=\"border: none\" type=\"file\" class=\"form-control\" name=\"file\" multiple=\"multiple\"  onchange=\"uploadFileSku(this)\">");
}

//设置sku图片
function setPageSkuImg(imgWebUrl,imgFdfsUrl,imgSkuMark) {
    $("#fdfsImg"+imgSkuMark).val(imgFdfsUrl);//设置图片fdfs路径
    $("#webImg"+imgSkuMark).attr('src',imgWebUrl);;//设置图片访问地址
    $("#skuDeleteImg"+imgSkuMark).show();
}
