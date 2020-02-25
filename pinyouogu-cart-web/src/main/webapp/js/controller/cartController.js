var app = new Vue({
    el:'#app',
    data:{
        cartList:[],
        addressList:[],
        address:{},
        order:{paymentType:'1'},
        totalMoney:0, //总金额
        totalNum:0  // 总数量
    },
    methods:{
        //查询所有的购物车的列表数据
        findCartList:function () {
            axios.get('/cart/findCartList.shtml').then(function (response) {
                //获取购物车列表数据
                app.cartList = response.data;
                app.totalMoney = 0;
                app.totalNum = 0;

                for(var i=0;i<response.data.length;i++){
                    var obj = response.data[i];//Cart
                    for(let j=0;j<obj.orderItemList.length;j++){
                        app.totalNum+=obj.orderItemList[j].num;
                        app.totalMoney+=obj.orderItemList[j].totalFee;
                    }
                }
            })
        },


        //向已有的购物车中添加商品
        addGoodsToCartList:function (itemId,num) {
            axios.get('/cart/addGoodsToCartList.shtml?itemId='+itemId+'&num='+num).then(function (response) {
                    if (response.data.success){
                        // 添加成功
                        app.findCartList();
                    }else {
                        // 添加失败
                        alert(response.data.message);
                    }
            })
        },


        // 查询地址
        findAddressLsit:function () {
            axios.get('/address/findAddressListByUserId.shtml').then(function (response) {
                app.addressList = response.data;
                for (var i=1;i<app.addressList.length;i++){
                    if (app.addressList[i].isDefault=='1'){
                        app.address = app.addressList[i];
                        break;
                    }
                }
            })
        },


        //地址选择
        selectAddress:function (address) {
            this.address = address;
        },
        isSelectedAddress:function (address) {
            if (address == this.address){
                return true;
            }
            return false;
        },


        //支付方式选择
        selectType:function (type) {
            console.log(type);
            this.$set(this.order,"paymentType",type);
            //this.order.paymentType=type;
        },


        // 显示商品清单
        created:function () {
            this.findCartList();
            //判断如果是getOrderInfo.html的时候才加载
            this.findAddressLsit();
        },

        //添加一个方法
        submitOrder: function () {
            //设置值
            this.$set(this.order,'receiverAreaName',this.address.address);
            this.$set(this.order,'receiverMobile',this.address.mobile);
            this.$set(this.order,'receiver',this.address.contact);
            axios.post('/order/add.shtml', this.order).then(
                function (response) {
                    if(response.data.success){
                        //跳转到支付页面
                        window.location.href="pay.html";
                    }else{
                        alert(response.data.message);
                    }
                }
            )
        },

    },

    created:function () {
        this.findCartList();
        
        if (window.location.href.indexOf("getOrderInfo.html")!=-1){
            this.findAddressLsit();
        }
    }
})