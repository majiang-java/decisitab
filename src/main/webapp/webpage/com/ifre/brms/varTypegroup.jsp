<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <t:base type="jquery,easyui,tools"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" layout="div" dialog="true" action="modelVarController.do?saveTypeGroup">
	<input name="id" type="hidden" value="${varTypegroup.id}">
	<fieldset class="step">
	<div class="form">
	<label class="Validform_label"> 变量名称: </label> 
	<input name="varTypegroupcode" class="inputxt" value="${varTypegroup.varTypegroupcode}" datatype="s2-60"> <span class="Validform_checktip">变量名称在2~60位字符</span></div>
	<div class="form">
	<label class="Validform_label"> 变量描述: </label> 
	<input name="varTypegroupname" class="inputxt" value="${varTypegroup.varTypegroupname}" datatype="*"> <span class="Validform_checktip">变量描述在2~200位字符</span></div>
	<div class="form">
    <label class="Validform_label"> 模型类型: </label>
    <input name="typecode" class="inputxt" value="${varTypegroup.typecode }" datatype="s1-50"><span class="Validform_checktip">模型类型在1~50位字符</span></div>
	</fieldset>
</t:formvalid>
</body>
</html>
