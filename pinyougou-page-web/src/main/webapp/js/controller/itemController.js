var app = new Vue({
    el:'#app',
    data:{
        num:1, //商品的购买数量
        specificationItems:JSON.parse(JSON.stringify(skuList[0].spec)),//默认展示第一个数组元素对应的规格数据
        sku:skuList[0]//数组第一个元素就是sku的数据展示
    },
    methods:{
        addNum:function (num) {
            num = parseInt(num);
            this.num+=num;  //加或者减
            if (this.num<=1){
                this.num=1;
            }
        },
        //方法  点击选项的时候调用 （ 用于判断当前的变量specificationItems 和SKU列表中的变量的值是否一致，
        //如果一致，需要将SKU变量绑定到一致的那个对象）
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


    },

    created:function () {

    }
})