var app = new Vue({
    el: "#app",
    data: {
        pages:15,
        pageNo:1,
        list:[],
        entity:{},
        ids:[],
        searchMap:{'keywords':''},//搜索的条件封装对象
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


    },
    //钩子函数 初始化了事件和
    created: function () {
      
        //this.searchList();

    }

})
