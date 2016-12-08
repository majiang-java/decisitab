<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="ruleProdList" title="决策打包" actionUrl="ruleTesterController.do?datagridJarList&prodType=1" idField="id" fit="true" queryMode="group" sortOrder="desc" pageSize="20">
   <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
   <t:dgCol title="编号" field="packStatus" hidden="true"></t:dgCol>
   <t:dgCol title="模板类型" field="tempType" hidden="true"></t:dgCol>
   <t:dgCol title="所属机构" field="orgId"  dictionary="orgId" width="120" query="true"  extend="{style:'min-width:205px;'}"></t:dgCol>
   <t:dgCol title="产品" field="kknwldgId"  dictionary="kKnwldgId"  width="120" query="true"  extend="{style:'min-width:200px;'}"></t:dgCol>
   <t:dgCol title="名称" field="name"   width="100" query="true" extend="{style:'min-width:200px;'}"></t:dgCol>
   <t:dgCol title="状态" field="status"  dictionary="ruleStatus" width="100"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="200"></t:dgCol>
   <t:dgFunOpt funname="pckBizObject(id,name)" title="决策打包" operationCode="pckBizObject"></t:dgFunOpt>
   <t:dgDelOpt url="ruleProdController.do?downJar&fileName={name}&version={versionId}&packStatus={packStatus}" title="决策包下载"></t:dgDelOpt>
   <t:dgFunOpt funname="publish(id)" title="决策发布" operationCode="pckBizObject"></t:dgFunOpt>
   <t:dgFunOpt funname="queryUrlVarID(status,id,tempType)" title="已发布接口" operationCode="queryUrlVarID"></t:dgFunOpt>
  <%--  <t:dgToolBar title="录入" icon="icon-add" url="ruleProdController.do?addorupdate" funname="add"></t:dgToolBar> --%>
   <t:dgToolBar title="编辑" icon="icon-edit" url="ruleProdController.do?addorupdate" funname="update" operationCode="update"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="ruleProdController.do?addorupdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="调用jar包方法"  icon="icon-search" funname="callJar"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 
 
 <script type="text/javascript">
 $(function() {
	 $('#ccc').combotree({
		url : 'departController.do?setPFunction&selfId=${depart.id}',
       width: 195,
       onSelect : function(node) {
       //  alert(node.text);
       //  changeOrgType();
       }
   });    

});
 	$(function(){
		$("#ruleProdList").datagrid({
			onDblClickRow: function (data) {  
				detail('查看','knwldgLibController.do?addorupdate','ruleProdList',null,null);
			}, 
			onLoadError: function(data) {
				$.messager.alert('错误','请重新加载页面');
	        }
		});
	}); 
 	
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
 	
 	function publish(id,name){
 		 var url = "ruleTesterController.do?showPublishPage&id="+id+"&prodName="+name;
 		 createdetailwindow('决策方案发布', url);
 	}
 	
 	function queryUrlVarID(status,id,tempType){
 		if(status == '3'){
 	        var url = "ruleProdController.do?queryUrlVarID&id="+id+"&tempType="+tempType;
 	        //add('打包产品', url, null); 
 	        createdetailwindow("已发布接口调用代码",url);
 		}else{
 			$.messager.alert('提示','请先发布决策方案');
 		}

	} 	
 	
 	function callJar(){
 		  var url = "ruleTesterController.do?callJar";
 	      createdetailwindow("调用jar包方法",url,"100%","100%");
 	}
</script>