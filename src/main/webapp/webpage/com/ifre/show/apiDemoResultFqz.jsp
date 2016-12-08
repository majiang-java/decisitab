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
label{
	padding: 5px;
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
				<h3>评级：${map.riskRanking}</h3>
				<h3>最终建议：${map.finalProposal}</h3>
				<br>
				<label>最高分分值：${map.firstValue}</label>
				<label>最高分建议：${map.firstProposal}</label>
				<br>
				<label>次高分分值：${map.secondValue}</label>
				<label>次高分建议：${map.secondProposal}</label>
				<br>
				<label>第三高分分值：${map.thirdValue}</label>
				<label>第三高分建议：${map.thirdProposal}</label>
				<br>				
				<label>第一代码：${map.firstCode}</label>
				<label>第二代码：${map.secondCode}</label>
				<label>第三代码：${map.thirdCode}</label>
				<br>
				<label>第一单个分值：${map.firstSingleValue}</label>
				<label>第二单个分值：${map.secondSingleValue}</label>
				<label>第三单个分值：${map.thirdSingleValue}</label>
				
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
            var myChart = ec.init(document.getElementById('main')); 
            var option = {
           	    tooltip : {
           	        formatter: "{a} <br/>{b} : {c}分"
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
           	            name:'反欺诈指标',
           	            type:'gauge',
           	            startAngle: 180,
           	            endAngle: 0,
           	            center : ['50%', '90%'],    // 默认全局居中
           	            radius : 320,
           	        	min:0,
     	         		max:200,
           	            axisLine: {            // 坐标轴线
           	                lineStyle: {       // 属性lineStyle控制线条样式
           	                    width: 200,
           	                    color:[
									[0.3,'#6FB3ED'],
									[0.5,'#FEB985'],
									[1,'#D77B82']
           	                    ]
           	                }
           	            },
           	            axisTick: {            // 坐标轴小标记
           	                splitNumber: 0,   // 每份split细分多少段
           	                length :12,        // 属性length控制线长
           	            },
           	            axisLabel: {           // 坐标轴文本标签，详见axis.axisLabel
           	                formatter: function(v){
           	                    switch (v+''){
           	                        case '60': return '低风险';
           	                        case '100': return '高风险';
           	                        case '200': return '欺诈';
           	                        default: return '';
           	                    }
           	                },
           	                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
           	                    color: '#fff',
           	                    fontSize: 15,
           	                    fontWeight: 'bolder'
           	                }
           	            },
           	            pointer: {
           	                width:50,
           	                length: '90%',
           	                color: 'rgba(255, 255, 255, 0.8)'
           	            },
           	            title : {
           	                show : true,
           	                offsetCenter: [0, '-60%'],       // x, y，单位px
           	                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
           	                    color: '#fff',
           	                    fontSize: 30
           	                }
           	            },
           	            detail : {
           	                show : true,
           	                backgroundColor: 'rgba(0,0,0,0)',
           	                borderWidth: 0,
           	                borderColor: '#ccc',
           	                width: 100,
           	                height: 40,
           	                offsetCenter: [0, -40],       // x, y，单位px
           	                formatter:'${map.totalScore}',
           	                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
           	                    fontSize : 50
           	                }
           	            },
           	            data:[{value:"${map.totalScore}", name: '反欺诈评分'}]
           	        }
           	    ]
           	};
         	// 为echarts对象加载数据 
         	if ("${map.totalScore}" > 200) {
         		option.series[0].data[0].value=200;
         	} 
            myChart.setOption(option); 
 		}
 	);
</script>
</html>