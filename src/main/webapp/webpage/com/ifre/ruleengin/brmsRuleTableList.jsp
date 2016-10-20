<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>

<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<link href="./css/skin.css" rel="stylesheet" />
<style>
	
</style>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="brmsRuleTableList" title="决策表" actionUrl="brmsRuleTableController.do?datagrid" idField="id" fit="true"  queryMode="group">
   <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
 
   <t:dgCol title="所属机构" field="orgId" dictionary="orgId"  width="120" query="true"></t:dgCol>
   <t:dgCol title="产品" field="knowId" dictionary="kKnwldgId"  width="120" query="true"></t:dgCol>
   <t:dgCol title="产品" field="prodId" dictionary="prodId" query="true"  width="120"></t:dgCol>
   <t:dgCol title="包全名" field="pkgAllName"   width="120"></t:dgCol>
   <t:dgCol title="名称" field="name"   width="120"></t:dgCol>
   <t:dgCol title="注释" field="note"   width="120"></t:dgCol>
   <t:dgCol title="优先级" field="salience"   width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="brmsRuleTableController.do?del&id={id}" operationCode="del"/>
   <t:dgToolBar title="上传" icon="icon-newupload" url="fileController.do?upload" funname="add" operationCode="upload"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="brmsRuleTableController.do?editDecitable"  width="100%" height="100%" funname="update" operationCode="update"></t:dgToolBar>
   <t:dgToolBar title="创建" icon="icon-edit" url="brmsRuleTableController.do?createDecitable" funname="add" operationCode="add"></t:dgToolBar>
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