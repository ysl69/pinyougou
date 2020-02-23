//别名配置
var aliasName={"username":"用户名","password":"密码","repwd":"确认密码","phone":"手机号","smsCode":"验证码"};
//别名更换
function _alias(n) {
    var aname = aliasName[n];
    if(aname!=null){
        return aname;
    }
    return n;
}

!function (n, e) {
    "object" == typeof exports && "undefined" != typeof module ? module.exports = e() : "function" == typeof define && define.amd ? define(e) : ((n = n || self).__vee_validate_locale__zh_CN = n.__vee_validate_locale__zh_CN || {}, n.__vee_validate_locale__zh_CN.js = e())
}(this, function () {
    "use strict";
    var n, e = {
        name: "zh_CN", messages: {
            _default: function (n) {
                return _alias(n) + "的值无效"
            }, after: function (n, e) {
                var t = e[0];
                return _alias(n) + "必须在" + t + "之后" + (e[1] ? "或等于" + t : "")
            }, alpha: function (n) {
                return _alias(n) + "只能包含字母字符"
            }, alpha_dash: function (n) {
                return _alias(n) + "能够包含字母数字字符、破折号和下划线"
            }, alpha_num: function (n) {
                //n=aliasName[n];
                return _alias(n) + "只能包含字母数字字符"
            }, alpha_spaces: function (n) {
                return _alias(n) + "只能包含字母字符和空格"
            }, before: function (n, e) {
                var t = e[0];
                return _alias(n) + "必须在" + t + "之前" + (e[1] ? "或等于" + t : "")
            }, between: function (n, e) {
                return _alias(n) + "必须在" + e[0] + "与" + e[1] + "之间"
            }, confirmed: function (n, e) {
                //return "不能和" + e[0] + "匹配"
                return "2次" + _alias(n) + "不匹配"
            }, credit_card: function (n) {
                return _alias(n) + "的格式错误"
            }, date_between: function (n, e) {
                return _alias(n) + "必须在" + e[0] + "和" + e[1] + "之间"
            }, date_format: function (n, e) {
                return _alias(n) + "必须符合" + e[0] + "格式"
            }, decimal: function (n, e) {
                void 0 === e && (e = []);
                var t = e[0];
                return void 0 === t && (t = "*"), _alias(n) + "必须是数字，且能够保留" + ("*" === t ? "" : t) + "位小数"
            }, digits: function (n, e) {
                return _alias(n) + "必须是数字，且精确到" + e[0] + "位数"
            }, dimensions: function (n, e) {
                return _alias(n) + "必须在" + e[0] + "像素与" + e[1] + "像素之间"
            }, email: function (n) {
                return _alias(n) + "不是一个有效的邮箱"
            }, excluded: function (n) {
                return _alias(n) + "不是一个有效值"
            }, ext: function (n) {
                return _alias(n) + "不是一个有效的文件"
            }, image: function (n) {
                return _alias(n) + "不是一张有效的图片"
            }, included: function (n) {
                return _alias(n) + "不是一个有效值"
            }, integer: function (n) {
                return n + "必须是整数"
            }, ip: function (n) {
                return _alias(n) + "不是一个有效的地址"
            }, length: function (n, e) {
                var t = e[0], r = e[1];
                return r ? _alias(n) + "长度必须在" + t + "到" + r + "之间" : _alias(n) + "长度必须为" + t
            }, max: function (n, e) {
                return _alias(n) + "不能超过" + e[0] + "个字符"
            }, max_value: function (n, e) {
                return _alias(n) + "必须小于或等于" + e[0]
            }, mimes: function (n) {
                return _alias(n) + "不是一个有效的文件类型"
            }, min: function (n, e) {
                return _alias(n) + "必须至少有" + e[0] + "个字符"
            }, min_value: function (n, e) {
                return _alias(n) + "必须大于或等于" + e[0]
            }, numeric: function (n) {
                return _alias(n) + "只能包含数字字符"
            }, regex: function (n) {
                return _alias(n) + "格式无效"
            }, required: function (n) {
                return _alias(n) + "是必须的"
            }, size: function (n, e) {
                var t, r, u, i = e[0];
                return _alias(n) + "必须小于" + (t = i, r = 1024, u = 0 == (t = Number(t) * r) ? 0 : Math.floor(Math.log(t) / Math.log(r)), 1 * (t / Math.pow(r, u)).toFixed(2) + " " + ["Byte", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB"][u])
            }, url: function (n) {
                return _alias(n) + "不是一个有效的url"
            }
        }, attributes: {}
    };
    return "undefined" != typeof VeeValidate && VeeValidate.Validator.localize(((n = {})[e.name] = e, n)), e
});