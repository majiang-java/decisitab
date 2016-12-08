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
	<form action="businessDemoController.do?doRunModelYz16" method="post" onsubmit="return toVaild()">
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
				<td><input class="inputxt" id="prodIdShow" name="prodIdShow" value="4028d0fe5875500d0158757737d3003b"/></td>
				<td><label>iFRE提供的决策方案产品编码</label></td>
			</tr>
			<tr hidden="hidden">
				<td><label>businessNum：</label></td>
				<td><input class="inputxt" id="businessNum" name="businessNum" /></td>
				<td><label>调用机构自行设置，业务编码</label></td>
			</tr>
			<tr>
				<td><label>年龄：</label></td>
				<td>
					<select id="age" name="age">
    					<option value="">请选择</option>
						<option value="1">大于21岁小于24岁</option>
						<option value="2">大于等于24岁小于31岁</option>
						<option value="3">大于等于31岁小于41岁</option>
						<option value="4">大于等于41岁小于46岁</option>
						<option value="5">大于等于46岁</option>
						<option value="-99">保密</option>
    				</select>
				</td>
			</tr>
			<tr>
				<td><label>最高学历：</label></td>
				<td>
					<select id="education" name="education">
    					<option value="">请选择</option>
						<option value="1">本科、硕士及以上</option>
						<option value="2">专科</option>
						<option value="3">其他</option>
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
				<td><label>婚姻状况：</label></td>
				<td>
					<select id="marriage" name="marriage">
    					<option value="">请选择</option>
						<option value="1">已婚</option>
						<option value="2">未婚</option>
						<option value="3">离异、再婚、丧偶</option>
						<option value="-99">保密</option>
    				</select>
				</td>
			</tr>
			<tr>
				<td><label>共同居住者：</label></td>
				<td>
					<select id="liveJoin" name="liveJoin">
    					<option value="">请选择</option>
						<option value="1">父母</option>
						<option value="2">配偶及子女</option>
						<option value="3">朋友</option>
						<option value="4">亲戚</option>
						<option value="5">12，13,14,24</option>
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
						<option value="2">无</option>
						<option value="-99">保密</option>
    				</select>
				</td>
			</tr>
			<tr>
				<td><label>入职时间：</label></td>
				<td>
					<select id="compJoinYear" name="compJoinYear">
    					<option value="">请选择</option>
						<option value="1">不满一年</option>
						<option value="2">1年或1年以上3年以下</option>
						<option value="3">3年或3年以上9年以下</option>
						<option value="4">9年或9年以上19年以下</option>
						<option value="5">19年或19年以上23年以下</option>
						<option value="6">23年或23年以上</option>
						<option value="-99">保密</option>
    				</select>
				</td>
			</tr>
			<tr>
				<td><label>目前住址省：</label></td>
				<td>
					<select id="nowAddressProvince" name="nowAddressProvince">
    					<option value="">请选择</option>
						<option value="2">河北省-130000</option>
						<option value="1">辽宁省-210000</option>
						<option value="10">上海-310000</option>
						<option value="9">江苏省-320000</option>
						<option value="4">福建省-350000</option>
						<option value="7">江西省-360000</option>
						<option value="8">山东省-370000</option>
						<option value="5">河南省-410000</option>
						<option value="3">陕西省-610000</option>
						<option value="-99">保密</option>
    				</select>
				</td>
			</tr>
			<tr>
				<td><label>是否有信用报告：</label></td>
				<td>
					<select id="isCredit" name="isCredit">
    					<option value="">请选择</option>
						<option value="1">有</option>
						<option value="0">无</option>
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
    	var age = document.getElementById("age").value;
        if(age.replace(/(^\s+)|(\s+$)/g,"") == ""){
            alert("请选择“年龄”！");
            return false;
        }
        var education = document.getElementById("education").value;
        if(education.replace(/(^\s+)|(\s+$)/g,"") == ""){
            alert("请选择“最高学历”！");
            return false;
        }
        var isOpen = document.getElementById("isOpen").value;
        if(isOpen.replace(/(^\s+)|(\s+$)/g,"") == ""){
            alert("请选择“公开程度”！");
            return false;
        }
        var marriage = document.getElementById("marriage").value;
        if(marriage.replace(/(^\s+)|(\s+$)/g,"") == ""){
            alert("请选择“婚姻状况”！");
            return false;
        }
        var liveJoin = document.getElementById("liveJoin").value;
        if(liveJoin.replace(/(^\s+)|(\s+$)/g,"") == ""){
            alert("请选择“共同居住者”！");
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
        var nowAddressProvince = document.getElementById("nowAddressProvince").value;
        if(nowAddressProvince.replace(/(^\s+)|(\s+$)/g,"") == ""){
            alert("请选择“目前住址省”！");
            return false;
        }
        var isCredit = document.getElementById("isCredit").value;
        if(isCredit.replace(/(^\s+)|(\s+$)/g,"") == ""){
            alert("请选择“是否有信用报告”！");
            return false;
        }
        return true;
    }
	
</script> 
</html>