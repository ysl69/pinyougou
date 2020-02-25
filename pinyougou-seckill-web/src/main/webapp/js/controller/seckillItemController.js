var app = new Vue({
    el:'#app',
    data:{
        pages: 15,
        pageNo: 1,
        list: [],
        entity: {},
        ids: [],
        goodsInfo:{},
        seckillId:0,
        messageInfo:'',
        timeString:'',
        searchEntity: {}
    },
    methods:{

        /**
         *
         * @param alltime 为 时间的毫秒数。
         * @returns {string}
         */
        convertTimeString:function(alltime){
            var allsecond=Math.floor(alltime/1000);//毫秒数转成 秒数。
            var days= Math.floor( allsecond/(60*60*24));//天数
            var hours= Math.floor( (allsecond-days*60*60*24)/(60*60) );//小时数
            var minutes= Math.floor(  (allsecond -days*60*60*24 - hours*60*60)/60    );//分钟数
            var seconds= allsecond -days*60*60*24 - hours*60*60 -minutes*60; //秒数
            if(days>0){
                days=days+"天 ";
            }
            if(hours<10){
                hours="0"+hours;
            }
            if(minutes<10){
                minutes="0"+minutes;
            }
            if(seconds<10){
                seconds="0"+seconds;
            }
            return days+hours+":"+minutes+":"+seconds;
        },
        //倒计时
        caculate: function (alltime) {

            let clock = window.setInterval(function () {
                alltime = alltime - 1000;
                //反复被执行的函数
                app.timeString = app.convertTimeString(alltime);
                if (alltime <= 0) {
                    //取消
                    window.clearInterval(clock);
                }
            }, 1000);//相隔1000执行一次。
        },

        //根据商品的ID 获取商品的数据：剩余时间的毫秒数以及 商品的库存
        getGoodsById: function (id) {
            axios.get('/seckillGoods/getGoodsById.shtml', {
                params: {
                    id: id
                }
            }).then(function (response) {
                console.log(response.data);
                app.goodsInfo = response.data;
                app.caculate(response.data.time);
                console.log(app.goodsInfo);
            })
        },


        //秒杀抢单
        submitOrder:function () {
            console.log("下单的id的值为:" + this.seckillId);
            axios.get('/seckillOrder/submitOrder/'+this.seckillId+'.shtml').then(function (response) {
                if (response.data.success){
                    //跳转到支付页面
                    alert("去支付");
                    app.messageInfo = response.data.message;
                }else {
                    if (response.data.message=='403'){
                        // 说明没有登录 要去登陆
                        alert("要登录");
                        var url = window.location.href;//获取当前浏览器中的URL的地址
                        window.location.href="http://localhost:9111/page/login.shtml?url="+url;
                    } else {
                        app.messageInfo = response.data.message;
                    }
                }
            })
        },


        //查询订单的状态 当点击立即抢购之后执行
        queryStatus:function () {
            let count = 0;
            //三秒钟执行一次
            let queryorder = window.setInterval(function () {
                count+=3;
                axios.get('/seckillOrder/queryOrderStatus.shtml').then(function (response) {
                    console.log("正在查询.............状态值"+response.data.message);
                    if (response.data.success){
                        //跳转到支付页面
                        window.clearInterval(queryorder);
                        //alert("跳转到支付页面");
                        window.location.href="pay/pay.html";
                    }else {
                        if (response.data.message == '403'){
                            //要登录
                        }else {
                            //不需要登录需要提示
                            app.messageInfo=response.data.message+"....."+count;
                        }
                    }
                })
            },3000)
        }


    },
    created:function () {
        //开始执行 获取参数
        let urlParam = this.getUrlParam();
        //获取秒杀商的ID
        this.seckillId=urlParam.id;


        //开始执行
        //this.caculate(1000000);

        //获取商品的时间信息
        this.getGoodsById(this.seckillId);
    }
})