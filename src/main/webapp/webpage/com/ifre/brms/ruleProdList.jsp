<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 1px;">
		<t:datagrid name="ruleProdList" title="决策方案" 	actionUrl="ruleProdController.do?datagrid" idField="id" fit="true" queryMode="group" sortOrder="desc" pageSize="20"
		onDblClick="detail('查看','ruleProdController.do?addorupdate','ruleProdList',null,null)">
			<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
			<t:dgCol title="模版类型" field="tempType" hidden="true"></t:dgCol>
			<t:dgCol title="所属机构" field="orgId" dictionary="orgId" width="120"
				query="true"></t:dgCol>
			<t:dgCol title="产品" field="kknwldgId" dictionary="kKnwldgId"
				width="100" query="true"></t:dgCol>
			<%--    <t:dgCol title="产品" field="kknwldgName"    width="100"></t:dgCol> --%>
			<t:dgCol title="名称" field="name" width="100" query="true"></t:dgCol>
			<t:dgCol title="类型" field="type"  dictionary="prodtype" query="true" width="100"></t:dgCol>
			<t:dgCol title="操作" field="opt" width="100"></t:dgCol>
			<t:dgDelOpt title="删除" url="ruleProdController.do?del&id={id}"
				operationCode="del" />
			 <t:dgFunOpt funname="syncObject(id,tempType)" title="同步" operationCode="syncObject"></t:dgFunOpt>
			<%--  <t:dgFunOpt funname="pckBizObject(id)" title="决策打包" operationCode="pckBizObject"></t:dgFunOpt>
   <t:dgDelOpt url="ruleProdController.do?downJar&fileName={name}&version={versionId}&status={status}" title="产品包下载"></t:dgDelOpt> --%>
			<%--  <t:dgToolBar title="录入" icon="icon-add" url="ruleProdController.do?addorupdate" funname="add"></t:dgToolBar> --%>
			<t:dgToolBar title="编辑" icon="icon-edit"
				url="ruleProdController.do?addorupdate" funname="update" operationCode="update"></t:dgToolBar>
			<t:dgToolBar title="查看" icon="icon-search" url="ruleProdController.do?addorupdate" funname="detail"></t:dgToolBar>
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
/*    	$(function(){
		$("#ruleProdList").datagrid({
			onDblClickRow: function (data) {  
				detail('查看','ruleProdController.do?addorupdate','ruleProdList',null,null);
			}, 
			onLoadError: function(data) {
				$.messager.alert('错误','请重新加载页面');
	        }
		});
	});  */  
 	
 	function addBizObject(id,name){
  	        var rowsData = $('#ruleProdList').datagrid('getSelections');
 	        var url = "rulePckgController.do?addorupdate&prodId="+id+"&prodName="+name;
 	        console.info(url);
 	        add('添加方案包', url, null);
 	}
 	
 	function pckBizObject(id){
	        var url = "ruleProdController.do?makePck&id="+id;
	        //add('打包产品', url, null); 
	        openwindow("打包产品",url,"ruleProdList",700,500);
	}
 	
 	function syncObject(id,tempType){
 		$.dialog.confirm('确定同步决策数据吗', function(){
 			$.post("brmsRuleTableController.do?doSyncDataModel",{id:id,tempType:tempType},function(data){
 	 			var d = $.parseJSON(data);
 				if (d.success) {
 					tip(d.msg);
 				}else{
 					tip(d.msg);
 				}
 	 		});
 		}, function(){
 		});
 	
 	}
 	
 	
</script>

























