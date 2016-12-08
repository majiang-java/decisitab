<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<style type="text/css">
table {
	margin-top:40px; 
}
td{
	min-width: 120px;
}
td label{
	padding: 5px;
}
button{
	background-color: white;
	padding-bottom: 5px;
	padding-top: 5px;
	padding-left:40px;
	padding-right:40px;
	margin-left:5px;
	margin-right: 5px;
}
</style>
</head>
<body>
	<div align="center">
		<button id="day" name="day" onclick="changeTypeDay()" style="background-color: silver;color: white;">每日</button>
		<button id="month" name="month" onclick="changeTypeMonth()">每月</button>
		<button id="week" name="week" onclick="changeTypeWeek()">每周</button>
		<button id="level" name="level" onclick="changeTypeLevel()">评级分布</button>
		<table id="tableDay" border=1 >
			<tr>
				<td><label>产品ID</label></td>
				<td><label>产品名称</label></td>
				<td><label>查询日期</label></td>
				<td><label>该日查询次数</label></td>
				<td><label>该日查询总分</label></td>
			</tr>
			<c:forEach items="${modelResultListByDay}" var="modelResult">
				<tr>
					<td><label>${modelResult.prodId}</label></td>
					<td><label>${modelResult.prodName}</label></td>
					<td><label>${modelResult.yearNum}-${modelResult.monthNum}-${modelResult.dayNum}</label></td>
					<td><label>${modelResult.num}</label></td>
					<td><label>${modelResult.score}</label></td>
				</tr>
			</c:forEach>
		</table>
		<table id="tableMonth" border=1 hidden="hidden">
			<tr>
				<td><label>产品ID</label></td>
				<td><label>产品名称</label></td>
				<td><label>查询月日期</label></td>
				<td><label>该月查询次数</label></td>
				<td><label>该月查询总分</label></td>
			</tr>
			<c:forEach items="${modelResultListByMonth}" var="modelResult">
				<tr>
					<td><label>${modelResult.prodId}</label></td>
					<td><label>${modelResult.prodName}</label></td>
					<td><label>${modelResult.yearNum}-${modelResult.monthNum}</label></td>
					<td><label>${modelResult.num}</label></td>
					<td><label>${modelResult.score}</label></td>
				</tr>
			</c:forEach>
		</table>
		<table id="tableWeek" border=1 hidden="hidden">
			<tr>
				<td><label>产品ID</label></td>
				<td><label>产品名称</label></td>
				<td><label>查询周日期</label></td>
				<td><label>该周查询次数</label></td>
				<td><label>该周查询总分</label></td>
			</tr>
			<c:forEach items="${modelResultListByWeek}" var="modelResult">
				<tr>
					<td><label>${modelResult.prodId}</label></td>
					<td><label>${modelResult.prodName}</label></td>
					<td><label>${modelResult.yearNum}年第${modelResult.weekNum}周</label></td>
					<td><label>${modelResult.num}</label></td>
					<td><label>${modelResult.score}</label></td>
				</tr>
			</c:forEach>
		</table>
		<table id="tableLevel" border=1 hidden="hidden">
			<tr>
				<td><label>产品ID</label></td>
				<td><label>产品名称</label></td>
				<td><label>查询月日期</label></td>
				<td><label>该月查询次数</label></td>
				<td><label>该月查询评级</label></td>
			</tr>
			<c:forEach items="${modelResultListByLevel}" var="modelResult">
				<tr>
					<td><label>${modelResult.prodId}</label></td>
					<td><label>${modelResult.prodName}</label></td>
					<td><label>${modelResult.yearNum}-${modelResult.monthNum}</label></td>
					<td><label>${modelResult.num}</label></td>
					<td><label>${modelResult.level}</label></td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
<script type="text/javascript">
	
	function changeTypeDay(){
		$('#day').css('color','white').css('background-color','silver');
		$('#level').css('color','black').css('background-color','white');
		$('#month').css('color','black').css('background-color','white');
		$('#week').css('color','black').css('background-color','white');
		$('#tableDay').show();
		$('#tableLevel').hide();
		$('#tableMonth').hide();
		$('#tableWeek').hide();
	}
	
	function changeTypeMonth(){
		$('#month').css('color','white').css('background-color','silver');
		$('#level').css('color','black').css('background-color','white');
		$('#day').css('color','black').css('background-color','white');
		$('#week').css('color','black').css('background-color','white');
		$('#tableMonth').show();
		$('#tableLevel').hide();
		$('#tableDay').hide();
		$('#tableWeek').hide();
	}
	
	function changeTypeWeek(){
		$('#week').css('color','white').css('background-color','silver');
		$('#level').css('color','black').css('background-color','white');
		$('#month').css('color','black').css('background-color','white');
		$('#day').css('color','black').css('background-color','white');
		$('#tableWeek').show();
		$('#tableLevel').hide();
		$('#tableDay').hide();
		$('#tableMonth').hide();
	}
	
	function changeTypeLevel(){
		$('#level').css('color','white').css('background-color','silver');
		$('#day').css('color','black').css('background-color','white');
		$('#week').css('color','black').css('background-color','white');
		$('#month').css('color','black').css('background-color','white');
		$('#tableLevel').show();
		$('#tableDay').hide();
		$('#tableWeek').hide();
		$('#tableMonth').hide();
	}
</script>
</html>