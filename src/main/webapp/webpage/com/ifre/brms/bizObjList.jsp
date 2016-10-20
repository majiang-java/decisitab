<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="bizObjList" fitColumns="true" title="业务对象" actionUrl="bizObjController.do?datagrid" idField="id" fit="true" queryMode="group" sortName="id" sortOrder="desc" pageSize="20"
  onDblClick="detail('查看','bizObjController.do?addorupdate','bizObjList','100%','100%')">
   <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
   <t:dgCol title="决策方案" field="prodId" query="true" dictionary="prodId" width="100" ></t:dgCol>
   <t:dgCol title="包名" field="pckgId" query="true" dictionary="pckgId" width="100"></t:dgCol>
 <%--    <t:dgCol title="决策方案" field="prodName"  width="100"></t:dgCol> --%>
  <%--  <t:dgCol title="包名" field="pckgName"   width="100"></t:dgCol> --%>
   <t:dgCol title="名称" field="name" query="true" width="100"></t:dgCol>
   <t:dgCol title="描述" field="descp" width="100" ></t:dgCol>
   <t:dgCol title="源码" field="sourceCode" width="100" ></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="bizObjController.do?del&id={id}" />
  <%--  <t:dgToolBar title="录入" icon="icon-add" url="bizObjController.do?addorupdate" funname="add" width="100%" height="100%"></t:dgToolBar> --%>
   <t:dgToolBar title="编辑" icon="icon-edit" url="bizObjController.do?addorupdate" funname="update" width="100%" height="100%"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>