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
	padding-left:10px;
	padding-right:10px;
}
</style>
</head>
<body>
<div align="center">
	<form action="businessDemoController.do?doRunModelPfk" method="post" onsubmit="return toVaild()">
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
				<td><input class="inputxt" id="prodIdShow" name="prodIdShow" value="4028d0fe5875500d01587576a0750037"/></td>
				<td><label>iFRE提供的决策方案产品编码</label></td>
			</tr>
			<tr hidden="hidden">
				<td><label>businessNum：</label></td>
				<td><input class="inputxt" id="businessNum" name="businessNum" /></td>
				<td><label>调用机构自行设置，业务编码</label></td>
			</tr>
			<tr>
				<td><label>月收入（元）：</label></td>
				<td>
					<select id="monthlyIncome" name="monthlyIncome">
    					<option value="">请选择</option>
						<option value="1">小于等于10001</option>
						<option value="500002">大于500000</option>
						<option value="-99">保密</option>
    				</select>
				</td>
			</tr>
		</table>
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
	
    function toVaild(){
    	var monthlyIncome = document.getElementById("monthlyIncome").value;
        if(monthlyIncome.replace(/(^\s+)|(\s+$)/g,"") == ""){
            alert("请选择“月收入”！");
            return false;
        }
        return true;
    }
	
</script> 
</html>