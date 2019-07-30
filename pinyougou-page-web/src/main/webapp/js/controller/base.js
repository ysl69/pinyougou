
//定义一个插件
let URLComponent={};

URLComponent.install=function (Vue, options) {
    //定义一个全局的方法
    Vue.prototype.getUrlParam = function () {
        var obj={};
        var name,value;
        var str=window.location.href; //取得整个地址栏
        var num=str.indexOf("?");
        str=str.substr(num+1); //取得所有参数   stringvar.substr(start [, length ]

        var arr=str.split("&"); //各个参数放到数组里
        for(var i=0;i < arr.length;i++){
            num=arr[i].indexOf("=");
            if(num>0){
                name=arr[i].substring(0,num);
                value=arr[i].substr(num+1);
                obj[name]=value;
            }
        }
        return obj;
    }
}

Vue.use(URLComponent);