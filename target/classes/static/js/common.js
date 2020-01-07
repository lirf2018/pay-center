function endWith(str, reg) {
    var regLength = reg.length;
    var strLength = str.length;
    if (regLength > strLength) {
        return false;
    }
    var index = str.substr(strLength - regLength, strLength - 1);
    if (index == reg) {
        return;
    }
    return false;
}

function myAlertSimple(text) {
    swal({
        title: "提示",
        text: text
    });
}

function myAlertSimpleWithClose(text) {
    swal({
        title: "提示",
        text: text
    });
    setTimeout("javascript:swal.close()",1000);
}

//将时间戳格式化
function getMyDate(time) {
    if (typeof(time) == "undefined") {
        return "";
    }
    var oDate = new Date(time),
        oYear = oDate.getFullYear(),
        oMonth = oDate.getMonth() + 1,
        oDay = oDate.getDate(),
        oHour = oDate.getHours(),
        oMin = oDate.getMinutes(),
        oSen = oDate.getSeconds(),
        oTime = oYear + '-' + getzf(oMonth) + '-' + getzf(oDay) + ' ' + getzf(oHour) + ':' + getzf(oMin) + ':' + getzf(oSen);//最后拼接时间

    return oTime;
}

//补0操作,当时间数据小于10的时候，给该数据前面加一个0
function getzf(num) {
    if (parseInt(num) < 10) {
        num = '0' + num;
    }
    return num;
}

//数字校验
function checknumber(number) {
    var reg = /^[0-9]*$/;
    if (null == number || number == '' || !reg.test($.trim(number))) {
        return false;
    }
    return true;
    //或者直接
    //onkeyup="this.value=this.value.replace(/[^\d]/g,'')"
}

//只能输入数字和小数
function numAndpoint(number) {
    var reg = /^\d+(\.\d+)?$/;
    if (null == number || number == '' || !reg.test($.trim(number))) {
        return false;
    }
    return true;
}


//页面条件查询
function searchData() {
    table.draw({
        start: 0
    });
}