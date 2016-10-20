<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <t:base type="jquery,easyui,tools"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" layout="div" dialog="true" action="modelVarController.do?saveType">
    <input name="id" type="hidden" value="${type.id }">
    <input name="VarTypegroup.id" type="hidden" value="${typegroupid}">
    <fieldset class="step">
        <div class="form">
            <label class="Validform_label"> 变量类别: </label>
            <input readonly="true" class="inputxt" value="${varTypegroupname }">
        </div>
        <div class="form">
            <label class="Validform_label"> 变量取值: </label>
            <input name="typename" class="inputxt" value="${type.typename }" datatype="s1-150">
            <span class="Validform_checktip">类型范围在1~150位字符</span>
        </div>
        <div class="form">
            <label class="Validform_label"> 第一码值: </label>
            <input name="codevalue1" class="inputxt" value="${type.codevalue1}" datatype="n">
            <span class="Validform_checktip">第一码值为数字类型</span>
        </div> 
        <div class="form">
            <label class="Validform_label"> 第二码值: </label>
            <input name="codevalue2" class="inputxt" value="${type.codevalue2}" datatype="n">
            <span class="Validform_checktip">第二码值为数字类型</span>
        </div>                 
        <div class="form">
            <label class="Validform_label"> 分值: </label>
            <input name="score" class="inputxt" value="${type.score}" datatype="n">
            <span class="Validform_checktip">分值为数字类型</span>
        </div> 
    </fieldset>
</t:formvalid>
</body>
</html>
