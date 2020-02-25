var app = new Vue({
    el: "#app",
    data: {
        pages:15,
        pageNo:1,
        list:[],
        entity:{},
        smsCode:'',//验证码的值
        repwd:'',//确认密码
        loginName:'',
        ids:[],
        searchEntity:{}
    },
    methods: {
        //注册
        register:function () {
            axios.post('/user/add/'+this.smsCode+'.shtml',this.entity).then(function (response) {
                if(response.data.success){
                    //跳转到登录页面
                    window.location.href="home-index.html";
                }
            }).catch(function (error) {
                console.log("1231312131321");
            });
        },

        //点击a标签的时候 调用方法 发送请求 发送验证码
        createSmsCode:function () {
            axios.get('/user/sendCode.shtml?phone='+this.entity.phone).then(function (response) {
                if (response.data.success){
                    alert(response.data.message);//显示数据
                }else {
                    // 发送失败
                    alert(response.data.message);
                }
            }).catch(function (error) {
                console.log("123123123231");
            })
        },

        formSubmit:function () {
            var that=this;
            this.$validator.validate().then(
                function (result) {
                    if(result){
                        console.log(that);
                        axios.post('/user/add/'+that.smsCode+'.shtml',that.entity).then(function (response) {
                            if(response.data.success){
                                //跳转到其用户后台的首页
                                window.location.href="home-index.html";
                            }else{
                                that.$validator.errors.add(response.data.errorsList);
                            }
                        }).catch(function (error) {
                            console.log("1231312131321");
                        });
                    }
                }
            )
        },


        // 获取登录名
        getName:function () {
            axios.get('/login/name.shtml').then(function (response) {
                app.loginName = response.data;
            }).catch(function (error) {
                console.log("1231312131321");
            })
        }



    },
    //钩子函数 初始化了事件和
    created: function () {
        this.getName();
    }

})
