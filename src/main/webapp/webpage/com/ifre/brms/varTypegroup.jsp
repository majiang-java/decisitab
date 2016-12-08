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
	<input name="varTypegroupcode" class="inputxt" value="${varTypegroup.varTypegroupcode}" datatype="s2-60" style="min-width:260px;"> <span class="Validform_checktip">变量名称在2~60位字符</span></div>
	<div class="form">
		<label class="Validform_label"> 变量描述: </label> 
		<%-- <input name="varTypegroupname" class="inputxt" value="${varTypegroup.varTypegroupname}" datatype="*" style="min-width:260px;">  --%>
		<textarea name="varTypegroupname" datatype="*"  rows="2" style="min-width:260px;">${varTypegroup.varTypegroupname}</textarea>
		<span class="Validform_checktip">变量描述在2~200位字符，注意：不能有回车</span>
	</div>
	<div class="form">
	<label class="Validform_label"> 所属方案: </label>
	<td class="value"><select id="ruleProdEntity.id" name="ruleProdEntity.id" datatype="*"  style="min-width:265px;">
		<c:forEach items="${ruleProdList}" var="ruleProd">
			<option value="${ruleProd.id }" <c:if test="${ruleProd.id==varTypegroup.ruleProdEntity.id}">selected="selected"</c:if>>${ruleProd.name}</option>
		</c:forEach>
	</select> <span class="Validform_checktip">请选择决策方案</span></td>
	</fieldset>
</t:formvalid>
</body>
</html>
