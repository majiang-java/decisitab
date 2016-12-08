<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <t:base type="jquery,easyui,tools"></t:base>
</head>
 <body style="overflow-y: hidden" scroll="no">
 <t:formvalid formid="formobj" layout="div" dialog="true" action="ruleProdController.do?save">
    <input id="id" name="id" type="hidden" value="${ruleProd.id}">
    <input name="orgId" type="hidden" value="${depart.id}">
    <input name="orgCode" type="hidden" value="${depart.orgCode}">
    <input name="kknwldgId" type="hidden" value="${ruleProd.kknwldgId}">
    <input name="status" type="hidden" value="${ruleProd.status}">
    <input name="rightStatus" type="hidden" value="${ruleProd.rightStatus}">
    <fieldset class="step">
        <div class="form">
            <label class="Validform_label"> 所属机构: </label>
            <input id="orgName" class="inputxt" value="${depart.departname}" readonly  style="min-width:260px;">	
        </div>
        <div class="form">
            <label class="Validform_label"> 产品: </label>
            <input name="kknwldgName" class="inputxt" value="${knwldgLib.name }" readonly  style="min-width:260px;">
        </div>
        <div class="form">
            <label class="Validform_label"> 名称: </label>
            <input name="name" class="inputxt" value="${ruleProd.name}"  datatype="s1-80"  style="min-width:260px;">
            <span class="Validform_checktip">名称范围在1~80位字符</span>
        </div> 
        <div class="form">
            <label class="Validform_label"> 描述: </label>
            <%-- <input name="descp" class="inputxt" value="${ruleProd.descp}"  datatype="s1-80"  style="min-width:260px;"> --%>
            <textarea name="descp" datatype="s1-80" rows="2" style="min-width:260px;">${ruleProd.descp}</textarea>
            <span class="Validform_checktip">名称范围在1~80位字符</span>
        </div>   
        <c:if test="${empty ruleProd.id}">              
	        <div class="form">
	            <label class="Validform_label"> 方案类型: </label>
	            <c:if test="${empty ruleProd.type}"><t:dictSelect field="type" typeGroupCode="prodtype" hasLabel="false" defaultVal="1" type="radio"/></c:if>
	            <c:if test="${not empty ruleProd.type}"><t:dictSelect field="type" typeGroupCode="prodtype" hasLabel="false" defaultVal="${ruleProd.type}" type="radio"/></c:if>
	        </div> 
        </c:if>
    </fieldset>
</t:formvalid>
 </body>