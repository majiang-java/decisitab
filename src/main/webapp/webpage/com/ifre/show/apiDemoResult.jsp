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
				<h3>评级：${map.ranking}</h3>
				<h3>违约概率：${map.defaultRatio}</h3>
				<div id="main" style="height:400px"></div>				
			</c:if>
			<c:if test="${status == false}">
				<h3>接口返回状态：查询失败</h3>
				<h3>返回失败原因：${Msg}</h3>
			</c:if>
		</c:if>
	</div>
</body>
<script src="http://echarts.baidu.com/build/dist/echarts.js"></script>
<script type="text/javascript">
    // 路径配置
    require.config({
        paths: {
            echarts: 'http://echarts.baidu.com/build/dist'
        }
    });
 	// 使用
 	require(
 		['echarts',
 		 'echarts/chart/gauge' // 使用柱状图就加载bar模块，按需加载
 		],
 		function (ec) {
            // 基于准备好的dom，初始化echarts图表
            var myChart = ec.init(document.getElementById('main'),'macarons'); 
            var option = {
           	    tooltip : {
           	        formatter: "评分=${map.totalScore}分<br />违约率=${map.defaultRatio}%"
           	    },
           	    toolbox: {
           	        show : true,
           	        feature : {
           	            mark : {show: false},
           	            restore : {show: false},
           	            saveAsImage : {show: true}
           	        }
           	    },
           	    series : [
           	        {
           	            name: "违约评分",
           	            type:'gauge',
           	         	min:0,
           	         	max:100,
           	         	splitNumber:5,
           	         	axisLine:{
           	         		show: true,
           	         		lineStyle:{
           	         			color:[
           	         				[0.2,'#32C8C9'],
           	         				[0.4,'#98B458'],
           	         				[0.6,'#6FB3ED'],
           	         				[0.8,'#FEB985'],
           	         				[1,'#D77B82']
           	         			],
           	         		}
           	         	},
           	            detail : {formatter:'${map.totalScore}'},
           	            data:[{value: 0, name: '违约率${map.defaultRatio}%'}]
           	        }
           	    ]
           	};
         	// 为echarts对象加载数据
         	var color;
         	//此处截断不合理，需要在返回评分的时候截断，待优化。
         	option.series[0].data[0].value="${map.totalScore<360?360:map.totalScore}";
         	if (1 == 2){
         		color = [
         			[0.25,'#32C8C9'],
         			[0.45,'#98B458'],
         			[0.55,'#6FB3ED'],
         			[0.75,'#FEB985'],
         			[1,'#D77B82']
         		];
         		option.series[0].min=750;
     			option.series[0].max=950;
         	} else {
         		color = [
           			[80/240,'#32C8C9'],
    	         	[115/240,'#98B458'],
    	         	[140/240,'#6FB3ED'],
    	         	[150/240,'#FEB985'],
    	         	[1,'#D77B82']
    	         ];
         		option.series[0].min=360;
         		option.series[0].max=600;
         	}
         	option.series[0].axisLine.lineStyle.color = color;
         	
            myChart.setOption(option); 
 		}
 	);
</script>
</html>