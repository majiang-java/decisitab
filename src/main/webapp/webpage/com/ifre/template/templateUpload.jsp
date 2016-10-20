<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>文件列表</title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" layout="div" dialog="true" beforeSubmit="upload">
	<fieldset class="step">
	<div class="form">
		<label class="Validform_label"> 模版类型: </label> 
		<t:dictSelect id="typeid" field="typeid" typeGroupCode="tempType" datatype="*"></t:dictSelect>
		<span class="Validform_checktip">请选择模版类型</span>
	</div>
	<div class="form">
		<t:upload name="fiels" formData="typeid" buttonText="上传文件" uploader="fileController.do?uploadTemplate" extend="office" id="file_upload" ></t:upload>
	</div>
	<div class="form" id="filediv" style="height: 50px"></div>
	</fieldset>
</t:formvalid>
</body>
</html>
