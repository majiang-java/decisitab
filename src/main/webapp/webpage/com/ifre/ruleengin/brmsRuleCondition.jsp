<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>规则条件</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="div" action="brmsRuleConditionController.do?save">
		<input id="id" name="id" type="hidden" value="${brmsRuleConditionPage.id }">
		<fieldset class="step">
			<div class="form">
		      <label class="Validform_label">决策表ID:</label>
		      <input class="inputxt" id="ruleTableId" name="ruleTableId" 
					   value="${brmsRuleConditionPage.ruleTableId}" datatype="*">
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">业务对象ID:</label>
		      <input class="inputxt" id="bizObjId" name="bizObjId" ignore="ignore"
					   value="${brmsRuleConditionPage.bizObjId}">
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">业务对象名:</label>
		      <input class="inputxt" id="bizObjName" name="bizObjName" ignore="ignore"
					   value="${brmsRuleConditionPage.bizObjName}">
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">对象属性:</label>
		      <input class="inputxt" id="bizObjProp" name="bizObjProp" ignore="ignore"
					   value="${brmsRuleConditionPage.bizObjProp}">
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">属性条件脚本:</label>
		      <input class="inputxt" id="propCondScript" name="propCondScript" ignore="ignore"
					   value="${brmsRuleConditionPage.propCondScript}">
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">属性条件描述:</label>
		      <input class="inputxt" id="propCondDes" name="propCondDes" ignore="ignore"
					   value="${brmsRuleConditionPage.propCondDes}">
		      <span class="Validform_checktip"></span>
		    </div>
	    </fieldset>
  </t:formvalid>
 </body>