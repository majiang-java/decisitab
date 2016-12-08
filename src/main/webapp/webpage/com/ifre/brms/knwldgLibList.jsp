<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="knwldgLibList" title="产品"  autoLoadData="true"  actionUrl="knwldgLibController.do?datagrid" idField="id" fit="true" queryMode="group" sortOrder="desc" pageSize="20"
  onDblClick="detail('查看','knwldgLibController.do?addorupdate','knwldgLibList',null,null)">
   <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
   <t:dgCol title="所属机构ID" dictionary="orgId" sortable="true" hidden="true" field="orgId"  width="120"></t:dgCol>
   <t:dgCol title="所属机构" dictionary="orgCode" sortable="true" query="true" field="orgCode"  width="120"></t:dgCol>
<%--     <t:dgCol title="所属机构名称" field="orgName" query="true" width="120"></t:dgCol> --%>
   <t:dgCol title="名称" field="name"   width="120" query="true"></t:dgCol>
   <t:dgCol title="描述" field="descp"   width="120" query="true"></t:dgCol>
   <t:dgCol title="状态" field="status"   width="120" query="true" dictionary="knwStatus"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="knwldgLibController.do?del&id={id}" operationCode="del"/>
   <t:dgFunOpt funname="addBizObject(id,orgCode,name,orgId)" title="添加决策方案" operationCode="addBizObject"></t:dgFunOpt>
   <t:dgToolBar title="录入" icon="icon-add" url="knwldgLibController.do?addorupdate" funname="add" operationCode="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="knwldgLibController.do?addorupdate" funname="update" operationCode="update"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="knwldgLibController.do?addorupdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 
 <script type="text/javascript">
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
/*  	$(function(){
		$("#knwldgLibList").datagrid({
			onDblClickRow: function (data) {  
				detail('查看','knwldgLibController.do?addorupdate','knwldgLibList',null,null);
			}, 
			onLoadError: function(data) {
				$.messager.alert('错误','请重新加载页面');
	        }
		});
	});  */
 	
 	function addBizObject(id,orgCode,name,orgId){
  	        var rowsData = $('#knwldgLibList').datagrid('getSelections');
 	        var url = "ruleProdController.do?addorupdate&knowId="+id+"&knowName="+name+"&orgCode="+orgCode+"&orgId="+orgId;
 	        console.info(url);
 	        add('添加决策方案', url, null);
 	}
</script>