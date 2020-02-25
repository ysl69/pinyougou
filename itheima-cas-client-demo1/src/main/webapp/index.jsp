<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<body>

<h2>Hello World!</h2>
<%--获取用户名--%>
<h2>欢迎<%=request.getRemoteUser()%>来到cas客户端1</h2>
<a href="http://localhost:9400/cas/logout">退出登录</a>
</body>
</html>
