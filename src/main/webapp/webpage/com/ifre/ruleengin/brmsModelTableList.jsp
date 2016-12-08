<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>

<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<link href="./css/skin.css" rel="stylesheet" />
<style>
	
</style>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="brmsRuleTableList" title="决策表" actionUrl="brmsRuleTableController.do?ruleTreeGrid&prodType=1" idField="id" fit="true"  queryMode="group" treegrid="true" pageSize="20">
   <t:dgCol title="编号" field="id" treefield="id" hidden="true"></t:dgCol>
  <t:dgCol title="机构" field="orgId"  dictionary="orgId" query="true" treefield="note" width="120"></t:dgCol>
   <t:dgCol title="产品" field="prodId" treefield="text" query="true" width="120"></t:dgCol>
   <t:dgCol title="优先级" field="salience" treefield="src"   width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="brmsRuleTableController.do?del&id={id}&src={salience}" operationCode="del"/>
   <t:dgToolBar title="创建" icon="icon-edit" url="brmsRuleTableController.do?createDecitable&prodType=1" funname="add" operationCode="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="brmsRuleTableController.do?editDecitable"  width="100%" height="100%" funname="update" operationCode="update"></t:dgToolBar>
 <%--   <t:dgToolBar title="上传" icon="icon-newupload" url="fileController.do?upload" funname="add" operationCode="upload"></t:dgToolBar> --%>
   <%-- <t:dgToolBar title="查看" icon="icon-search" url="brmsRuleTableController.do?addorupdate" funname="detail" height="40%"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>
 
 <script>
 $(function() {
	 $('#ccc').combotree({
		url : 'departController.do?setPFunction&selfId=${depart.id}',
       width: 155,
       onSelect : function(node) {
       //  alert(node.text);
       //  changeOrgType();
       }
   });   
 });
 </script>