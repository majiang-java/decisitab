<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="brmsRuleProdList" title="规则属性" actionUrl="brmsRuleProdController.do?datagrid" idField="id" fit="true">
   <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
   <t:dgCol title="所属机构ID" field="orgId"   width="120"></t:dgCol>
   <t:dgCol title="产品ID" field="kknwldgId"   width="120"></t:dgCol>
   <t:dgCol title="名称" field="name"   width="120"></t:dgCol>
   <t:dgCol title="描述" field="descp"   width="120"></t:dgCol>
   <t:dgCol title="方案组ID" field="groupId"   width="120"></t:dgCol>
   <t:dgCol title="方案ID" field="artifactId"   width="120"></t:dgCol>
   <t:dgCol title="版本号" field="versionId"   width="120"></t:dgCol>
   <t:dgCol title="版本描述" field="versionDesc"   width="120"></t:dgCol>
   <t:dgCol title="状态" field="status"   width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="brmsRuleProdController.do?del&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="brmsRuleProdController.do?addorupdate" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="brmsRuleProdController.do?addorupdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="brmsRuleProdController.do?addorupdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>