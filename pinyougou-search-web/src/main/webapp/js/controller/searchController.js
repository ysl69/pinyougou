var app = new Vue({
    el: "#app",
    data: {
        searchMap:{'keywords':'','category':'','brand':'',spec:{},'price':'','pageNo':1,'pageSize':40,'sortFiled':'','sortType':''},//作为条件查询的对象
        pageLabels:[],// 页码存储的变量
        preDott:false,
        nextDott:false,
        resultMap:{brandList:[]},//返回的结果对象
        searchEntity: {}
    },
    methods: {
        //根据搜索的条件 执行查询 返回结果 resultmap 点击的时候调用
        search:function () {
            axios.post('/itemSearch/search.shtml',this.searchMap).then(
                function (response) {//response.data=map 会有集合数据
                    app.resultMap=response.data;
                    //调用方法重新构建分页标签
                    app.buildPageLabel();
                }
            )
        },

        // 分页
        buildPageLabel:function () {
            this.pageLabels=[]; //重新赋值给空值
            //显示以当前页为中心的5个页码
            let firstPage=1;
            let lastPage=this.resultMap.totalPages;//总页数

            if(this.resultMap.totalPages>5){
                //判断 如果当前的页码 小于等于3  pageNo<=3  那么就显示前5页的数据     1 2 3 4 5  显示前5页
                if(this.searchMap.pageNo<=3){
                    firstPage=1;
                    lastPage=5;
                    this.preDott=false;
                    this.nextDott=true;
                }else if(this.searchMap.pageNo>=this.resultMap.totalPages-2){
                    //如果当前的页码大于= 总页数-2  显示后5页的数据   98 99 100
                    firstPage=this.resultMap.totalPages-4;
                    lastPage=this.resultMap.totalPages;
                    this.preDott=true;
                    this.nextDott=false;
                }else{
                    //否则 就显示中间的5页码
                    firstPage=this.searchMap.pageNo-2;
                    lastPage=this.searchMap.pageNo+2;

                    this.preDott=true;
                    this.nextDott=true;

                }
            }else{
                //总页数<=5页
                this.preDotted=false;
                this.nextDotted=false;
                firstPage =1;
                lastPage = this.resultMap.totalPages;
            }
            console.log(firstPage);
            console.log(lastPage);

            for(let i=firstPage;i<=lastPage;i++){
                this.pageLabels.push(i);
            }
        },

        //根据页码来查询
        //目的就是 当点击页码的时候调用  将被点击到的页码的值赋值给变量pageNO,发送请求 获取数据
        queryByPage:function(pageNo){
            pageNo = parseInt(pageNo);

            if(pageNo>this.resultMap.totalPages){
                pageNo=this.resultMap.totalPages;
            }
            if(pageNo<1){
                pageNo=1;
            }
            this.searchMap.pageNo=pageNo;
            this.search();
        },

        // 清除之前的查询条件
        clear:function(){
            this.searchMap = {'keywords':this.searchMap.keywords,'category':'','brand':'',spec:{},'price':'','pageNo':1,'pageSize':40,'sortField':'','sortType':''};
        },

        // 排序
        doSort:function(sortFiled,sortType){
            this.searchMap.sortFiled = sortFiled;
            this.searchMap.sortType = sortType;
            this.search();
        },


        // 判断 搜素的关键字是否就是品牌 如果是 返回true  否则返回false
        isKeywordsIsBrand:function(){
            if (this.resultMap.brandList!=null && this.resultMap.brandList.length>0){
                //循环遍历品牌的列表  判断 关键字中是否包含品牌即可 如果是 返回true 否则 返回false
                for (var i = 0; i < this.resultMap.brandList.length; i++) { //[{id:1,text:"联想"},{}]
                    if (this.searchMap.keywords.indexOf(this.resultMap.brandList[i].text) != -1){
                        //赋值brand
                        this.searchMap.brand = this.resultMap.brandList[i].text;
                        return true;
                    }
                }
            }
            return false
        },


        //添加搜索项
        addSearchItem:function (key, value) {
            if (key=='category' || key=='brand' ||key=='price'){
                this.searchMap[key] = value;
            }else {
                this.searchMap.spec[key] = value;

            }
            //发送请求 执行搜索
            this.search();
        },


        //移除掉搜索项
        removeSearchItem:function (key) {
            //1.移除变量里面的值
            if (key=='category' || key=='brand' ||key=='price'){
                this.searchMap[key] ='';
            }else {
                delete this.searchMap.spec[key];
            }
            //2.重新发送请求查询
            this.search();
        },



    },
    created: function () {
        //初始化调用查询所有的商品列表
        //1.获取URL中的参数的值
        var urlParamObj = this.getUrlParam();
        //2.赋值给变量searchMap.keywords
        // decodeURIComponent 解码
        if (urlParamObj.keywords != undefined && urlParamObj != null) {
            this.searchMap.keywords = decodeURIComponent(urlParamObj.keywords);
            //3.执行搜索
            this.search();
        }
    }
});