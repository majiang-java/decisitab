<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>规则包</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="div" action="brmsRulePckgController.do?save">
		<input id="id" name="id" type="hidden" value="${brmsRulePckgPage.id }">
		<fieldset class="step">
			<div class="form">
		      <label class="Validform_label">产品ID:</label>
		      <input class="inputxt" id="prodId" name="prodId" 
					   value="${brmsRulePckgPage.prodId}" datatype="*">
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">名称:</label>
		      <input class="inputxt" id="name" name="name" ignore="ignore"
					   value="${brmsRulePckgPage.name}">
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">全名:</label>
		      <input class="inputxt" id="allName" name="allName" ignore="ignore"
					   value="${brmsRulePckgPage.allName}">
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">描述:</label>
		      <%-- <input class="inputxt" id="descp" name="descp" ignore="ignore"
					   value="${brmsRulePckgPage.descp}"> --%>
			  <textarea class="inputxt" id="descp" name="descp" ignore="ignore" rows="2" style="min-width:260px;">${brmsRulePckgPage.descp}</textarea>
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">父包ID:</label>
		      <input class="inputxt" id="parentPckgId" name="parentPckgId" ignore="ignore"
					   value="${brmsRulePckgPage.parentPckgId}">
		      <span class="Validform_checktip"></span>
		    </div>
	    </fieldset>
  </t:formvalid>
 </body>