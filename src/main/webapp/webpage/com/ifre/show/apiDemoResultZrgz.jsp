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
				<h3>总分：${map.totalScore}</h3>
				<h3>状态：${map.finalProposal}</h3>
				<c:if test="${map.finalProposal == '拒绝'}">
					<h3>拒绝原因：${map.rejectReason}</h3>
				</c:if>
				<div id="main" style="height:400px"></div>				
			</c:if>
			<c:if test="${status == false}">
				<h3>接口返回状态：查询失败</h3>
				<h3>返回失败原因：${Msg}</h3>
			</c:if>
		</c:if>
	</div>
</body>

<script src="js/echarts.min.js"></script>
<script type="text/javascript">
	var myChart = echarts.init(document.getElementById('main'));
	var value = '${map.finalProposal}';
	//value = '通过';
	var passTestColor = '#bb0088';
    var passDataColor =  '#2211ff';
    //passDataColor = '#30d61c';
    var refuseTestColor = '#FE0401';
    var refuseDataColor1 = '#F20205';
    var refuseDataColor2 = '#F8CACC';
    var testColor;
    var dataColor1;
    var dataColor2;
    if(value == '通过'){
    	testColor = passTestColor;
    	dataColor1 = passDataColor;
    	dataColor2 = passDataColor;
    }else{
    	value = '拒绝';
    	testColor = refuseTestColor;
    	dataColor1 = refuseDataColor1;
    	dataColor2 = refuseDataColor2;
    }
	var option = {
			title : {text : value,x : 'center',y : 'center',textStyle : {color : testColor,fontWeight : 'bolder',fontSize : 64}},
			series : [ {
				type : 'pie',radius : [ '35%', '38%' ],
				data : [ {itemStyle : {normal : {color : dataColor1}}}]
			}, {
				type : 'pie',radius : [ '30%', '31%' ],
				data : [ {itemStyle : {normal : {color : dataColor2}}}]
			} ]
		};
	
	myChart.setOption(option);
</script>
</html>