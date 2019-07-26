var app = new Vue({
    el: "#app",
    data: {
        pages:15,
        pageNo:1,
        list:[],
        entity:{},
        ids:[],
        searchMap:{'keywords':'','category':'','brand':'',spec:{},'price':''},//搜索的条件封装对象
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
        }

    },
    //钩子函数 初始化了事件和
    created: function () {
      
        //this.searchList();

    }

})
