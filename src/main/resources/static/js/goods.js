// 根据一级分类设置类目
function setCategory() {
    var levelId = $("#levelId").val();
    $("#categoryId").html(categoryHtml);//设置类目初始
    resetAll();
    if(levelId == ''){
        return;
    }
    $.ajax({
        url: "category/loadLevelCategoryData",
        type: 'POST',
        data: {
            "levelId":levelId,
            "status":1
        },// 要提交的表单 ,category_id category_name
        async: false,
        cache: false,
        dataType: "json",
        success: function (data) {
            var list = data.data;
            var html = "";
            for (var i = 0; i < list.length; i++) {
                if(goodsCategoryId == list[i].category_id){
                    html = html + "<option value='"+list[i].category_id+"' selected>"+list[i].category_name+"</option>";
                }else{
                    html = html + "<option value='"+list[i].category_id+"'>"+list[i].category_name+"</option>";
                }

            }
            $("#categoryId").append(html);
            changeCategory();
        }
    });
}

//选择类目
function changeCategory() {
    var categoryId = $("#categoryId").val();
    var goodsId = $("#goodsId").val();
    resetAll();//重置全陪
    if(categoryId == ''){
        return;
    }
    $.ajax({
        url: "goods/loadCategoryRel",
        type: 'POST',
        data: {
            "categoryId":categoryId,
            "goodsId":goodsId
        },// 要提交的表单 ,category_id category_name
        async: false,
        cache: false,
        dataType: "json",
        success: function (data) {
            var list = data.list;
            var html = "";
            for (var i = 0; i < list.length; i++) {
                var propId = list[i].propId;//属性ID
                var showView = list[i].showView;//显示类型:checkbox：多选 select：下拉列表',
                var isSales = list[i].isSales;//是否销售属性:0不是销售属性1是销售属性
                var propName = list[i].propName;//属性名称
                html = html + "<tr><td class='tableLeft'prop-sell='"+isSales+"' prop_id='"+propId+"' prop_name='"+propName+"'  show-view='"+showView+"'><span style='color: red'>"+(isSales == 1?"*":"")+" </span>"+propName+"</td><td style='text-align: left'>&nbsp;&nbsp;propValueHtml</td></tr>";
                var selected = " <select class='form-control form-control-common' onchange='onchangeSkuSellProp()'><option value='-1'>--选择"+propName+"--</option>selectHtml</select>";
                //
                if (isSales == 1) {
                    salesCount = salesCount + 1;
                }
                var propValueHtml = "";
                for (var j = 0; j < list[i].propValueObjList.length; j++) {
                    var valueId = list[i].propValueObjList[j].valueId;
                    var valueName = list[i].propValueObjList[j].valueName;
                    var relGoods = list[i].propValueObjList[j].relGoods;//是否与商品关联 1 关联
                    if('checkbox' == showView){
                        propValueHtml = propValueHtml + "<input type='checkbox'  "+(relGoods==1?"checked":"")+" value='"+valueId+"`"+valueName+"' onclick='onchangeSkuSellProp()'> "+valueName+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
                    }else{
                        propValueHtml = propValueHtml + "<option "+((relGoods==1?"selected":""))+" value='"+valueId+"`"+valueName+"'>"+valueName+"</option>";
                    }
                }
                if('select' == showView){
                    propValueHtml = selected.replace("selectHtml",propValueHtml);
                }
                html = html.replace("propValueHtml",propValueHtml);
            }
            $("#classifyList").html(html);
            if (list.length > 0) {
                $("#classifyDiv").show();
                onchangeSkuSellProp()
            }else{
                $("#classifyDiv").hide();
            }
        }
    });

}

//点击选择销售属性(对已选择的属性组合)
function onchangeSkuSellProp(){
    resetSellPropValue();//重置
    var flag = false;
    var rootHtml = $("#classifyList>tr");
    rootHtml.each(function () {
        var tdFirst = $(this).children(0);//获取第1个td
        var propId = tdFirst.attr("prop_id");//属性ID
        var showView = tdFirst.attr("show-view");//显示类型:checkbox：多选 select：下拉列表',
        var isSales = tdFirst.attr("prop-sell");//是否销售属性:0不是销售属性1是销售属性
        var propName = tdFirst.attr("prop_name");//属性名称
        var propNameArray = [];
        propNameArray.push(propId);
        propNameArray.push(propName);
        //alert("propId:"+propId+"  showView:"+showView+"  isSales:"+isSales+"  propName:"+propName);
        //alert($(this).html());
        if (isSales == 1) {
            //销售属性
            flag = true;
            var propValueArray = [];//已经选中的属性值
            var tdArray = $(this).children();
            var objTD = tdArray[1].children;
            if (showView == 'checkbox') {
                for (var i = 0; i < objTD.length; i++) {
                    var inputObj = objTD[i];
                    if(inputObj.checked){
                        propValueArray.push(inputObj.value)
                    }
                }
            } else {
                var value = objTD[0].value;
                if(-1 != value){
                    propValueArray.push(value)
                }
            }
            if (propValueArray.length > 0) {
                propValueArrays.push(propValueArray);//属性值
            }
            propNameArrays.push(propNameArray);//属性名称
        }else{
            //非销售属性
            flag = true;
            var tdArray = $(this).children();
            var objTD = tdArray[1].children;
            if (showView == 'checkbox') {
                for (var i = 0; i < objTD.length; i++) {
                    var inputObj = objTD[i];
                    if(inputObj.checked){
                        propValueUnSellArrays.push(inputObj.value);//选择的属性值Id
                    }
                }
            } else {
                var value = objTD[0].value;
                if(-1 != value){
                    propValueUnSellArrays.push(value);//选择的属性值Id
                }
            }
        }
    });
    //console.log("propValueArrays:" + propNameArrays);
    //console.log("propValueArrays:" + propValueArrays);
    //console.log("propValueArrays:" + propValueArrays.length);
    //生成商品Sku页面元素
    if (flag && propValueArrays.length == propNameArrays.length) {
        showGoodsSku();
    } else if (flag) {
        console.log("清空生成商品Sku页面元素");
    }
}

//重置全部(选中一级分类)(选中类目)
function resetAll() {
    propNameArrays = [];//重置销售属性[[],[]]
    propValueArrays = [];//重置已经选中的属性值[[],[]]
    propValueUnSellArrays = [];//非销售属性已经选中的属性值标识[[],[]]
    salesCount = 0;
    //$("#classifyDiv").html("");//属性选择层

    $("#classifyList").html("");//属性选择层
    $("#classifySkuList").html("");//商品sku层
    $("#classifyDiv").hide();//整个sku tr
    $("#skuTable").hide();//商品sku层table  标题
}

//重置销售属性  重置已经选中的属性值
function resetSellPropValue() {
    propNameArrays = [];//重置销售属性[[],[]]
    propValueArrays = [];//重置已经选中的属性值[[],[]]
    propValueUnSellArrays = [];//非销售属性已经选中的属性值标识[[],[]]
    $("#classifySkuList").html("");
    $("#skuTable").hide();
}

//生成商品Sku页面元素
function showGoodsSku() {
    console.log("propNameArrays:" + propNameArrays);
    console.log("propValueArrays:" + propValueArrays);
    console.log("开始生成商品Sku页面元素");
    //处理sku表格头部
    var headHtml = "<th>商品sku名称</th><th>商品sku编码</th><th>商品sku成本价</th><th>商品sku原价</th><th>商品sku销售价</th><th>商品sku库存</th><th>商品sku图片</th>";//固定列
    var dynamicHtml = "";//动态头部列

    var propIdArray = [];//销售属性标识数组
    for (var i = 0; i < propNameArrays.length; i++) {
        var propObj = propNameArrays[i]+"";
        var prop = propObj.split(",");
        dynamicHtml = dynamicHtml + "<th>" + prop[1] + "</th>";
        propIdArray.push(prop[0]);//销售属性标识数组
    }
    $("#skuListHead").html("<tr>"+dynamicHtml+headHtml+"</tr>");
    //处理sku表格内容
    var contentsArrars  = getSkuContent();
    $("#classifySkuList").html("");
    for (var i = 0; i < contentsArrars.length; i++) {
        var propId = propIdArray[i];//属性标识
        var skuPropCode = "";//商品sku属性值标识

        //一次循环为一行数据
        var objArrays =  contentsArrars[i]+"";//一行数据  组合后的内容,不同销售属性间用``分开,一组标识和名称用`分开
        var objArray = objArrays.split("``");//得到分开的销售属性值
        var contentRowDynamicHtml = "";//动态销售属性列
        for (var j = 0; j < objArray.length; j++) {
            var valueObj = objArray[j].split("`");
            var valueId = valueObj[0];//属性值id
            var valueName = valueObj[1];//属性值名称
            contentRowDynamicHtml = contentRowDynamicHtml + "<td>" + valueName + "</td>";
            skuPropCode = skuPropCode + valueId +";";
        }
        //设置sku初始值
        var skuCode = "";
        var skuPurchasePrice = "0.00";
        var skuTrueMoney = "0.00";
        var skuNowMoney = "0.00";
        var skuNum = "0";

        var skuId = "";
        var skuName = "";

        var skuImg = "";
        var skuWebImg = "./img/upload.png";
        var isDeleteImg = "style='display: none'";//sku主图片右上角不显示删除

        for (var j = 0; j < skuList.length; j++) {
            var skuObj = skuList[j];
            if(skuObj.prop_code == skuPropCode){
                skuCode = skuObj.sku_code;
                skuPurchasePrice = skuObj.purchase_price;
                skuTrueMoney = skuObj.true_money;
                skuNowMoney = skuObj.now_money;
                skuNum = skuObj.sku_num;

                skuId = skuObj.sku_id;
                skuName = skuObj.sku_name;

                skuImg = skuObj.sku_img;
                if (skuImg != null && skuImg != '') {
                    skuWebImg = skuObj.sku_web_img;
                    isDeleteImg = "";
                }
                break;
            }
        }
        //固定列
        var contentHtml = "<tr>" + contentRowDynamicHtml;
        contentHtml = contentHtml + "<td><input type='text' placeholder='请输入商品名称' class='form-control' name='skuName' value='" + skuName + "'></td>";//商品sku名称  form-control-common
        contentHtml = contentHtml + "<td><input type='text' placeholder='请输入商品sku编码' class='form-control input-width60' name='skuCode' value='" + skuCode + "'></td>";//商品sku编码  form-control-common
        contentHtml = contentHtml + "<td><input type='text' placeholder='请输入商品sku成本价' class='form-control input-width60' name='skuPurchasePrice' value='" + skuPurchasePrice + "' ></td>"//商品sku成本价
        contentHtml = contentHtml + "<td><input type='text' placeholder='请输入商品sku原价' class='form-control input-width60' name='skuTrueMoney' value='" + skuTrueMoney + "' ></td>";//商品sku原价
        contentHtml = contentHtml + "<td><input type='text' placeholder='请输入商品sku销售价' class='form-control input-width60' name='skuNowMoney' value='" + skuNowMoney + "' ></td>";//商品sku销售价
        contentHtml = contentHtml + "<td><input type='text' placeholder='请输入商品sku库存' class='form-control input-width60' name='skuNum' value='" + skuNum + "' ></td>";//商品sku库存
        contentHtml = contentHtml + "<td>" +
            "<div align='center'><div class='skuImgDiv'>" +
            "      <div id='skuDeleteImg" + i + "' " + isDeleteImg + " class='deleteSkuImg' onclick='deleteSkuImg(this)' delete-mark='" + i + "'><img src='./img/delete1.png' width='15px' height='15px'></div>" +
            "      <img id='webImg" + i + "'  style='border: 1px solid #ccc' src='" + skuWebImg + "' onmousemove='changeImgFileColor(this,0)' onmouseout='changeImgFileColor(this,1)' data-mark='" + i + "' onclick='skuImgOnclick(this)'>" +
            "      <input type='hidden' value='" + skuImg + "' name='skuImg' id='fdfsImg" + i + "'>" +
            "</div></div><input type='hidden' value='" + skuPropCode + "' name='skuPropCode'><input type='hidden' value='" + skuId + "' name='skuId'>" +
            "</td>";//商品sku图片


        contentHtml = contentHtml + "</tr>";
        $("#classifySkuList").append(contentHtml);
    }
    $("#skuTable").show();
}
//生成商品sku
function getSkuContent() {
    index = 1;
    if(propValueArrays.length == 1){
        return propValueArrays[0];
    }else{
        //多个销售属性组合
        var rootArray = propValueArrays[0];
        for (var i = 0; i < propValueArrays.length; i++) {
            if(index >= propValueArrays.length ){
                break;
            }
            rootArray = reducePropValue(rootArray,propValueArrays);
        }
        return rootArray;
    }
}
var index = 1;
function reducePropValue(rootArray,propValueArrays) {
    var resultOut = [];//结果集
    //propValueArrays 开始循环的下标
    for (var i = 0; i < rootArray.length; i++) {
        var rootArrayValue = rootArray[i];
        var valueArrays = propValueArrays[index];//要循环的数据
        for (var j = 0; j < valueArrays.length; j++) {
            var result = [];
            var value = valueArrays[j];
            var rootValus = rootArrayValue + "``"+value;
            result.push(rootValus);
            resultOut.push(result);
        }

    }
    index ++;
    return resultOut;
}