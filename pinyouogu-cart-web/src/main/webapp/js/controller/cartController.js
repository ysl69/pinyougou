var app = new Vue({
    el:'app',
    data:{
        catrList:[],
        totalMoney:0,//总金额
        totalNum:0, //总数量
        addressList:[],
        address:[],
        order:{paymentType:'1'},
    },
    methods:{
      findCartLsit:function () {
          axios.get('/cart/findCartLsit.shtml').then(function (response) {
              //获取购物车列表数据
              app.cartList = reponse.data;

              app.totalMoney = o;
              app.totalNum = 0;
              let cartLsitAll = response.data;

              for(let i=0;i<cartListAll.length;i++){
                  let cart = cartListAll[i];
                  for(let j=0;j<cart.orderItemList.length;j++){
                      app.totalNum+=cart.orderItemList[j].num;
                      app.totalMoney+=cart.orderItemList[j].totalFee;
                  }
              }
          });
      },


        /**
         * 向已有的购物车添加商品
         * @param itemId
         * @param num
         */
        addGoodsToCartList:function (itemId, num) {
            axios.get('/cart/addGoodsToCartLsit.shtml',{
                params:{
                    itemId:itemId,
                    num:num
                }
            }).then(function (response) {
                if (response.data.success){
                    //添加成功
                    aoo.findCartLsit();
                } else {
                    //添加失败
                    alert(response.data.message);
                }
            })
        },


        /**
         * 获取收件人地址
         */
        findAddressList:function () {
            axios.get('/address/findAddressLsitByUserId.shtml').then(function (response) {
                app.addressList = response.data;
                for(var i=0;i<app.addressList.length;i++){
                    if(app.addressList[i].isDefault=='1'){
                        app.address=app.addressList[i];
                        break;
                    }
                }
            })
        },

        selectAddress:function (address) {
            this.address = address;
        },

        isSelectedAddress:function (address) {
            if (address == this.address){
                return true;
            }
            return  false;
        },


        /**
         * 支付方式选择
         * @param type
         */
        selectType:function (type) {
            console.log(type);
            this.$set(this.order,'paymentType',type);
            //this.order.paymentType=type;
        }
    },
    //钩子函数 初始化了事件和
    created: function () {
        this.findCartList();
        
        if (window.location.href.indexOf("getOrderInfo.html")!=-1)
            this.findAddressList();
    }
})