<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<body>
	<h1>客户列表界面</h1>

	<table>
		<tr>
			<td>ID</td>
			<td>名称</td>
			<td>联系人</td>
			<td>电话</td>
			<td>邮箱</td>
			<td>备注</td>
			<td>操作</td>
		</tr>
		<c:forEach items="${cs}" var="customer">
			<tr>
				<td>${customer.id}</td>
				<td>${customer.name}</td>
				<td>${customer.contact}</td>
				<td>${customer.telphone}</td>
				<td>${customer.email}</td>
				<td>${customer.remark}</td>
				<td><a href="customer/edit">编辑</a>  |  <a href="customer/delete">删除</a></td>
			</tr>
		</c:forEach>
	</table>

</body>
</html>
