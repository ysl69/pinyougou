var app = new Vue({
    el:'#app',
    data:{
        totalMoney:0,
        payObject:{}  //封装支付的金额 二维码连接 交易订单号
    },
    methods:{

        // 生成二维码
        createNative:function(){
            axios.get('/pay/createNative.shtml').then(function (response) {
                if (response.data){
                    // 有数据
                    app.payObject = response.data;
                    //订单金额
                    app.payObject.total_fee = response.data.total_fee/100;
                    // 商户订单号
                    app.out_trade_no=response.data.out_trade_no;
                    //生成二维码
                    var qRious = new QRious({
                        element:document.getElementById("qrious"),
                       level: "H",
                        size:250,
                        value:response.data.code_url

                    });
                    //已经生成二维码了
                    if (qRious){
                        // 发送请求获取值
                        app.queryPayStatus(app.payObject.out_trade_no);
                    }
                }else {
                    // 没有数据
                }

            })
        },


        //查询支付状态
        queryPayStatus:function (out_trade_no) {
            axios.get('/pay/queryPayStatus.shtml',{
                params:{
                    out_trade_no : out_trade_no
                }
            }).then(function (response) {
                    if (response.data.success){
                        //支付成功
                        window.location.href="paysuccess.html?money="+app.payObject.total_fee;
                    }else {
                        //支付失败
                        if (response.data.message == "支付超时"){
                            // 刷新二维码
                            app.createNative();
                        }else {
                            window.location.href = "payfail.html";
                        }
                    }
            })
        }


    },
    created:function () {
        // 当页面一加载就调用
        if (window.location.href.indexOf("pay.html") != -1){
            this.createNative();
        }else {
            var urlParamObject = this.getUrlParam();
            if(urlParamObject.money){
                this.totalMoney = urlParamObject.money;
            }
        }

    }
})