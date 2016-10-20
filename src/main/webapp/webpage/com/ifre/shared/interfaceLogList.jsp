<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="interfaceLogList" title="资产端流水" actionUrl="interfaceLogController.do?datagrid" idField="id" fit="true" queryMode="group" sortName="createDate" sortOrder="desc" pageSize="20">
   <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
   <t:dgCol title="渠道来源" field="sysSource" width="100" query="true" extend="{style:'margin:10px'}"></t:dgCol>
   <t:dgCol title="渠道描述" field="sysDesc" width="100" query="true" extend="{style:'margin:10px'}"></t:dgCol>
   <t:dgCol title="业务编码" field="businessNum" width="100" query="true" extend="{style:'margin:10px'}"></t:dgCol>
   <t:dgCol title="业务类型" field="businessType"  dictionary="busiType" width="100" query="true" extend="{style:'margin:10px'}"></t:dgCol>
   <t:dgCol title="处理状态" field="handleStatus"  dictionary="HandleStat"  width="100" query="true" extend="{style:'margin:10px'}"></t:dgCol>
   <t:dgCol title="创建时间" field="createDate"   width="100"></t:dgCol>
   <t:dgToolBar title="查看" icon="icon-search" url="interfaceLogController.do?addorupdate" funname="detail" width="100%" height="100%"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
<script> 
 	$(function(){
 		$("#interfaceLogList").datagrid({
 			onDblClickRow: function (data) { 
 				detail('通讯日志详情','interfaceLogController.do?addorupdate','interfaceLogList','100%','100%');
 			},
 			onLoadError: function(data) {
 				$.messager.alert('错误','请重新加载页面');
	        }
 		});
 	});
 </script>