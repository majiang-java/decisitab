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
	min-width: 120px;
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
<div align="center">
<div style="max-width: 800px" align="left">
	<form action="businessDemoController.do?doRunFqz" method="post" onsubmit="return toVaild()">
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
				<td><input class="inputxt" id="prodIdShow" name="prodIdShow" value="4028d0fe5875500d0158757669670035"/></td>
				<td><label>iFRE提供的决策方案产品编码</label></td>
			</tr>
			<tr hidden="hidden">
				<td><label>businessNum：</label></td>
				<td><input class="inputxt" id="businessNum" name="businessNum" /></td>
				<td><label>调用机构自行设置，业务编码</label></td>
			</tr>
			<tr id="parameters" ><td colspan="4">反欺诈验证</td></tr>
			<tr>
				<td><label>姓名：</label></td>
				<td><input class="inputxt" id="name" name="name" /></td>
				<td><label></label></td>
			</tr>
			<tr>
				<td><label>身份证号：</label></td>
				<td><input class="inputxt" id="idCard" name="idCard" /></td>
				<td><label></label></td>
			</tr>
			<tr>
				<td><label>手机号码：</label></td>
				<td><input class="inputxt" id="mobile" name="mobile" /></td>
				<td><label></label></td>
			</tr>
			<tr>
				<td><label>公司固话：</label></td>
				<td><input class="inputxt" id="companyTel" name="companyTel" /></td>
				<td><label></label></td>
			</tr>
		</table>
		<table>
			<tr><td>联系人list</td></tr>
			<tr>
				<td><label>联系人姓名</label></td>
				<td><label>联系人手机号</label></td>
				<td><label>操作</label></td>
			</tr>
			<tr id="linkmans"></tr>
			<tr>
				<td><input class="inputxt" id="linkmanNames" name="linkmanNames" /></td>
				<td><input class="inputxt" id="linkmanMobiles" name="linkmanMobiles" /></td>
				<td><label onclick="del(this)">删除</label></td>
			</tr>
			<tr>
				<td colspan="2"></td><td ><label onclick="add()">添加</label></td>
			</tr>
		</table>
		<br>
		<div align="center"><button type="submit" >提交</button></div>
	    
	</form>
</div>
</div>
</body>
<script type="text/javascript">
	$(function(){
		var preUrl = window.location.protocol+ "//" +window.location.host;
		document.getElementById('url').value = preUrl +"/saas_deis/shared/brmsShared/api/callModelFqz?jsonData=";
		document.getElementById("businessNum").value = new Date().getTime();
	});
	
	function add(){
		//亲测可用
		var tr = "<tr><td><input class=\"inputxt\" id=\"linkmanNames\" name=\"linkmanNames\" /></td><td><input class=\"inputxt\" id=\"linkmanMobiles\" name=\"linkmanMobiles\" /></td><td><label onclick=\"del(this)\">删除</label></td></tr>";
		document.getElementById("linkmans").insertAdjacentHTML("afterEnd",tr);
		
	}
	
	function del(delThis){
		$.dialog.confirm("确认删除该联系人?", function(){
            $(delThis).parent().parent().remove();
		}, function(){
			//
		});
	}
	
    function toVaild(){
        var prodIdShow = document.getElementById("prodIdShow").value;
        if(prodIdShow.replace(/(^\s+)|(\s+$)/g,"") == ""){
            alert("请输入测试prodId！");
            return false;
        }
        return true;
    }
</script> 
</html>