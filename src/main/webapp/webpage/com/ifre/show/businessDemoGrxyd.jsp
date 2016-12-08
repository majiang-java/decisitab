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
	<form action="businessDemoController.do?doRunModelGrxyd" method="post" onsubmit="return toVaild()">
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
				<td><input class="inputxt" id="prodIdShow" name="prodIdShow" value="4028d0fe5875500d01587576dbe50039"/></td>
				<td><label>iFRE提供的决策方案产品编码</label></td>
			</tr>
			<tr hidden="hidden">
				<td><label>businessNum：</label></td>
				<td><input class="inputxt" id="businessNum" name="businessNum" /></td>
				<td><label>调用机构自行设置，业务编码</label></td>
			</tr>
			<tr>
				<td><label>性别：</label></td>
				<td>
					<select id="gender" name="gender">
    					<option value="">请选择</option>
						<option value="1">男</option>
						<option value="2">女</option>
						<option value="-99">保密</option>
    				</select>
				</td>
			</tr>
			<tr>
				<td><label>年龄：</label></td>
				<td>
					<select id="age" name="age">
    					<option value="">请选择</option>
						<option value="1">小于24岁大于21岁</option>
						<option value="2">小于31岁大于等于24岁</option>
						<option value="3">小于41岁大于等于31岁</option>
						<option value="4">小于46岁大于等于41岁</option>
						<option value="5">大于等于46岁</option>
						<option value="-99">保密</option>
    				</select>
				</td>
			</tr>
			<tr>
				<td><label>公开程度：</label></td>
				<td>
					<select id="isOpen" name="isOpen">
    					<option value="">请选择</option>
						<option value="1">可告知</option>
						<option value="2">决不可告知</option>
						<option value="3">无所谓</option>
						<option value="-99">保密</option>
    				</select>
				</td>
			</tr>
			<tr>
				<td><label>居住情况：</label></td>
				<td>
					<select id="liveCase" name="liveCase">
						<option value="">请选择</option>
						<option value="2">自建房、租房、宿舍</option>
						<option value="3">自有商业按揭、自有无按揭购房、自有公积金按揭购房、亲戚住房</option>
    					<option value="1">其他</option>
						<option value="-99">保密</option>
    				</select>
				</td>
			</tr>
			<tr>
				<td><label>有无车产：</label></td>
				<td>
					<select id="car" name="car">
						<option value="">请选择</option>
						<option value="1">有</option>
						<option value="0">无</option>
						<option value="-99">保密</option>
    				</select>
				</td>
			</tr>
			<tr>
				<td><label>入职时间：</label></td>
				<td>
					<select id="compJoinYear" name="compJoinYear">
						<option value="">请选择</option>
						<option value="1">小于3个月</option>
						<option value="2">大于等于3个月小于9个月</option>
						<option value="3">大于等于9个月小于19个月</option>
						<option value="4">大于等于19个月小于23个月</option>
						<option value="5">大于等于23个月</option>
						<option value="-99">保密</option>
    				</select>
				</td>
			</tr>
			<tr>
				<td><label>是否本市户籍：</label></td>
				<td>
					<select id="isNative" name="isNative">
						<option value="">请选择</option>
						<option value="1">是</option>
						<option value="0">否</option>
						<option value="-99">保密</option>
    				</select>
				</td>
			</tr>
			<tr>
				<td><label>信用卡账户数：</label></td>
				<td>
					<select id="creditCardAccount" name="creditCardAccount">
						<option value="">请选择</option>
						<option value="1">1张</option>
						<option value="2">2张</option>
						<option value="4">3-4张</option>
						<option value="5">5张及以上</option>
						<option value="-99">保密</option>
    				</select>
				</td>
			</tr>
			<tr>
				<td><label>近一个月查询次数：</label></td>
				<td>
					<select id="queryTimesOne" name="queryTimesOne">
						<option value="">请选择</option>
						<option value="1">没有查询过</option>
						<option value="2">1-2次</option>
						<option value="3">3-5次</option>
						<option value="4">6次及以上</option>
						<option value="-99">保密</option>
    				</select>
				</td>
			</tr>
			<tr>
				<td><label>月均流水</label></td>
				<td><input class="inputxt"  id="monthlyPay" name="monthlyPay" placeholder="请输入数字" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')" /></td>
				<td><label>保密请输入0</label></td>
			</tr>
			<tr>
				<td><label>月供金额</label></td>
				<td><input class="inputxt"  id="monthlyPayment" name="monthlyPayment" placeholder="请输入数字" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')" /></td>
				<td><label>保密请输入0</label></td>
			</tr>
			<tr hidden="hidden">
				<td><label>衍生变量rate=月均流水/月供金额：</label></td>
				<td>
					<select id="rate" name="rate">
						<option value="">请选择</option>
						<option value="1">大于等于0小于等于1</option>
						<option value="2">大于1小于等于2</option>
						<option value="3">大于2小于等于3</option>
						<option value="4">大于3小于等于7</option>
						<option value="5">大于7</option>
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
    	var gender = document.getElementById("gender").value;
        if(gender.replace(/(^\s+)|(\s+$)/g,"") == ""){
            alert("请选择“性别”！");
            return false;
        }
        var age = document.getElementById("age").value;
        if(age.replace(/(^\s+)|(\s+$)/g,"") == ""){
            alert("请选择“年龄”！");
            return false;
        }
        var isOpen = document.getElementById("isOpen").value;
        if(isOpen.replace(/(^\s+)|(\s+$)/g,"") == ""){
            alert("请选择“公开程度”！");
            return false;
        }
        var liveCase = document.getElementById("liveCase").value;
        if(liveCase.replace(/(^\s+)|(\s+$)/g,"") == ""){
            alert("请选择“居住情况”！");
            return false;
        }
        var car = document.getElementById("car").value;
        if(car.replace(/(^\s+)|(\s+$)/g,"") == ""){
            alert("请选择“有无车产”！");
            return false;
        }
        var compJoinYear = document.getElementById("compJoinYear").value;
        if(compJoinYear.replace(/(^\s+)|(\s+$)/g,"") == ""){
            alert("请选择“入职时间”！");
            return false;
        }
        var isNative = document.getElementById("isNative").value;
        if(isNative.replace(/(^\s+)|(\s+$)/g,"") == ""){
            alert("请选择“是否本市户籍”！");
            return false;
        }
        var creditCardAccount = document.getElementById("creditCardAccount").value;
        if(creditCardAccount.replace(/(^\s+)|(\s+$)/g,"") == ""){
            alert("请选择“信用卡账户数”！");
            return false;
        }
        var queryTimesOne = document.getElementById("queryTimesOne").value;
        if(queryTimesOne.replace(/(^\s+)|(\s+$)/g,"") == ""){
            alert("请选择“近一个月查询次数”！");
            return false;
        }
        var monthlyPay = document.getElementById("monthlyPay").value;
        if(monthlyPay.replace(/(^\s+)|(\s+$)/g,"") == ""){
            alert("请输入“月流水”！");
            return false;
        }
        var monthlyPayment = document.getElementById("monthlyPayment").value;
        if(monthlyPayment.replace(/(^\s+)|(\s+$)/g,"") == ""){
            alert("请输入“月供金额”！");
            return false;
        }
        /* var rate = document.getElementById("rate").value;
        if(rate.replace(/(^\s+)|(\s+$)/g,"") == ""){
            alert("请选择“衍生变量rate”！");
            return false;
        } */
        return true;
    }
	
</script> 
</html>