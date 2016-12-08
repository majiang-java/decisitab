<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<style type="text/css">
table {
	display: table;
	border-collapse: separate;
	border-spacing: 4px;
	border-color: grey;
	padding-left: 20px;
	padding-top: 20px;
}
td{
	min-width: 120px;
}
td input{
	min-width: 260px;
}
td select{
	min-width: 265px;
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
	<form id = "from" action="businessDemoController.do?doRunModel" method="post" onsubmit="return toVaild()">
		<table>
			<tr hidden="hidden">
				<td><label>url：</label></td>
				<td><input class="inputxt" id="url" name="url" /></td>
				<td><label>iFRE提供的决策方案接口url</label></td>
			</tr>
			<tr hidden="hidden">
				<td><label>sysSource：</label></td>
				<td><input class="inputxt" id="sysSource" name="sysSource" value="iFREdemo"/></td>
				<td><label>iFRE提供的系统渠道来源</label></td>
			</tr>
			<tr hidden="hidden">
				<td><label>encryptKey：</label></td>
				<td><input class="inputxt" id="encryptKey" name="encryptKey" value="1112345abcde12345abcde"/></td>
				<td><label>iFRE提供的加密密钥</label></td>
			</tr>
			<tr hidden="hidden">
				<td><label>signKey：</label></td>
				<td><input class="inputxt" id="signKey" name="signKey" value="1234567890"/></td>
				<td><label>iFRE提供的签名秘钥</label></td>
			</tr>
			<tr hidden="hidden">
				<td><label>prodId：</label></td>
				<td><input class="inputxt" id="prodIdShow" name="prodIdShow" /></td>
				<td><label>iFRE提供的决策方案产品编码</label></td>
			</tr>
			<tr hidden="hidden">
				<td><label>modelType：</label></td>
				<td><input class="inputxt" id="modelType" name="modelType" /></td>
				<td><label>iFRE提供的决策表的模型类型</label></td>
			</tr>
			<tr hidden="hidden">
				<td><label class=".label">businessNum：</label></td>
				<td><input class="inputxt" id="businessNum" name="businessNum" /></td>
				<td><label>调用机构自行设置，业务编码</label></td>
			</tr>
			<tr>
				<td><label >选择“决策方案”：</label></td>
				<td>
					<select id = "selectProd" name = "selectProd">
						<option value="">请选择</option>
						<c:forEach items="${prodList}" var="prodItem">
							<option value="${prodItem.id}">${prodItem.name}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
		</table>
		<label id="selectProdShowDate"></label>
		<%-- <table>
			<c:forEach items="${model.modelElement}" var="modelElement">
				<tr>
					<td><label>${modelElement.value}</label></td>
					<td>
						<select id="${modelElement.name}" name="${modelElement.name}" onchange="change(this)">
							<option value="">请选择</option>
							<c:forEach items="${modelElement.modelElementItem}" var="modelElementItem">
		    					<option value="${modelElementItem.codeValue}">${modelElementItem.value}</option>
							</c:forEach>
	    				</select>
					</td>
					<td hidden="hidden"><input id="key" name="key" value="${modelElement.name}"></td>
					<td hidden="hidden"><input id="value" name="value"></td>
					<td hidden="hidden"><input id="label" name="label" value="${modelElement.value}"></td>
				</tr>
			</c:forEach>
		</table> --%>
		<br>
		<button type="submit" >提交</button>
	    
	</form>
</div>
</body>
<script type="text/javascript">
	$(function(){
		var preUrl = window.location.protocol+ "//" +window.location.host;
		document.getElementById('url').value = preUrl +"/saas_deis/shared/brmsShared/api/callModelFire?jsonData=";
		document.getElementById("businessNum").value = new Date().getTime();
	});
	
	//模型指标选择
 	function change(thisSelect){
		$(thisSelect).parent().parent().find("input")[1].value = $(thisSelect).val();
    }
	
    function toVaild(){
    	for(var i=0;i<label.length;i++){
    		if(value[i].value == ""){
	    		alert("请选择“"+label[i].value+"”！");
	    		return false;
    		}
    	}
    	return true;
    }
    
	function selectProdShowDate(data){
		$("#temp").remove();
		document.getElementById("prodIdShow").value = data.prodId;
		document.getElementById("modelType").value = data.modelType;
		if(data){
			var table = "<table id = 'temp'>";
			for (i = 0; i < data.modelElement.length; i++) {
				table = table + "<tr>";
				table = table + "<td><label>"+data.modelElement[i].value+"</label></td>";
				table = table + "<td>";
				table = table + "<select id='"+data.modelElement[i].name+"' name='"+data.modelElement[i].name+"' onchange='change(this)'>";
				table = table + "<option value=''>请选择</option>";
				for (j = 0; j < data.modelElement[i].modelElementItem.length; j++) {
					table = table + "<option value='"+data.modelElement[i].modelElementItem[j].codeValue+"'>"+data.modelElement[i].modelElementItem[j].value+"</option>";
				}
				table = table + "</select>";
				table = table + "</td>";
				table = table + "<td hidden='hidden'><input id='key' name='key' value='"+data.modelElement[i].name+"'></td>";
				table = table + "<td hidden='hidden'><input id='value' name='value'></td>";
				table = table + "<td hidden='hidden'><input id='label' name='label' value='"+data.modelElement[i].value+"'></td>";	
				table = table + "</tr>";
			}
			table = table + "</table>";
			document.getElementById("selectProdShowDate").insertAdjacentHTML("afterEnd",table);
		}
    }
	
	$("#selectProd").change(function() {
		if($("#selectProd").val() == ''){
			$("#temp").remove();
		}else{
			var data = "prodId="+$("#selectProd").val();		    
			$.ajax({
					type : "POST",
					url : "businessDemoController.do?businessDemoAJax",
					//dataType: options.dataType,
					data : data,
					success : function(data) {
						var jsonObj = JSON.parse(data);
						if(jsonObj.success){
							selectProdShowDate(jsonObj.obj);
						}else{
							alert(jsonObj.msg);
						}
					},
					error : function(data) {
						alert("数据获取失败，请刷新！");
						//连接失败暂不处理
						//var result = data.responseText;
						//console.info("result:" + result);
					}
				});
		}

	});
	//当json的key包含双引号时，需要在json外添加小括号，参考文章：http://www.jb51.net/article/79765.htm
	//selectProdShowDate(eval('(${model})'));
	
</script> 
</html>