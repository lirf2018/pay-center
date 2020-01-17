function showDialog(msg) {
    layer.open({
        content: msg
        ,skin: 'msg'
        ,time: 2 //2秒后自动关闭
    });
}

//金额格式转换 保留2位小数
function parsePrice(price) {
    price = price + "";
    var priceArray = price.split(".");
    if (priceArray.length == 1) {
        return price + ".00";
    } else if (priceArray.length == 2) {
        var end = priceArray[1];
        if(end.length == 1){
            return price + "0";
        }
    }
    return price;
}


/*
时间倒计时插件
TimeDown.js
*/
function TimeDown(id, endDateStr) {
    //结束时间
    var endDate = new Date(endDateStr);
    //当前时间
    var nowDate = new Date();
    //相差的总秒数
    var totalSeconds = parseInt((endDate - nowDate) / 1000);
    //天数
    var days = Math.floor(totalSeconds / (60 * 60 * 24));
    //取模（余数）
    var modulo = totalSeconds % (60 * 60 * 24);
    //小时数
    var hours = Math.floor(modulo / (60 * 60));
    modulo = modulo % (60 * 60);
    //分钟
    var minutes = Math.floor(modulo / 60);
    //秒
    var seconds = modulo % 60;
    //输出到页面
    document.getElementById(id).innerHTML = "还剩:" + days + "天" + hours + "小时" + minutes + "分钟" + seconds + "秒";
    //延迟一秒执行自己
    setTimeout(function () {
        TimeDown(id, endDateStr);
    }, 1000)
}

//屏蔽嵌入广告
var del_times = 0, deTimer = null;
function adGo() {
    var iframe = document.getElementsByTagName('iframe')[0];
    if(iframe){
        console.log(iframe)
        var bodyNode = {tagName:''}, iframeParent, targetNode = iframe.parentNode;
        while (bodyNode.tagName != 'BODY'){
            bodyNode = targetNode;
            if(bodyNode.tagName != 'BODY'){
                iframeParent = targetNode;
                targetNode = targetNode.parentNode;
            }
        }
        if(iframeParent) //如果iframe有父类
            bodyNode.removeChild(iframeParent);
        else
            bodyNode.removeChild(iframe);
    }
    del_times++;
    if (del_times > 10) window.clearInterval(deTimer)
}

//抢先 删除 嵌入广告
(function(){adGo();}())

deTimer = self.setInterval(adGo, 200);