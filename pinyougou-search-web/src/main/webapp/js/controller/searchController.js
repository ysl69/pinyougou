var app = new Vue({
    el: "#app",
    data: {
        pages:15,
        pageNo:1,
        list:[],
        entity:{},
        ids:[],
        preDott:false,
        nextDott:false,
        searchMap:{'keywords':'','category':'','brand':'',spec:{},'price':'','pageNo':1,"pageSize":40,'sortFiled':'','sortType':''},//搜索的条件封装对象
        pageLabels:[],//页码存储的变量
        resultMap:{},//搜索的结果封装对象
        searchEntity:{}
    },
    methods: {
        searchList:function () {
            axios.post('/itemSearch/search.shtml',this.searchMap).then(function (response) {
                //获取数据
                app.resultMap=response.data;
                //默认获取第一个值
                console.log(response.data);

                //调用方法重新构建分页标签
                app.buildPageLabel();

            });
        },


        //添加搜索项
        addSearchItem:function (key, value) {
            if (key=='category' || key=='brand' || key=='price'){
                this.searchMap[key] = value;
            }else {
                this.searchMap.spec[key] = value;
            }
            this.searchList();
        },

        //撤销搜索项
        removeSearchItem:function (key) {
            if (key=='category' || key=='brand' || key=='price'){
                this.searchMap[key] = '';
            } else {
                delete this.searchMap.spec[key];
            }
            this.searchList();
        },

        //搜索结果分页
        buildPageLabel:function () {
            this.pageLabels=[];
            //显示以当前为中心的5个页码
            let firstPage=1;
            let lastPage=this.resultMap.totalPages;//总页数

            if (this.resultMap.totalPages>5){
                //判断 如果当前的页码 小于等于3  pageNo<=3      1 2 3 4 5  显示前5页
                if (this.searchMap.pageNo<=3){
                    firstPage=1;
                    lastPage=5;
                    this.preDott=false;
                    this.nextDott=true;
                }else if (this.searchMap.pageNo>=this.resultMap.totalPages-2) {//如果当前的页码大于= 总页数-2    98 99 100
                    firstPage=this.resultMap.totalPages-4;
                    lastPage=this.resultMap.totalPages;
                    this.preDott=true;
                    this.nextDott=false;
                }else{
                    firstPage=this.searchMap.pageNo-2;
                    lastPage=this.searchMap.pageNo+2;
                    this.preDott=true;
                    this.nextDott=true;

                }
            }else{
                this.preDott=false;
                this.nextDott=false;
            }
            for(let i=firstPage;i<=lastPage;i++){
                this.pageLabels.push(i);
            }
        },


        //点击页码查询
        queryByPage:function (pageNo) {
            pageNo = parseInt(pageNo);
            this.searchMap.pageNo = pageNo;
            this.searchList();
        },

        //搜索点击处理
        clear:function () {
            this.searchMap = {'keywords':this.searchMap.keywords,'category':'','brand':'',spec:{},'price':'','pageNo':1,'pageSize':40,'sortField':'','sortType':''};
        },
        
        //价格排序
        doSort:function (sortFiled,sortType) {
            this.searchMap.sortFiled = sortFiled;
            this.searchMap.sortType = sortType;
            this.searchList();
        }
    },
    //钩子函数 初始化了事件和
    created: function () {
      
        //this.searchList();

    }

})
