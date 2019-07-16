var app = new Vue({
    el: "#app",
    data: {
        pages:15,
        pageNo:1,
        list:[],
        entity:{goods:{},goodsDesc:{itemImages:[],customAttributeItems:[],specificationItems:[]},itemList:[]},
        image_entity:{url:'',color:''},
        specList:[], //规格列表 包括选项的列表
        itemCat1List:[],  // 一级分类的列表，变量
        itemCat2List:[],  // 二级分类的列表，变量
        itemCat3List:[],  // 三级分类的列表，变量
        brandTextList:[],  // 品牌列表
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
        },

        //当点击复选框的时候调用 并影响变量：entity.goodsDesc.specficationItems的值
        updateChecked:function ($event,specName,specValue) {
            //1 如果有对象
            let searchObject = this.searchObjectByKey(this.entity.goodsDesc.specificationItems,specName,"attributeName");
            if (searchObject != null){
                //searchObject====={"attributeName":"网络制式","attributeValue":["移动3G","移动4G"]}
                //判断 是否是勾选 如果是勾选 添加数据
                if ($event.target.checked){
                    //判断 是否是勾选 如果是勾选 添加数据
                    searchObject.attributeValue.push(specValue);
                }else {
                    //否则 就是移除数据
                    searchObject.attributeValue.splice(searchObject.attributeValue.indexOf(specValue),1);
                    //判断如果数组的长度为0 移除对象
                    if (searchObject.attributeValue.length == 0){
                        this.entity.goodsDesc.specificationItems.splice(this.entity.goodsDesc.specificationItems.indexOf(searchObject),1);
                    }
                }
            }else {
                //2. 如果没有对象
                //直接添加对象即可
                //[{"attributeName":"网络制式","attributeValue":["移动3G","移动4G"]},{"attributeName":"屏幕尺寸","attributeValue":["6寸","5.5寸"]}]
                this.entity.goodsDesc.specificationItems.push({
                    "attributeName":specName,
                    "attributeValue":[specValue]
                })
            }
            
        },


        /**
         *
         * @param list 从该数组中查询[{"attributeName":"网络制式","attributeValue":["移动3G","移动4G"]}]
         * @param specName  指定查询的属性的具体值 比如 网络
         * @param key  指定从哪一个属性名查找  比如：attributeName
         * @returns {*}
         */
        searchObjectByKey:function (list,specName,key) {
            for (var i=0;i<list.length;i++){
                let specificationItem = list[i]; //{"attributeName":"网络制式","attributeValue":["移动3G","移动4G"]}
                if (specificationItem[key] == specName){
                    return specificationItem;
                }
            }
            return null;
        },

        //点击复选框的时候 调用生成 sku列表的的变量
        createLsit:function () {
            //1.定义初始化的值
            this.entity.itemList = [{'spec':{},'price':0,'num':0,'status':'0','isDefault':'0'}];

            //2.循环遍历 specificationItems
            var specificationItems = this.entity.goodsDesc.specificationItems;
            for(var i=0;i<specificationItems.length;i++) {
                //3.获取 规格的名称 和规格选项的值 拼接 返回一个最新的SKU的列表
                var obj = specificationItems[i];
                this.entity.itemList = this.addColum(
                    this.entity.itemList,
                    obj.attributeName,
                    obj.attributeValue);
            }
        },

        /**
         *获取 规格的名称 和规格选项的值 拼接 返回一个最新的SKU的列表 方法
         * @param list
         * @param columnName  网络
         * @param columnValue  [移动3G,移动4G]
         */
        addColum:function (list,columnName,columnValue) {
            var newList = [];
            for (var i = 0; i < list.length; i++) {
                var oldRow = list[i];
                for (var j = 0; j < columnValue.length; j++) {
                    var newRow = JSON.parse(JSON.stringify(oldRow));//深克隆
                    var value = columnValue[j];//移动3G
                    newRow.spec[columnName] = value;
                    newList.push(newRow);
                }
            }
            return newList;
        },

    },
    watch:{
        //entity.goods.category1Id 为要监听变量 ，当发生变化时 触发函数，newval 表示的是新值，oldvalue 表示的是旧值
        'entity.goods.category1Id':function (newvalue,oldvalue) {
            //赋值为空
            this.itemCat3List=[];
            //删除属性回到原始状态
            if(this.entity.goods.id==null ) {
                delete this.entity.goods.category2Id;

                delete this.entity.goods.category3Id;

                delete this.entity.goods.typeTemplateId;

            }

            if (newvalue != undefined){
                axios.get('/itemCat/findByParentId/'+newvalue+'.shtml').then(function (response) {
                    app.itemCat2List = response.data;
                }).catch(function (error) {
                    console.log(error);
                })
            }
        },

        'entity.goods.category2Id':function (newvalue,oldvalue) {
            //删除
            if(this.entity.goods.id==null ) {
                delete this.entity.goods.category3Id;
                delete this.entity.goods.typeTemplateId;
            }

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
        },

        //监听模板的ID 的变化 查询该模板的对象，对象里面有品牌列表数据
        'entity.goods.typeTemplateId':function (newvalue, oldvalue) {
            if (newvalue != undefined){
                axios.get('/typeTemplate/findOne/'+newvalue+'.shtml').then(function (response) {
                    // 获取到的是模板的对象
                    var typeTempalte = response.data;
                    // 品牌列表
                    app.brandTextList = JSON.parse(typeTempalte.brandIds);//[{"id":1,"text":"联想"}]

                    //获取模板的扩展属性的值赋值显示
                    app.entity.goodsDesc.customAttributeItems = JSON.parse(typeTempalte.customAttributeItems);
                })
                //监听模板的变化 根据模板的ID 获取模板的规格的数据拼接成要的格式
                axios.get('/typeTemplate/findSpecList/'+newvalue+'.shtml').then(function (response) {
                    app.specList = response.data;
                })
            }
        },

        //监控变量的变化，如果是已经
        'entity.goods.isEnableSpec':function (newVal,oldVal) {
            //如果是隐藏规格列表 则清除所有数据，展开是再进行选择。
            if(newVal==0){
                this.entity.goodsDesc.specificationItems=[];
                this.entity.itemList=[];
            }
        },

        //监控数据变化 ，如果最后还剩下一个就直接删除
        'entity.itemList':function (newval,oldval) {
            //如果是相同的数据那么直接赋值为空即可
            console.log(JSON.stringify([{spec:{},price:0,num:0,status:'0',isDefault:'0'}])==JSON.stringify(newval));
            if(JSON.stringify([{spec:{},price:0,num:0,status:'0',isDefault:'0'}])==JSON.stringify(newval)){
                this.entity.itemList=[];
             }
        },
    },
    //钩子函数 初始化了事件和
    created: function () {
        //this.searchList(1);
        this.findItemCat1List();

    }

})
