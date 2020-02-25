# 品优购
基于SOA架构一个综合性的B2B2C平台

### 一、品优购简介

品优购网上商城是一个综合性的 B2B2C 平台，类似京东商城、天猫商城。网站采用商家入驻的模式，商家入驻平台提交申请，有平台进行资质审核，审核通过后，商家拥有独立的管理后台录入商品信息。商品经过平台审核后即可发布。

品优购网上商城主要分为网站前台、运营商后台、商家管理后台三个子系统

### 二、框架组合

品优购采用当前流行的前后端编程架构。

后端框架采用Spring +SpringMVC+mybatis +Dubbo。前端采用Vue + Bootstrap。

### 三、涉及技术

spring springmvc mybatis

myql

dubbo（系统间通信）

rocketmq（消息的中间件）

mycat （数据库要读写分离 分库分表—-数据库集群 mycat）

redis spring data redis

elasticsearch spring data elasticsearch

kindEditor （富文本编辑器）

fastdfs 分布式文件系统（OSS）

阿里大于 (短信服务 )

freemarker（模板类似JSP）

微信支付（支付流程）
spring security（类似shiro）
CAS (单点登录)
httpclient
nginx(负载均衡 反向代理 )

