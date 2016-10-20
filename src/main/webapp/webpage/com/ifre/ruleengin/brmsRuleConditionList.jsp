<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="brmsRuleConditionList" title="规则条件" actionUrl="brmsRuleConditionController.do?datagrid" idField="id" fit="true">
   <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
   <t:dgCol title="决策表ID" field="ruleTableId"   width="120"></t:dgCol>
   <t:dgCol title="业务对象ID" field="bizObjId"   width="120"></t:dgCol>
   <t:dgCol title="业务对象名" field="bizObjName"   width="120"></t:dgCol>
   <t:dgCol title="对象属性" field="bizObjProp"   width="120"></t:dgCol>
   <t:dgCol title="属性条件脚本" field="propCondScript"   width="120"></t:dgCol>
   <t:dgCol title="属性条件描述" field="propCondDes"   width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="brmsRuleConditionController.do?del&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="brmsRuleConditionController.do?addorupdate" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="brmsRuleConditionController.do?addorupdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="brmsRuleConditionController.do?addorupdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>