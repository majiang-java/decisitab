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
}
td{
	min-width: 170px;
}
td input{
	min-width: 260px;
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
	<form action="apiDemoController.do?doRunModel" method="post" onsubmit="return toVaild()">
		<table>
			<tr>
				<td><label>url：</label></td>
				<td><input class="inputxt" id="url" name="url" value="http://localhost:8080/saas_brms/shared/brmsShared/api/callModelFire?jsonData=" /></td>
				<td><label>iFRE提供的决策方案接口url</label></td>
			</tr>
			<tr>
				<td><label>sysSource：</label></td>
				<td><input class="inputxt" id="sysSource" name="sysSource" value="sys_test"/></td>
				<td><label>iFRE提供的系统渠道来源</label></td>
			</tr>
			<tr>
				<td><label>encryptKey：</label></td>
				<td><input class="inputxt" id="encryptKey" name="encryptKey" value="1112345abcde12345abcde"/></td>
				<td><label>iFRE提供的加密密钥</label></td>
			</tr>
			<tr>
				<td><label>signKey：</label></td>
				<td><input class="inputxt" id="signKey" name="signKey" value="1234567890"/></td>
				<td><label>iFRE提供的签名秘钥</label></td>
			</tr>
			<tr>
				<td><label>prodId：</label></td>
				<td><input class="inputxt" id="prodIdShow" name="prodIdShow" placeholder="请输入待测试决策方案产品编码" value="8a51b49a5848299601584c5441120714"/></td>
				<td><label>iFRE提供的决策方案产品编码</label></td>
			</tr>
			<tr>
				<td><label>businessNum：</label></td>
				<td><input class="inputxt" id="businessNum" name="businessNum" /></td>
				<td><label>调用机构自行设置，业务编码</label></td>
			</tr>
			<tr id="parameters"><td>模型变量list</td></tr>
			<tr>
				<td><label>模型/规则变量名：</label></td>
				<td><input class="inputxt" id="key" name="key" placeholder="模型/规则变量名" value="age"/></td>
				<td><label>模型/规则变量名对应值：</label></td>
				<td><input class="inputxt" id="value" name="value" placeholder="模型/规则变量值" value="30"/></td>
				<td><label onclick="del(this)">删除</label></td>
			</tr>
			<tr>
				<td colspan="4"></td><td ><label onclick="add()">添加</label></td>
			</tr>
		</table>
		<br>
		<div align="center"><button type="submit" >提交</button></div>
	    
	</form>
	<table style="display: none">
		<tbody id="add_template">
			<tr>
				<td><label>模型/规则变量名：</label></td>
				<td><input class="inputxt" id="key" name="key" placeholder="模型/规则变量名"/></td>
				<td><label>模型/规则变量名对应值：</label></td>
				<td><input class="inputxt" id="value" name="value" placeholder="模型/规则变量值"/></td>
				<td><label onclick="del(this)">删除</label></td>
			</tr>
		</tbody>
	</table>
</body>
<script type="text/javascript">
	$(function(){
		document.getElementById('url').value = window.location.host+"/saas_deis/shared/brmsShared/api/callModelFire?jsonData=";
		document.getElementById("businessNum").value = new Date().getTime();
		$("#choosearea").citySelect({
			url: "apiDemoController.do?getDepartProdList&prodType=${prodType}",
			prov:"",
			city:"",
			dist:"",
			nodata:"none"
			});
	});
	
	function add(){
		//亲测有问题
		//var tr = $("#add_template tr").clone();
		//$("#add_objProp_table").append(tr);
		//亲测可用
		var tr = "<tr><td><label>模型/规则变量名：</label></td><td><input class=\"inputxt\" id=\"key\" name=\"key\" placeholder=\"模型\/规则变量名\"/></td><td><label>模型/规则变量名对应值：</label></td><td><input class=\"inputxt\" id=\"value\" name=\"value\" placeholder=\"模型\/规则变量值\"/></td><td><label onclick=\"del(this)\">删除</label></td></tr>";
		document.getElementById("parameters").insertAdjacentHTML("afterEnd",tr);
		
	}
	
	function del(delThis){
		$.dialog.confirm("确认删除该参数?", function(){
            $(delThis).parent().parent().remove();
		}, function(){
			//
		});
	}
	
    function toVaild(){
        var url = document.getElementById("url").value;
        if(url.replace(/(^\s+)|(\s+$)/g,"") == ""){
            alert("请输入测试url！");
            return false;
        }
        var sysSource = document.getElementById("sysSource").value;
        if(sysSource.replace(/(^\s+)|(\s+$)/g,"") == ""){
            alert("请输入测试sysSource！");
            return false;
        }
        var prodIdShow = document.getElementById("prodIdShow").value;
        if(prodIdShow.replace(/(^\s+)|(\s+$)/g,"") == ""){
            alert("请输入测试prodId！");
            return false;
        }
        var businessNum = document.getElementById("businessNum").value;
        if(businessNum.replace(/(^\s+)|(\s+$)/g,"") == ""){
            alert("请输入测试businessNum！");
            return false;
        }
        return true;
    }
	
</script> 
</html>