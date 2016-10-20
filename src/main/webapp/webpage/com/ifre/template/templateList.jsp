<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<t:datagrid name="addTemplateList" title="模版管理" actionUrl="templateMangerController.do?ruleTreeGrid" idField="id" treegrid="true" pagination="false">
	<t:dgCol title="编号" field="id" treefield="id" hidden="true"></t:dgCol>
	<t:dgCol title="模版类别与名称" field="functionName" width="100" treefield="text"></t:dgCol>
	<t:dgCol title="操作" field="opt" width="100"></t:dgCol>
	<t:dgDelOpt url="templateMangerController.do?delDemo&id={id}" title="删除"></t:dgDelOpt>
	<t:dgToolBar title="上传" icon="icon-upload" url="fileController.do?uploadTemplateview" funname="add" operationCode="upload"></t:dgToolBar>
    <t:dgToolBar title="编辑" icon="icon-edit" url="templateMangerController.do?editDecitable"  width="100%" height="100%" funname="update" operationCode="update"></t:dgToolBar>
</t:datagrid>