var app = new Vue({
    el:"#app",
    data:{
        list:[],  //数组  [{id,name,firstchar},{},{}]
        entity:{},  //品牌对象
        ids:[],//存储要删除的品牌的id列表
        searchEntity:{},//搜索条件对象
        pages:15, // 总页数
        pageNo:1, //当前页码
    },
    methods:{
        //查询所有品牌列表
        findAll:function () {
            //发送请求 获取列表数据 赋值给变量
            axios.get('/brand/findAll.shtml').then(
                function (response) {  //response.data= list
                    console.log(response);
                    app.list = response.data
                }
            ).catch(function (error) {
                // 请求失败处理
                console.log(error);
            })
        },

        // 分页
        //第一：页码加载的就应该被调用默认查询第一页数据
        //第二：当点击下一页的时候 也被调用
        //发送请求 获取分页的数据 赋值给变量
        searchList:function (curPage) {
            axios.post('/brand/search.shtml?pageNo='+curPage,this.searchEntity).then(
                function (response) {//response.data=pageinfo
                    //当前页
                    //app.pageNo = response.data.pageNum;
                    app.pageNo = curPage;
                    //总页数
                    app.pages = response.data.pages;
                    //获取数据
                    app.list = response.data.list;
                }
            )

           /* axios.post('/brand/findPage.shtml?pageNo='+curPage).then(
                function (response) {//response.data=pageinfo
                    // 获取数据
                    app.list = response.data.list;
                    // 当前页
                    app.pageNo = response.data.pageNum;
                    // 总页数
                    app.pages = response.data.pages;
                })*/
        },


        //添加品牌
        add:function () {
            axios.post('/brand/add.shtml',this.entity).then(
                function (response) {//response.data=result
                    console.log(response);
                    if (response.data.success){
                        //刷新页面
                        app.searchList(1);
                    }
                }
            ).catch(function (error) {
                console.log(error);
            })
        },


        //当点击修改的时候 根据点击到的品牌的ID 发送请求 获取数据赋值给变量entity
        findOne:function (id) {
            axios.get('/brand/findOne/'+id+'.shtml').then(
                function (response) {//response.data=tbbrand
                    app.entity = response.data;
                }).catch(function (error) {
                console.log(error);
            })
        },

        //更新品牌  当点击保存的时候调用
        update:function () {
            axios.post('/brand/update.shtml',this.entity).then(
                function (response) {//response.data=result
                    console.log(response);
                    if (response.data.success){
                        //刷新页面
                        app.searchList(1);
                    }
                }).catch(function (error) {
                console.log(error);
            })
        },

        // 保存
        save:function () {
            if (this.entity.id==null || this.entity.id==undefined ) {
                this.add();
            }else {
                // 更新
                this.update();
            }
        },


        // 删除
        dele:function () {
            axios.post('/brand/delete.shtml',this.ids).then(
                function (response) {
                    console.log(response);
                    if (response.data.success){
                        app.searchList(1);
                    }
                }).catch(function (error) {
                    console.log(error);
            })
        }

    },
    // 钩子函数
    created:function () {
        //this.findAll();
        this.searchList(1);
    }
})