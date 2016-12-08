<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="busiSysInfoList" title="资产端多渠道配置" actionUrl="busiSysInfoController.do?datagrid" idField="id" fit="true" pageSize="20">
   <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
   <t:dgCol title="渠道来源" field="sysSource"   width="100"></t:dgCol>
   <t:dgCol title="渠道描述" field="sysDesc"   width="100"></t:dgCol>
   <t:dgCol title="密码" field="password"   width="100"></t:dgCol>
   <t:dgCol title="签名秘钥" field="signKey"   width="100"></t:dgCol>
   <t:dgCol title="加密秘钥" field="encryptKey"   width="100"></t:dgCol>
   <t:dgCol title="回调地址" field="notifyUrl"   width="100"></t:dgCol>
   <t:dgCol title="绑定机构编码" field="orgCode"   width="100"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="busiSysInfoController.do?del&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="busiSysInfoController.do?addorupdate" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="busiSysInfoController.do?addorupdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="busiSysInfoController.do?addorupdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script> 
 	$(function(){
 		$("#busiSysInfoList").datagrid({
 			onDblClickRow: function (data) { 
 				detail('渠道来源详情','busiSysInfoController.do?addorupdate','busiSysInfoList','','');
 			},
 			onLoadError: function(data) {
 				$.messager.alert('错误','请重新加载页面');
	        }
 		});
 	});
 </script>