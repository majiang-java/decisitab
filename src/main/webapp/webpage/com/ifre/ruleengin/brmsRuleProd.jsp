<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>规则属性</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="div" action="brmsRuleProdController.do?save">
		<input id="id" name="id" type="hidden" value="${brmsRuleProdPage.id }">
		<fieldset class="step">
			<div class="form">
		      <label class="Validform_label">所属机构ID:</label>
		      <input class="inputxt" id="orgId" name="orgId" 
					   value="${brmsRuleProdPage.orgId}" datatype="*">
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">产品ID:</label>
		      <input class="inputxt" id="kknwldgId" name="kknwldgId" 
					   value="${brmsRuleProdPage.kknwldgId}" datatype="*">
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">名称:</label>
		      <input class="inputxt" id="name" name="name" ignore="ignore"
					   value="${brmsRuleProdPage.name}">
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">描述:</label>
		      <input class="inputxt" id="descp" name="descp" ignore="ignore"
					   value="${brmsRuleProdPage.descp}">
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">方案组ID:</label>
		      <input class="inputxt" id="groupId" name="groupId" ignore="ignore"
					   value="${brmsRuleProdPage.groupId}">
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">方案ID:</label>
		      <input class="inputxt" id="artifactId" name="artifactId" ignore="ignore"
					   value="${brmsRuleProdPage.artifactId}">
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">版本号:</label>
		      <input class="inputxt" id="versionId" name="versionId" ignore="ignore"
					   value="${brmsRuleProdPage.versionId}">
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">版本描述:</label>
		      <input class="inputxt" id="versionDesc" name="versionDesc" ignore="ignore"
					   value="${brmsRuleProdPage.versionDesc}">
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">状态:</label>
		      <input class="inputxt" id="status" name="status" ignore="ignore"
					   value="${brmsRuleProdPage.status}" datatype="n">
		      <span class="Validform_checktip"></span>
		    </div>
	    </fieldset>
  </t:formvalid>
 </body>