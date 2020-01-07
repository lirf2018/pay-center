//点击类目行(增加或者修改类目)
function addCategoryDiv(categoryId) {
    var data = "";
    var index = 0;
    if (categoryId > 0) {
        //修改
        $("#categoryId").val(categoryId);
        //查询类目ById
        $.ajax({
            url: "category/loadCategory",
            type: 'POST',
            data: {
                "categoryId": categoryId
            },// 要提交的表单 ,
            async: false,
            cache: false,
            dataType: "json",
            success: function (data) {
                var data = data.data;
                index = 1;
                addCategoryHtml(data, index);
            }
        });
    } else {
        //新增
        $("#categoryId").val("");
        addCategoryHtml(data, index);
    }
}

//返回类目增加列表
function addCategoryHtml(data, index) {
    var categoryId = $("#categoryId").val();
    var title = "新增类目";
    var categoryName = "";
    var categoryCode = "";
    var categoryImg = "";
    var categoryWebImg = "./img/null.jpg";
    var caOuteId = "";
    var caDataIndex = "";
    var caRemark = "";
    if (index == 1) {
        title = "修改类目";
        categoryName = data.ca_category_name;
        categoryCode = data.ca_category_code;
        categoryImg = data.ca_category_img;
        if (data.ca_category_web_img != '' || data.ca_category_web_img != null) {
            categoryWebImg = data.ca_category_web_img;
        }
        caOuteId = data.ca_oute_id;
        caDataIndex = data.ca_data_index;
        caRemark = data.ca_remark;
    }

    var html = "";
    html = html + "<div>" +
        "<table id='caTitle' class='table table-hover table-bordered' style='table-layout:fixed;word-break: break-all; overflow:hidden;'>" +
        "<th>" + title + "</th>" +
        "</table>" +
        "</div>";
    html = html + "<table class='table table-hover table-bordered' style='table-layout:fixed;word-break: break-all; overflow:hidden;'>";
    html = html + "<tr>" +
        "<td class='tableLeft'><span style='color: red;font-weight: bold'>* </span>类目名称</td>" +
        "<td><input type='text' placeholder='请输入类目名称' class='form-control' name='categoryName' id='categoryName' value='" + categoryName + "' ></td>" +
        "</tr>";
    html = html + "<tr>" +
        "<td class='tableLeft'><span style='color: red;font-weight: bold'>* </span>类目编码</td>" +
        "<td><input type='text' placeholder='请输入类目编码' class='form-control' name='categoryCode' id='categoryCode' value='" + categoryCode + "' ></td>" +
        "</tr>";

    html = html + "<tr>" +
        "<td class='tableLeft'><span style='color: red;font-weight: bold'>* </span>图片:<br><span style='color: red;padding-top: 3px'>建议尺寸:80*80</span></td>" +
        "<td style='text-align: left'>" +
        "<div class='imgDiv' id='imgDiv0'><input type='hidden' value='" + categoryImg + "' id='categoryImg' name='categoryImg'>";
    if (categoryId > 0) {
        html = html + "<div class='deleteImg' onclick='deleteImg(this)' delete-mark='0'><img src='./img/delete.png' width='25px' height='25px'></div>";
    }else{
        html = html + "<div class='deleteImg' onclick='deleteImg(this)' delete-mark='0'></div>";
    }

    html = html + "<img style='border: 1px solid #ccc' src='" + categoryWebImg + "' width='80px' height='80px' >" +
        "</div>" +
        "<form id='form0' method='post' enctype='multipart/form-data' action='image/uploadFile'>" +
        "<input style='border: none' type='file' class='form-control' name='file' id='file1' multiple='multiple' data-mark='0'  onchange='uploadFile(this)'>" +
        "</form>" +
        "</td>" +
        "</tr>";

    html = html + "<tr>" +
        "<td class='tableLeft'><span style='color: red;font-weight: bold'> </span>外部标识</td>" +
        "<td><input type='text' placeholder='请输入外部标识' class='form-control' name='caOuteId' id='caOuteId'  value='" + caOuteId + "' ></td>" +
        "</tr>";
    html = html + "<tr>" +
        "<td class='tableLeft'><span style='color: red;font-weight: bold'>* </span>排序</td>" +
        "<td>" +
        "<div style='display: flex'>" +
        "<input type='text' placeholder='请输入排序' class='form-control form-control-common' id='caDataIndex' name='caDataIndex' value='" + caDataIndex + "'>" +
        "<div style='color: red;padding-left: 10px;line-height: 45px'>数值越大越靠前</div>" +
        "</div>" +
        "</td>" +
        "</tr>";
    html = html + "<tr>" +
        "<td class='tableLeft'>备注</td>" +
        "<td tyle='text-align: left'><textarea style='width: 100%;resize: none;border: 1px solid #ccc;'  rows='4'  id='caRemark' name='caRemark'>" + caRemark + "</textarea></td>" +
        "</tr>" +
        "<tr>";
    html = html + "<td colspan='2'>" +
        "<input type='button' value='保存' class='btn btn-success btn-save' onclick='submitAddCategory()' >" +
        "</td>" +
        "</tr>";
    html = html + "</table>";
    $("#categoryDiv").html(html);
    $("#categoryDiv").show();
    $("#propDiv").hide();
}

//修改属性
function onclickPropTr(obj) {
    var propId = $(obj).parent().attr("data-prop-id");
    var categoryId = $(obj).parent().attr("data-category-id");
    $("#propId").val(propId);
    addPropDiv(propId, categoryId);
}

//新增属性
function clickAddProp() {
    var categoryId = $("#categoryId").val();
    if (null == categoryId || categoryId == '') {
        return myAlertSimple("请点击所属类目行");
    }
    $("#propId").val("");
    addPropDiv(0, categoryId);
}

function addPropDiv(propId, categoryId) {
    var data = "";
    var index = 0;
    if (propId > 0) {
        //修改
        $("#propId").val(propId);
        //查询类目ById
        $.ajax({
            url: "category/loadCategoryProp",
            type: 'POST',
            data: {
                "categoryId": categoryId,
                "propId": propId
            },// 要提交的表单 ,
            async: false,
            cache: false,
            dataType: "json",
            success: function (data) {
                data = data.data;
                index = 1;
                showPropHtml(data, index);
            }
        });
    } else {
        //新增
        $("#propId").val("");
        showPropHtml(data, index);
    }
}

function showPropHtml(data, index) {
    var propId = $("#propId").val();
    var title = "新增类目属性";
    var propName = "";
    var propCode = "";
    var propImg = "";
    var propWebImg = "./img/null.jpg";
    var pOuteId = "";
    var pDataIndex = "";
    var isSales = "";
    var isSalesSelect = "";
    var showView = "";
    var showViewSelect = "";
    var pRemark = "";

    //类目名称
    var categoryId = $("#categoryId").val();
    var categoryName = "";
    for (var i = 0; i < categoryList.length; i++) {
        if (categoryId == categoryList[i].categoryId) {
            categoryName = categoryList[i].categoryName;
        }
    }


    var arrayValue;//属性值
    //属性值div
    var propHtml = "";
    propHtml = propHtml + "<div><table class='table table-hover table-bordered' id='propValueTable'>";//表头
    if (index == 1) {
        title = "修改类目属性";
        propName = data.propName;
        propCode = data.propCode;
        propImg = data.propImg;
        propWebImg = data.propWebImg;
        pOuteId = data.outeId;
        pDataIndex = data.dataIndex;
        isSales = data.isSales;
        if (isSales == 0) {
            isSalesSelect = "selected";
        }
        showView = data.showView;
        if ("checkbox" == showView) {
            showViewSelect = "selected";
        }
        pRemark = data.remark;
        arrayValue = data.propValueObjList;
        if (arrayValue.length > 0) {
            for (var i = 0; i < arrayValue.length; i++) {
                var valueId = arrayValue[i].valueId;
                var valueName = arrayValue[i].valueName;
                var outeId = arrayValue[i].outeId;
                var vValue = arrayValue[i].value;
                var vDataIndex = arrayValue[i].dataIndex;
                propHtml = propHtml + "<tr>" +
                    "<td class='tableLeft2'><span style='color: red;font-weight: bold'>* </span>值名称</td>" +
                    "<td>" +
                    "<input type='hidden' name='valueId' value='" + valueId + "'>" +
                    "<input type='text' placeholder='值名称' class='form-control form-control-common' name='valueName'  value='" + valueName + "' >" +
                    "</td>" +
                    "<td class='tableLeft2'><span style='color: red;font-weight: bold'> </span>外部标识</td>" +
                    "<td><input type='text' placeholder='外部标识' class='form-control form-control-common input-width100' name='vOuteId'  value='" + outeId + "' ></td>" +
                    "<td class='tableLeft2'><span style='color: red;font-weight: bold'>* </span>属性值</td>" +
                    "<td><input type='text' placeholder='属性值' class='form-control form-control-common' name='vValue'  value='" + vValue + "' ></td>" +
                    "<td class='tableLeft2'><span style='color: red;font-weight: bold'>* </span>排序</td>" +
                    "<td><input type='text' placeholder='排序' class='form-control form-control-common input-width60' name='vDataIndex'  value='" + vDataIndex + "' ></td>" +
                    "<td><input type='button' value='移除' class='btn btn-danger' onclick='removePropValueTr(this)'></td>" +
                    "</tr>";
            }
        }
    }
    propHtml = propHtml + "</table></div>";//表结尾

    var html = "";
    html = html + "<div>" +
        "<table id='pTitle' class='table table-hover table-bordered' style='table-layout:fixed;word-break: break-all; overflow:hidden;'>" +
        "<th>" + title + "</th>" +
        "</table>" +
        "</div>";
    html = html + "<table class='table table-hover table-bordered' style='table-layout:fixed;word-break: break-all; overflow:hidden;'>";
    html = html + "<tr>" +
        "<td class='tableLeft'><span style='color: red;font-weight: bold'> </span>所属类目</td>" +
        "<td><input type='text' readonly  class='form-control' name='pCategoryName'  value='" + categoryName + "' ></td>" +
        "</tr>";
    html = html + "<tr>" +
        "<td class='tableLeft'><span style='color: red;font-weight: bold'>* </span>属性名称</td>" +
        "<td><input type='text' onkeydown='updatePropNameDiv(this)' onkeyup='updatePropNameDiv(this)' placeholder='请输入属性名称' class='form-control' name='propName' id='propName'  value='" + propName + "' ></td>" +
        "</tr>";
    html = html + "<tr>" +
        "<td class='tableLeft'><span style='color: red;font-weight: bold'>* </span>属性编码</td>" +
        "<td><input type='text' placeholder='请输入属性名称' class='form-control' name='propCode'  value='" + propCode + "' id='propCode' ></td>" +
        "</tr>";


    html = html + "<tr>" +
        "<td class='tableLeft'><span style='color: red;font-weight: bold'>* </span>图片:<br><span style='color: red;padding-top: 3px'>建议尺寸:80*80</span></td>" +
        "<td style='text-align: left'>" +
        "<div  class='imgDiv' id='imgDiv1'>" +
        "<input type='hidden' value='" + propImg + "' id='propImg' name='propImg'>";
    if (propId > 0) {
        html = html + "<div class='deleteImg' onclick='deleteImg(this)' delete-mark='1'><img src='./img/delete.png' width='25px' height='25px'></div>";
    }else{
        html = html + "<div class='deleteImg' onclick='deleteImg(this)' delete-mark='0'></div>";
    }

    html = html + "<img style='border: 1px solid #ccc' src='" + propWebImg + "' width='80px' height='80px' >" +
        "</div>" +
        "<form id='form1' method='post' enctype='multipart/form-data' action='image/uploadFile'>" +
        "<input style='border: none' type='file' class='form-control' name='file' id='file1' multiple='multiple' data-mark='1'  onchange='uploadFile(this)'>" +
        "</form>" +
        "</td>" +
        "</tr>";

    html = html + "<tr>" +
        "<td class='tableLeft'><span style='color: red;font-weight: bold'> </span>外部标识</td>" +
        "<td><input type='text' placeholder='请输入外部标识' class='form-control' name='pOuteId' id='pOuteId' value='" + pOuteId + "' ></td>" +
        "</tr>";
    html = html + "<tr>" +
        "<td class='tableLeft'><span style='color: red;font-weight: bold'>* </span>排序</td>" +
        "<td>" +
        "<div style='display: flex'>" +
        "<input type='text' placeholder='请输入排序' class='form-control form-control-common' id='pDataIndex' name='pDataIndex' value='" + pDataIndex + "'>" +
        "<div style='color: red;padding-left: 10px;line-height: 45px'>数值越大越靠前</div>" +
        "</div>" +
        "</td>" +
        "</tr>";
    html = html + "<tr>" +
        "<td class='tableLeft'><span style='color: red;font-weight: bold'>* </span>是否销售属性</td>" +
        "<td>" +
        "<select  class='form-control form-control-common' name='isSales' id='isSales'>" +
        "<option value='1'>是销售属性</option>" +
        "<option value='0' " + isSalesSelect + ">不是销售属性</option>" +
        "</select>" +
        "</td>" +
        "</tr>";
    html = html + "<tr>" +
        "<td class='tableLeft'><span style='color: red;font-weight: bold'>* </span>显示类型</td>" +
        "<td>" +
        "<select  class='form-control form-control-common' id='showView' name='showView'>" +
        "<option value='select'>下拉</option>" +
        "<option value='checkbox' " + showViewSelect + ">多选</option>" +
        "</select>" +
        "</td>" +
        "</tr>";
    html = html + "<tr>" +
        "<td class='tableLeft'>备注</td>" +
        "<td tyle='text-align: left'><textarea style='width: 100%;resize: none;border: 1px solid #ccc;'  rows='4' id='pRemark' name='pRemark'>" + pRemark + "</textarea></td>" +
        "</tr>";
    html = html + "<tr>" +
        "<td colspan='2'>" +
        "<table class='table' style='border: none'>" +
        "<tr>" +
        "<td style='border: none'>" +
        "<div style='text-align: center;' align='center'>" +
        "<span>属性名称:</span><span id='propNameDiv' style='color: blue;padding: 0px 10px 0 3px;font-weight: bold'>" + propName + "</span>" +
        "<button class='btn btn-success' type='button' onclick='addPropValueDiv()' ><i class=''></i><span class='bold'>新增属性值</span></button>" +
        "</div>" +
        "</td>" +
        "</tr>" +
        "</table>" +
        "<div id='propValueDiv'>";
    html = html + propHtml;
    html = html + "</div>" +
        "</td>" +
        "</tr>";
    html = html + "<tr>" +
        "<td colspan='2'>" +
        "<input type='button' value='保存' class='btn btn-success btn-save' onclick='submitAddCategoryProp()' >" +
        "</td>" +
        "</tr>";
    html = html + "</table>";

    $("#propDiv").html(html);
    $("#propDiv").show();
    $("#categoryDiv").hide();
}

//增加属性值tr
function addPropValueDiv() {
    var html = "<tr>" +
        "<td class='tableLeft2'><span style='color: red;font-weight: bold'>* </span>值名称</td>" +
        "<td>" +
        "<input type='hidden' name='valueId' value='0'>" +
        "<input type='text' placeholder='值名称' class='form-control form-control-common' name='valueName'  value='' >" +
        "</td>" +
        "<td class='tableLeft2'><span style='color: red;font-weight: bold'> </span>外部标识</td>" +
        "<td><input type='text' placeholder='外部标识' class='form-control form-control-common input-width100' name='vOuteId'  value='' ></td>" +
        "<td class='tableLeft2'><span style='color: red;font-weight: bold'>* </span>属性值</td>" +
        "<td><input type='text' placeholder='属性值' class='form-control form-control-common' name='vValue'  value='' ></td>" +
        "<td class='tableLeft2'><span style='color: red;font-weight: bold'>* </span>排序</td>" +
        "<td><input type='text' placeholder='排序' class='form-control form-control-common input-width60' name='vDataIndex'  value='0' ></td>" +
        "<td><input type='button' value='移除' class='btn btn-danger' onclick='removePropValueTr(this)'></td>" +
        "</tr>";
    $("#propValueTable").append(html);
}

//上传图片
function uploadFile(obj) {
    var mark = $(obj).attr("data-mark");//0类目图片 1属性图片
    var imgPath = "";
    var imgWebPath = "";

    //上传完得到id 或者路径 标志上传成功
    var data = new FormData($('#form'+mark)[0]);
    $.ajax({
        url: "image/uploadFile",
        type: 'POST',
        data: data,
        dataType: 'JSON',
        cache: false,
        processData: false,
        contentType: false
    }).done(function (ret) {
        if (ret.code == 12) {
            imgPath = ret.imgfdfsUrl;
            imgWebPath = ret.imgWebUrl;
            if (imgPath == '') {
                return;
            }
            setPageShow(imgPath, imgWebPath, mark);
        } else {
            return myAlertSimple(ret.msg);
        }
    });
    //重置上传控件
    if (mark == 0) {
        $("#form0").html("<input style=\"border: none\" type=\"file\" class=\"form-control\" name=\"file\" id='file0' multiple=\"multiple\"  data-mark=\"0\"  onchange=\"uploadFile(this)\">");
    } else {
        $("#form1").html("<input style=\"border: none\" type=\"file\" class=\"form-control\" name=\"file\" id='file1' multiple=\"multiple\" data-mark=\"1\" onchange=\"uploadFile(this)\">");
    }
}

//设置页面图片
function setPageShow(imgPath, imgWebPath, mark) {
    if (mark == 0) {
        var html = "<input type='hidden' value='" + imgPath + "' id='categoryImg' name='categoryImg'>";
        html = html + "<div class='deleteImg' onclick='deleteImg(this)' delete-mark='0'><img src='./img/delete.png' width='25px' height='25px'></div>";
        html = html + "<img style='border: 1px solid #ccc' src='" + imgWebPath + "' width='80px' height='80px' >";
        $("#imgDiv0").html(html);
    } else {
        var html = "<input type='hidden' value='" + imgPath + "' id='propImg' name='propImg'>";
        html = html + "<div class='deleteImg' onclick='deleteImg(this)' delete-mark='1'><img src='./img/delete.png' width='25px' height='25px'></div>";
        html = html + "<img style='border: 1px solid #ccc' src='" + imgWebPath + "' width='80px' height='80px' >";
        $("#imgDiv1").html(html);
    }
}


//删除图片
function deleteImg(obj) {
    var mark = $(obj).attr("delete-mark");//0类目图片 1属性图片
    if (mark == 0) {
        var html = "<input type='hidden' value='' id='categoryImg' name='categoryImg'>";
        html = html + "<div class='deleteImg' onclick='deleteImg(this)' delete-mark='0'></div>";
        html = html + "<img style='border: 1px solid #ccc' src='./img/null.jpg' width='80px' height='80px' >";
        $("#imgDiv0").html(html);
    } else {
        var html = "<input type='hidden' value='' id='propImg' name='propImg'>";
        html = html + "<div class='deleteImg' onclick='deleteImg(this)' delete-mark='1'></div>";
        html = html + "<img style='border: 1px solid #ccc' src='./img/null.jpg' width='80px' height='80px' >";
        $("#imgDiv1").html(html);
    }

}

