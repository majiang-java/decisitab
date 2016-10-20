<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="brmsConditionDetailList" title="条件描述" actionUrl="brmsConditionDetailController.do?datagrid" idField="id" fit="true">
   <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
   <t:dgCol title="条件ID" field="condId"   width="120"></t:dgCol>
   <t:dgCol title="序号" field="seq"   width="120"></t:dgCol>
   <t:dgCol title="条件值" field="condValue"   width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="brmsConditionDetailController.do?del&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="brmsConditionDetailController.do?addorupdate" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="brmsConditionDetailController.do?addorupdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="brmsConditionDetailController.do?addorupdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>