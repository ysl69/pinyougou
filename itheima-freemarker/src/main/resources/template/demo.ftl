<html>
<head>
    <meta charset="utf-8">
    <title>Freemarker入门小DEMO </title>
</head>
<body>
<#-- include指令-->
<#include "head.ftl">

<#--我只是一个注释，我不会有任何输出  -->
${name},你好。${message}
<br>

<#--assign指令-->
<#--定义简单类型-->
<#assign linkman="周先生">
联系人：${linkman}
<br>

<#--定义对象类型-->
<#assign info={"mobile":"13301231212",'address':'北京市昌平区王府街'} >
电话：${info.mobile}  地址：${info.address}
<br>


<#-- if指令-->
<#if success==true>
    你已通过实名认证
<#else>
    你未通过实名认证
</#if>
<br>

<#-- list指令-->
<#list goodsList as goods>
    ${goods_index+1} 商品名称： ${goods.name} 价格：${goods.price}<br>
</#list>
<br>

<#--内建函数-->
<#--获取集合大小-->
共  ${goodsList?size}  条记录
<br>

<#--转换JSON字符串为对象-->
<#assign text="{'bank':'工商银行','account':'10101920201920212'}" />
<#assign data=text?eval />
开户行：${data.bank}  账号：${data.account}
<br>

<#--日期格式化-->
当前日期：${today?date} <br>
当前时间：${today?time} <br>
当前日期+时间：${today?datetime} <br>
日期格式化：  ${today?string("yyyy年MM月")}
<br>

<#--数字去除千分位-->
累计积分：${point}
累计积分：${point?c}
<br>

<#--空值处理运算符-->
<#--判断某变量是否存在:“??”-->
<#if aaa??>
    aaa变量存在
<#else>
    aaa变量不存在
</#if>
<br>

<#--缺失变量默认值:“!”-->
${aaa!'-'}
<br>

</body>
</html>