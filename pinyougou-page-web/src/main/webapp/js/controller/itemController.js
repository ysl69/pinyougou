var app = new Vue({
    el: "#app",
    data: {
        num:1,//商品的购买数量
        specificationItems:JSON.parse(JSON.stringify(skuList[0].spec)),//定义一个变量用于存储规格的数据
        sku:skuList[0]
    },
    methods: {
        addNum:function(num){
            num = parseInt(num);
            this.num+=num;//加或者减
            if(this.num<=1){
                this.num=1;
            }
        },
        selectSpecifcation:function(name,value){
            //设置值
            this.$set(this.specificationItems,name,value);
            //调用搜索匹配的方法
            this.search();
        },
        isSelected:function(name,value){
            if(this.specificationItems[name]==value){
                return true;
            }else{
                return false;
            }
        },
        search:function(){
            for(var i=0;i<skuList.length;i++){
                var object = skuList[i];
                if(JSON.stringify(this.specificationItems)==JSON.stringify(skuList[i].spec)){
                    console.log(object);
                    this.sku=object;
                    break;
                }
            }
        },

        //添加商品到购物车中
        addGoodsToCart:function(){
            axios.get('http://localhost:9107/cart/addGoodsToCartList.shtml', {
                params: {
                    itemId: this.sku.id,
                    num: this.num
                },
                //客户端在AJax的时候携带cookie到服务器。
                withCredentials: true
            }).then(function (response) {
                if (response.data.success){
                    window.location.href="http://localhost:9107/cart.html";
                }else {
                    //添加购物车失败
                    alert(response.data.message);
                }
            })
        },


    },

    //钩子函数 初始化了事件和
    created: function () {

    }

})