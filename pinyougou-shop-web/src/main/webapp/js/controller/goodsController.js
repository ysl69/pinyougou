var app = new Vue({
    el: "#app",
    data: {
        pages:15,
        pageNo:1,
        list:[],
        entity:{goods:{},goodsDesc:{itemImages:[]},itemList:[]},
        image_entity:{url:'',color:''},
        itemCat1List:[],  // 一级分类的列表，变量
        itemCat2List:[],  // 二级分类的列表，变量
        itemCat3List:[],  // 三级分类的列表，变量
        ids:[],
        searchEntity:{}
    },
    methods: {
        searchList:function (curPage) {
            axios.post('/goods/search.shtml?pageNo='+curPage,this.searchEntity).then(function (response) {
                //获取数据
                app.list=response.data.list;

                //当前页
                app.pageNo=curPage;
                //总页数
                app.pages=response.data.pages;
            });
        },
        //查询所有品牌列表
        findAll:function () {
            console.log(app);
            axios.get('/goods/findAll.shtml').then(function (response) {
                console.log(response);
                //注意：this 在axios中就不再是 vue实例了。
                app.list=response.data;

            }).catch(function (error) {

            })
        },
         findPage:function () {
            var that = this;
            axios.get('/goods/findPage.shtml',{params:{
                pageNo:this.pageNo
            }}).then(function (response) {
                console.log(app);
                //注意：this 在axios中就不再是 vue实例了。
                app.list=response.data.list;
                app.pageNo=curPage;
                //总页数
                app.pages=response.data.pages;
            }).catch(function (error) {

            })
        },
        //该方法只要不在生命周期的
        add:function () {
            // 获取富文本编辑器中的内容传递给对象
            this.entity.goodsDesc.introduction = editor.html();
            axios.post('/goods/add.shtml',this.entity).then(function (response) {

                console.log(response);
                if(response.data.success){
                   app.entity = {goods: {},goodsDesc: {},itemList: []};
                   // 清空
                    editor.html("");
                }
            }).catch(function (error) {
                console.log("1231312131321");
            });
        },
        update:function () {
            axios.post('/goods/update.shtml',this.entity).then(function (response) {
                console.log(response);
                if(response.data.success){
                    app.searchList(1);
                }
            }).catch(function (error) {
                console.log("1231312131321");
            });
        },
        save:function () {
            if(this.entity.id!=null){
                this.update();
            }else{
                this.add();
            }
        },
        findOne:function (id) {
            axios.get('/goods/findOne/'+id+'.shtml').then(function (response) {
                app.entity=response.data;
            }).catch(function (error) {
                console.log("1231312131321");
            });
        },
        dele:function () {
            axios.post('/goods/delete.shtml',this.ids).then(function (response) {
                console.log(response);
                if(response.data.success){
                    app.searchList(1);
                }
            }).catch(function (error) {
                console.log("1231312131321");
            });
        },

        // 上传图片
        uploadFile:function () {
            var formData = new FormData();
            //参数formData.append('file' 中的file 为表单的参数名  必须和 后台的file一致
            //file.files[0]  中的file 指定的时候页面中的input="file"的id的值 files 指定的是选中的图片所在的文件对象数组，这里只有一个就选中[0]
            formData.append("file",file.files[0]);
            axios({
                url:'http://localhost:9110/upload/uploadFile.shtml',
                data:formData,
                method:'post',
                headers:{
                    'Content-Type':'multipart/form-data'
                },
                // 开启跨域请求携带相关认证信息
                withCredentials:true
            }).then(function (response) {
                if (response.data.success){
                    // 上传成功
                    console.log(this);
                    app.image_entity.url = response.data.message;
                    console.log(JSON.stringify(app.image_entity));
                } else {
                    // 上传失败
                    alert(response.data.message);
                }
            })
        },


        // 添加图片
        addImageEntity:function () {
            this.entity.goodsDesc.itemImages.push(this.image_entity);
        },

        //移除图片
        remove_image_entity:function (index) {
            this.entity.goodsDesc.itemImages.splice(index,1);
        },

        // 一级分类
        findItemCat1List:function () {
            axios.get('/itemCat/findByParentId/0.shtml').then(function (response) {
                app.itemCat1List = response.data;
            }).catch(function (error) {
                console.log(error);
            })
        }

    },
    watch:{
        //entity.goods.category1Id 为要监听变量 ，当发生变化时 触发函数，newval 表示的是新值，oldvalue 表示的是旧值
        'entity.goods.category1Id':function (newvalue,oldvalue) {
            // 赋值为空
            if (newvalue != undefined){
                axios.get('/itemCat/findByParentId/'+newvalue+'.shtml').then(function (response) {
                    app.itemCat2List = response.data;
                }).catch(function (error) {
                    console.log(error);
                })
            }
        },

        'entity.goods.category2Id':function (newvalue,oldvalue) {
            if (newvalue != undefined){
                axios.get('/itemCat/findByParentId/'+newvalue+'.shtml').then(function (response) {
                    app.itemCat3List = response.data;
                }).catch(function (error) {
                    console.log(error);
                })
            }
        },

        'entity.goods.category3Id':function (newvalue, oldvalue) {
            if (newvalue != undefined){
                axios.get('/itemCat/findOne/' + newvalue + '.shtml').then(function (response) {
                    //获取列表数据 三级分类的列表
                    // app.entity.goods.typeTemplateId = response.data.typeId;
                    //第一个参数：需要改变的值的对象变量
                    //第二个参数：需要赋值的属性名
                    //第三个参数：要赋予的值
                    app.$set(app.entity.goods,'typeTemplateId',response.data.typeId);
                    console.log(response.data.typeId);
                    console.log(app.entity.goods.typeTemplateId);
                })
            }
        }
    },
    //钩子函数 初始化了事件和
    created: function () {
        //this.searchList(1);
        this.findItemCat1List();

    }

})
