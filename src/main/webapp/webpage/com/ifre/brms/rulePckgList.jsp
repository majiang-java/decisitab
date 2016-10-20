<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="rulePckgList" title="方案包" actionUrl="rulePckgController.do?datagrid" idField="id" fit="true" queryMode="group" sortOrder="desc" pageSize="20"
  onDblClick="detail('查看','rulePckgController.do?addorupdate','rulePckgList',null,null)">
   <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
   <t:dgCol title="决策方案" field="prodId"   width="120" dictionary="prodId" query="true" ></t:dgCol>
 <%--   <t:dgCol title="决策方案" field="prodName"   width="120" ></t:dgCol> --%>
   <t:dgCol title="包名称" field="name"   width="120"  query="true"></t:dgCol>
   <t:dgCol title="包全名" field="allName"   width="120"  query="true"></t:dgCol>
   <t:dgCol title="描述" field="descp"   width="120"></t:dgCol>
   <t:dgCol title="父包ID" field="parentPckgId"   width="120"  query="true"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="rulePckgController.do?del&id={id}" operationCode="del"/>
   <t:dgFunOpt funname="addBizObject(id,allName,prodId)" title="添加业务对象" operationCode="addBizObjecj"></t:dgFunOpt>
 <%--   <t:dgToolBar title="录入" icon="icon-add" url="rulePckgController.do?addorupdate" funname="add" ></t:dgToolBar>  --%> 
   <t:dgToolBar title="编辑" icon="icon-edit" url="rulePckgController.do?addorupdate" funname="update" operationCode="update"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="rulePckgController.do?addorupdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 
 
  <script type="text/javascript">
  
/*  	$(function(){
		$("#rulePckgList").datagrid({
			onDblClickRow: function (data) {  
				detail('查看','rulePckgController.do?addorupdate','rulePckgList',null,null);
			}, 
			onLoadError: function(data) {
				$.messager.alert('错误','请重新加载页面');
	        }
		});
	});  */
	
 	function addBizObject(pckgId,allName,prodId){
  	        var rowsData = $('#rulePckgList').datagrid('getSelections');
 	        var url = "bizObjController.do?addorupdate&prodId="+prodId+"&pckgId="+pckgId+"&allName="+allName;
 	        console.info(url);
 	        add('添加业务对象', url,rulePckgList,1400,600);
 	}
</script>