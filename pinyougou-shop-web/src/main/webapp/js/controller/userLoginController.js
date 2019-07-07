var app=new Vue({
    el:'#app',
    data:{
        username:'A'
    },
    methods:{
        //获取用户登录名
        loadUserName:function () {
            axios.post('/login/getName.shtml').then(function (response) {
                //获取数据
                app.username=response.data;
            });
        }
    },
    created:function () {
        this.loadUserName();
    }
});