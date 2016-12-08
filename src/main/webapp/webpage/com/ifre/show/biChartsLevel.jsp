<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<style type="text/css">
body {
	margin-top: 40px;
}

select{
	min-width: 265px;
	margin-bottom: 20px;
}

label {
	padding: 5px;
}

</style>
</head>
<body>
	<div align="center">
		<label>请选择决策方案</label>
		<select onchange="change(this)">
			<c:forEach items="${prodList}" var="item">
				<option value="${item.prodId}">${item.name}</option>
			</c:forEach>
		</select>
		<div id="level3MonthCharts" style="height: 600px;width: 80%;" ></div>
	</div>
</body>
<script src="js/echarts.min.js"></script>
<script type="text/javascript">
	var myChart = echarts.init(document.getElementById('level3MonthCharts'));
	var tempData = eval('${modelResultListBy3MonthLevelAndEChaet}');
	
	var legendArr = [];
	var seriesArr = [];
	var xAxisDataArr = [];
	
	var month = new Date().getMonth()+1;
	for(var i = 2; i >= 0;i--){
		if(month - i <= 0){
			legendArr.push((month+12-i)+"月");
		}else{
			legendArr.push((month-i)+"月");
		}
	}
	var tempIndex = 0;
	xAxisDataArr = tempData[tempIndex].keyList;
	var valueArr1 = [];
	var valueArr2 = [];
	var valueArr3 = [];
	var seriesDataArr = [];
	for(var j = 0;j<xAxisDataArr.length;j++){
		if(tempData[tempIndex].elementMap1[xAxisDataArr[j]]){
			valueArr1.push(tempData[tempIndex].elementMap1[xAxisDataArr[j]]);
		}else{
			valueArr1.push(0);
		}
		if(tempData[tempIndex].elementMap2[xAxisDataArr[j]]){
			valueArr2.push(tempData[tempIndex].elementMap2[xAxisDataArr[j]]);
		}else{
			valueArr2.push(0);
		}
		if(tempData[tempIndex].elementMap3[xAxisDataArr[j]]){
			valueArr3.push(tempData[tempIndex].elementMap3[xAxisDataArr[j]]);
		}else{
			valueArr3.push(0);
		}
	}
	seriesDataArr.push(valueArr1);
	seriesDataArr.push(valueArr2);
	seriesDataArr.push(valueArr3);

	for (i = 0; i < seriesDataArr.length; i++) {
		var item = {
				name : legendArr[i],
				type : 'bar',
				data : seriesDataArr[i]	
		}
		seriesArr.push(item);
	}
	
	var option = {
			tooltip : {trigger : 'axis',padding: 15},
			legend : {data :legendArr},
			grid : {show : false},
			toolbox : {show : true,feature : {magicType : {show : true,type : [ 'line', 'bar' ]},restore : {show : true},saveAsImage : {show : true}}},
			xAxis : {type : 'category',data : xAxisDataArr},
			yAxis : {type : 'value',axisLabel : {formatter : '{value} 次'}},
			series : seriesArr
		};
	if (option && typeof option === "object") {
		myChart.setOption(option, true);
	}
		
	function change(prodIdThis) {
		var prodId = $(prodIdThis).val();
		myChart.showLoading();
		for(var i = 0;i<tempData.length;i++){
			if(prodId == tempData[i].prodId ){
				xAxisDataArr = [];
				seriesArr = [];
				if(tempData[i].keyList.length > 0){
					xAxisDataArr = tempData[i].keyList;
					var valueArr1 = [];
					var valueArr2 = [];
					var valueArr3 = [];
					var seriesDataArr = [];
					for(var j = 0;j<xAxisDataArr.length;j++){
						if(tempData[i].elementMap1[xAxisDataArr[j]]){
							valueArr1.push(tempData[i].elementMap1[xAxisDataArr[j]]);
						}else{
							valueArr1.push(0);
						}
						if(tempData[i].elementMap2[xAxisDataArr[j]]){
							valueArr2.push(tempData[i].elementMap2[xAxisDataArr[j]]);
						}else{
							valueArr2.push(0);
						}
						if(tempData[i].elementMap3[xAxisDataArr[j]]){
							valueArr3.push(tempData[i].elementMap3[xAxisDataArr[j]]);
						}else{
							valueArr3.push(0);
						}
					}
					seriesDataArr.push(valueArr1);
					seriesDataArr.push(valueArr2);
					seriesDataArr.push(valueArr3);
					for (i = 0; i < seriesDataArr.length; i++) {
						var item = {name : legendArr[i],type : 'bar',data : seriesDataArr[i]}
						seriesArr.push(item);
					}
				}
				var option = {
						tooltip : {trigger : 'axis',padding: 15},
						legend : {data :legendArr},
						grid : {show : false},
						toolbox : {show : true,feature : {magicType : {show : true,type : [ 'line', 'bar' ]},restore : {show : true},saveAsImage : {show : true}}},
						xAxis : {type : 'category',data : xAxisDataArr},
						yAxis : {type : 'value',axisLabel : {formatter : '{value} 次'}},
						series : seriesArr
					};
				console.log("xAxisDataArr = "+xAxisDataArr);
				break;
			}
		}
		myChart.hideLoading();
		if (option && typeof option === "object") {
			myChart.setOption(option, true);
		}
	}	

</script>
</html>
