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
</style>
</head>
<body>
	<div align="center">
		<c:if test="${msg != null}">
			<label>图表展示失败，原因：${msg}</label>
		</c:if>
		<div id="numMonthCharts" style="height: 600px"></div>
	</div>
</body>
<script src="http://echarts.baidu.com/build/dist/echarts.js"></script>
<script type="text/javascript">
	// 路径配置
	require.config({
		paths : {
			echarts : 'http://echarts.baidu.com/build/dist'
		}
	});
	// 使用
	require([ 'echarts', 'echarts/chart/line', 'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载
	], function(ec) {
		// 基于准备好的dom，初始化echarts图表
		var myChart = ec.init(document.getElementById('numMonthCharts'));
		var legendArr = [];
		var seriesArr = [];
		var xAxisDataArr = eval('${modelResultListByLevelAndEChaet}');
		var tempData = eval('${modelResultListByMonthAndEChaet}');
		var seriesDataArr = [];
		for(var i = 0;i<tempData.length;i++){
			legendArr.push((i+1)+tempData[i].name);
			var valueArr = [];
			for(var j = 0;j<xAxisDataArr.length;j++){
				if(tempData[i].elementMap[xAxisDataArr[j]]){
					valueArr.push(tempData[i].elementMap[xAxisDataArr[j]]);
				}else{
					valueArr.push(0);
				}
			}
			seriesDataArr.push(valueArr);
		}
		for (i = 0; i < seriesDataArr.length; i++) {
			var item = {
					name : legendArr[i],
					type : 'line',
					data : seriesDataArr[i]	
			}
			seriesArr.push(item);
		}
		var option = {
				tooltip : {trigger : 'axis'},
				legend : {data :legendArr},
				grid : {show : false},
				toolbox : {show : true,feature : {magicType : {show : true,type : [ 'line', 'bar' ]},restore : {show : true},saveAsImage : {show : true}}},
				xAxis : {type : 'category',boundaryGap : false,data : xAxisDataArr},
				yAxis : {type : 'value',axisLabel : {formatter : '{value} 次'}},
				series : seriesArr
			};
		// 为echarts对象加载数据 
		myChart.setOption(option);
	});

</script>
</html>
