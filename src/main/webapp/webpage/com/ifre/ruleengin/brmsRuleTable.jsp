<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>决策表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="div" action="brmsRuleTableController.do?save">
		<input id="id" name="id" type="hidden" value="${brmsRuleTablePage.id }">
		<fieldset class="step">
			<div class="form">
		      <label class="Validform_label">所属机构ID:</label>
		      <input class="inputxt" id="orgId" name="orgId" 
					   value="${brmsRuleTablePage.orgId}" datatype="*">
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">产品ID:</label>
		      <input class="inputxt" id="knowId" name="knowId" 
					   value="${brmsRuleTablePage.knowId}" datatype="*">
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">产品ID:</label>
		      <input class="inputxt" id="prodId" name="prodId" 
					   value="${brmsRuleTablePage.prodId}" datatype="*">
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">包ID:</label>
		      <input class="inputxt" id="pkgNo" name="pkgNo" 
					   value="${brmsRuleTablePage.pkgNo}" datatype="*">
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">包全名:</label>
		      <input class="inputxt" id="pkgAllName" name="pkgAllName" 
					   value="${brmsRuleTablePage.pkgAllName}" datatype="*">
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">名称:</label>
		      <input class="inputxt" id="name" name="name" 
					   value="${brmsRuleTablePage.name}" datatype="*">
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">注释:</label>
		      <input class="inputxt" id="note" name="note" 
					   value="${brmsRuleTablePage.note}" datatype="*">
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">优先级:</label>
		      <input class="inputxt" id="salience" name="salience" ignore="ignore"
					   value="${brmsRuleTablePage.salience}" datatype="n">
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">条件总数:</label>
		      <input class="inputxt" id="condCount" name="condCount" ignore="ignore"
					   value="${brmsRuleTablePage.condCount}" datatype="n">
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">动作总数:</label>
		      <input class="inputxt" id="actionCount" name="actionCount" ignore="ignore"
					   value="${brmsRuleTablePage.actionCount}" datatype="n">
		      <span class="Validform_checktip"></span>
		    </div>
	    </fieldset>
  </t:formvalid>
 </body>