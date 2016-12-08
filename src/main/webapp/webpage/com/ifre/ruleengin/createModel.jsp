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
					name="departId" class="prov form-control" datatype="*"  style="min-width:260px;">
				</select>
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">产品:</label>
		     <select id="knowId" name="knowId" class="city form-control" datatype="*" style="min-width:260px;">
				</select>
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">决策方案:</label>
		     <select id="prodId" name="prodId" class="dist form-control" datatype="*" style="min-width:260px;">
				</select>
			 
		      <span class="Validform_checktip"></span>
		    </div>
		    </div>
		    <div class="form">
		        <label class="Validform_label">模版类型:</label>
		    	<select id="typeid" name="typeid" datatype="*"></select><a id="descs" style="display:inline-block;width:44px;height:16px;background-position:center right;margin-left:5px" class="icon-help" href="javascript:;">查看</a>
		    </div>
		    <div class="form">
		      <label class="Validform_label">是否用变量:</label>
		     	<div style="margin-top:8px"><input type="radio" class="dist form-control" name="isUse" value="1" checked/>使用&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" class="dist form-control" name="isUse" value="0"/>不使用</div>
		    </div>
	    </fieldset>
  </t:formvalid>
 </body>
 
 	<script type="text/javascript">
 		var type = '${prodType }';
		$(function(){
			
			$("#choosearea").citySelect({
				url: "brmsRuleTableController.do?getIndustMsg&prodType=${prodType}",
				prov:"",
				city:"",
				dist:"",
				nodata:"none"
				});
			setTimeout(function(){
				if(type =='1'){
					$("#typeid option").each(function() {
					    if(/\s*反欺诈\s*/.test($(this).text())) {
					        $(this).remove();
					    }
					})
				}
			},100);
			
			$.getJSON("templateDescController.do?combox",function(datac){
				var $typeid= $("#typeid");
				$typeid.empty();
				$typeid.append("<option value='' selected>-请选择-</option>");
				$.each(datac,function(i,n){
					$("#typeid").append("<option value='"+n.id+"'>"+n.text+"</option>");
				});
			});
			
			
		});
		
		$("#descs").click(function(){
			var c = $("#typeid")
			createdetailwindow("模版类型介绍", "templateDescController.do?showTemplateDesc&templateDescId="+c,"100%","100%");
		});
		
	</script>
  
  