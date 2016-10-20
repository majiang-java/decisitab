<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>按照模型添加</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script src="plug-in/selecter/jquery.cityselect.js"></script>
  <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="div" action="brmsRuleTableController.do?doCreateModel">
		<input id="id" name="id" type="hidden" value="${brmsRulePckgPage.id }">
		<fieldset class="step">
		<div id="choosearea">
			<div class="form">
		      <label class="Validform_label">机构：</label>
		       	<select id="department"
					name="departId" class="prov form-control" datatype="*">
				</select>
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">产品:</label>
		     <select id="knowId" name="knowId" class="city form-control" datatype="*">
				</select>
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">决策方案:</label>
		      <select id="prodId" name="prodId" class="dist form-control" datatype="*">
				</select>
		      <span class="Validform_checktip"></span>
		    </div>
		    </div>
		     <t:dictSelect id="typeid" field="typeid" typeGroupCode="tempType" datatype="*"></t:dictSelect>
	    </fieldset>
  </t:formvalid>
 </body>
 
 	<script type="text/javascript">
		$(function(){
			$("#choosearea").citySelect({
				url: "brmsRuleTableController.do?getIndustMsg",
				prov:"",
				city:"",
				dist:"",
				nodata:"none"
				});
		});
		
		
	</script>
  
  