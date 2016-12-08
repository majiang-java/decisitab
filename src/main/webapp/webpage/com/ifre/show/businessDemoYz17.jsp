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
	<form action="businessDemoController.do?doRunModelYz17" method="post" onsubmit="return toVaild()">
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
				<td><input class="inputxt" id="prodIdShow" name="prodIdShow" value="4028d0fe5875500d0158757840e50041"/></td>
				<td><label>iFRE提供的决策方案产品编码</label></td>
			</tr>
			<tr hidden="hidden">
				<td><label>businessNum：</label></td>
				<td><input class="inputxt" id="businessNum" name="businessNum" /></td>
				<td><label>调用机构自行设置，业务编码</label></td>
			</tr>
			<tr>
				<td><label>近6个月信用报告查询次数：</label></td>
				<td>
					<select id="creditReportQueryTimes6" name="creditReportQueryTimes6">
    					<option value="">请选择</option>
						<option value="1">0-1次</option>
						<option value="4">2-4次</option>
						<option value="6">5-6次</option>
						<option value="9">7-9次</option>
						<option value="10">10次及以上</option>
						<option value="-99">保密</option>
    				</select>
				</td>
			</tr>
			<tr>
				<td><label>最高贷款额度_按揭月供3_授信_是否通过：</label></td>
				<td>
					<select id="maxLoanQuotaMonthRepayCreditIsPassed" name="maxLoanQuotaMonthRepayCreditIsPassed">
    					<option value="">请选择</option>
						<option value="1">通过</option>
						<option value="-99">未通过</option>
						<option value="-99">保密</option>
    				</select>
				</td>
			</tr>
			<tr>
				<td><label>贷款最近24个月应还款次数：</label></td>
				<td>
					<select id="loanShouldRepayTimes24" name="loanShouldRepayTimes24">
    					<option value="">请选择</option>
						<option value="13">0-13次</option>
						<option value="22">14-22次</option>
						<option value="29">23-29次</option>
						<option value="41">30-41次</option>
						<option value="42">42次及以上</option>
						<option value="-99">保密</option>
    				</select>
				</td>
			</tr>
			<tr>
				<td><label>贷款用途：</label></td>
				<td>
					<select id="loanUsage" name="loanUsage">
    					<option value="">请选择</option>
						<option value="1">经营</option>
						<option value="2">装修</option>
						<option value="3">消费购物</option>
						<option value="4">品质生活</option>
						<option value="-99">保密</option>
    				</select>
				</td>
			</tr>
			<tr>
				<td><label>贷记卡最近24个月应还款次数：</label></td>
				<td>
					<select id="debitCardShouldRepayTimes24" name="debitCardShouldRepayTimes24">
    					<option value="">请选择</option>
						<option value="61">0-61次</option>
						<option value="80">62-80次</option>
						<option value="150">81-150次</option>
						<option value="151">151次及以上</option>
						<option value="-99">保密</option>
    				</select>
				</td>
			</tr>
			<tr>
				<td><label>公司类型：</label></td>
				<td>
					<select id="companyType" name="companyType">
    					<option value="">请选择</option>
						<option value="1">个体</option>
						<option value="2">民营</option>
						<option value="3">外资、合资</option>
						<option value="4">国企、事业单位</option>
						<option value="-99">保密</option>
    				</select>
				</td>
			</tr>
			<tr>
				<td><label>居住人数：</label></td>
				<td>
					<select id="peopleLiveNumber" name="peopleLiveNumber">
    					<option value="">请选择</option>
						<option value="1">1人</option>
						<option value="2">2人</option>
						<option value="3">3人</option>
						<option value="4">4人及以上</option>
						<option value="-99">保密</option>
    				</select>
				</td>
			</tr>
			<tr>
				<td><label>是否缴纳社保公积金：</label></td>
				<td>
					<select id="socialSecFundIsPayed" name="socialSecFundIsPayed">
    					<option value="">请选择</option>
						<option value="1">是</option>
						<option value="0">否</option>
						<option value="-99">保密</option>
    				</select>
				</td>
			</tr>
			<tr>
				<td><label>贷款逾期笔数：</label></td>
				<td>
					<select id="loanOverdue" name="loanOverdue">
    					<option value="">请选择</option>
						<option value="1">0-1笔</option>
						<option value="2">2笔</option>
						<option value="3">3笔及以上</option>
						<option value="-99">保密</option>
    				</select>
				</td>
			</tr>
			<tr>
				<td><label>负债率核查_授信_是否通过：</label></td>
				<td>
					<select id="debtRateCheckCreditIsPassed" name="debtRateCheckCreditIsPassed">
    					<option value="">请选择</option>
						<option value="1">通过</option>
						<option value="-99">未通过</option>
						<option value="-99">保密</option>
    				</select>
				</td>
			</tr>
			<tr>
				<td><label>学历：</label></td>
				<td>
					<select id="education" name="education">
    					<option value="">请选择</option>
						<option value="1">本科及以上</option>
						<option value="2">大专</option>
						<option value="3">中专及以下</option>
						<option value="-99">保密</option>
    				</select>
				</td>
			</tr>
			<tr>
				<td><label>资产负债率：</label></td>
				<td>
					<select id="debtAssetRatio" name="debtAssetRatio">
    					<option value="">请选择</option>
						<option value="0.1">0.184以下</option>
						<option value="0.2">0.184及以上</option>
						<option value="-99">保密</option>
    				</select>
				</td>
			</tr>
			<tr>
				<td><label>申请期限：</label></td>
				<td>
					<select id="applicationPeriod" name="applicationPeriod">
    					<option value="">请选择</option>
						<option value="12">12期</option>
						<option value="24">24期</option>
						<option value="36">36期</option>
						<option value="-99">其他</option>
						<option value="-99">保密</option>
    				</select>
				</td>
			</tr>
			<tr>
				<td><label>发薪日是否早于初审结束日：</label></td>
				<td>
					<select id="payDayisEarlierThanFirstAuditDay" name="payDayisEarlierThanFirstAuditDay">
    					<option value="">请选择</option>
						<option value="1">是</option>
						<option value="0">否</option>
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
    	var creditReportQueryTimes6 = document.getElementById("creditReportQueryTimes6").value;
        if(creditReportQueryTimes6.replace(/(^\s+)|(\s+$)/g,"") == ""){
            alert("请选择“近6个月信用报告查询次数”！");
            return false;
        }
        var maxLoanQuotaMonthRepayCreditIsPassed = document.getElementById("maxLoanQuotaMonthRepayCreditIsPassed").value;
        if(maxLoanQuotaMonthRepayCreditIsPassed.replace(/(^\s+)|(\s+$)/g,"") == ""){
            alert("请选择“最高贷款额度_按揭月供3_授信_是否通过”！");
            return false;
        }
        var loanShouldRepayTimes24 = document.getElementById("loanShouldRepayTimes24").value;
        if(loanShouldRepayTimes24.replace(/(^\s+)|(\s+$)/g,"") == ""){
            alert("请选择“贷款最近24个月应还款次数”！");
            return false;
        }
        var loanUsage = document.getElementById("loanUsage").value;
        if(loanUsage.replace(/(^\s+)|(\s+$)/g,"") == ""){
            alert("请选择“贷款用途”！");
            return false;
        }
        var debitCardShouldRepayTimes24 = document.getElementById("debitCardShouldRepayTimes24").value;
        if(debitCardShouldRepayTimes24.replace(/(^\s+)|(\s+$)/g,"") == ""){
            alert("请选择“贷记卡最近24个月应还款次数”！");
            return false;
        }
        var companyType = document.getElementById("companyType").value;
        if(companyType.replace(/(^\s+)|(\s+$)/g,"") == ""){
            alert("请选择“公司类型”！");
            return false;
        }
        var peopleLiveNumber = document.getElementById("peopleLiveNumber").value;
        if(peopleLiveNumber.replace(/(^\s+)|(\s+$)/g,"") == ""){
            alert("请选择“居住人数”！");
            return false;
        }
        var socialSecFundIsPayed = document.getElementById("socialSecFundIsPayed").value;
        if(socialSecFundIsPayed.replace(/(^\s+)|(\s+$)/g,"") == ""){
            alert("请选择“是否缴纳社保公积金”！");
            return false;
        }
        var socialSecFundIsPayed = document.getElementById("loanOverdue").value;
        if(socialSecFundIsPayed.replace(/(^\s+)|(\s+$)/g,"") == ""){
            alert("请选择“贷款逾期笔数”！");
            return false;
        }
        var debtRateCheckCreditIsPassed = document.getElementById("debtRateCheckCreditIsPassed").value;
        if(debtRateCheckCreditIsPassed.replace(/(^\s+)|(\s+$)/g,"") == ""){
            alert("请选择“负债率核查_授信_是否通过”！");
            return false;
        }
        var education = document.getElementById("education").value;
        if(education.replace(/(^\s+)|(\s+$)/g,"") == ""){
            alert("请选择“学历”！");
            return false;
        }
        var debtAssetRatio = document.getElementById("debtAssetRatio").value;
        if(debtAssetRatio.replace(/(^\s+)|(\s+$)/g,"") == ""){
            alert("请选择“资产负债率”！");
            return false;
        }
        var applicationPeriod = document.getElementById("applicationPeriod").value;
        if(applicationPeriod.replace(/(^\s+)|(\s+$)/g,"") == ""){
            alert("请选择“申请期限”！");
            return false;
        }
        var payDayisEarlierThanFirstAuditDay = document.getElementById("payDayisEarlierThanFirstAuditDay").value;
        if(payDayisEarlierThanFirstAuditDay.replace(/(^\s+)|(\s+$)/g,"") == ""){
            alert("请选择“发薪日是否早于初审结束日”！");
            return false;
        }
        return true;
    }
	
</script> 
</html>