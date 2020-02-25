var app = new Vue({
    el:"#app",
    data:{
        list:[], // 数组
        entity:{},  //品牌对象
        ids:[],//存储要删除的品牌的id列表
        searchEntity:{},//搜索条件对象
        pages:15,// 总页数
        pageNo:1,// 当前页码
    },
    methods: {
        searchList: function (curPage) {
            /*axios.post('/brand/findPage.shtml?pageNo=' + curPage).then(function (response) {
                // 获取数据
                app.list = response.data.list;

                // 当前页
                app.pageNo = curPage;
                // 总页数
                app.pages = response.data.pages;
            });*/


            //第一：页码加载的就应该被调用默认查询第一页数据
            //第二：当点击下一页的时候 也被调用
            //发送请求 获取分页的数据 赋值给变量
            axios.post('/brand/search.shtml?pageNo='+curPage,this.searchEntity).then(
                function (response) {//response.data=pageinfo
                    app.pageNo = response.data.pageNum;
                    app.pages=response.data.pages;
                    app.list=response.data.list;
                }
            )
        },

        // 查询所有品牌列表
        findAll: function () {
            console.log(app);
            axios.get('/brand/findAll.shtml').then(function (response) {
                console.log(response);
                //注意：this 在axios中就不再是 vue实例了。
                app.list = response.data;
            }).catch(function (error) {

            })
        },


        //该方法只要不在生命周期
        add: function () {
            axios.post('/brand/add.shtml', this.entity).then(function (response) {
                console.log(response);
                if (response.data.success) {
                    //app.findPage();
                    app.searchList(1);
                }
            }).catch(function (error) {
                console.log("1231312131321")
            })
        },


        // 按id查询
        findOne:function (id) {
            axios.get('/brand/findOne/'+id+'.shtml').then(function (response) {
                app.entity = response.data;
            }).catch(function (error) {
                console.log("123123123123");
            })
        },
        

        // 更新
        update:function () {
            axios.post('/brand/update.shtml',this.entity).then(function (response) {
                console.log(response);
                if (response.data.success){
                    app.searchList(1);
                }
            }).catch(function (error) {
                console.log("1231312131321");
            })
        },

        // 保存
        save:function () {
            if (this.entity.id != null){
                this.update();
            } else {
                this.add();
            }
        },


        dele:function () {
          axios.post('/brand/delete.shtml',this.ids).then(
              function (response) {  // response.data = result
              console.log(response);
              if (response.data.success){
                  app.ids = [];  // 清空
                  app.searchList(1);
              }
          }).catch(function (error) {
              console.log("123123123132231")
          })
        },

    },

    // 钩子函数
    created:function(){
        //this.findAll();
        this.searchList(1);
    }
})