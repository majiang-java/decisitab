<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<style type="text/css">
body {
	margin-top: 20px;
	margin-left: 20px;
}

</style>
</head>
<body>
	<div>
		<c:if test="${SUCCESS == false}">
			<h3>接口连接状态：连接失败</h3>
			<h3>连接失败原因：${Msg}</h3>
		</c:if>
		<c:if test="${SUCCESS == true}">
			<h3>接口连接状态：连接成功</h3>
			<%-- <h3>接口返回结果：</h3><label>${result}</label> --%>
			<c:if test="${status == true}">
				<h3>接口返回状态：查询成功</h3>
				<h3>查询信息：${map}</h3>
			</c:if>
			<c:if test="${status == false}">
				<h3>接口返回状态：查询失败</h3>
				<h3>返回失败原因：${Msg}</h3>
			</c:if>
		</c:if>
	</div>
</body>
</html>