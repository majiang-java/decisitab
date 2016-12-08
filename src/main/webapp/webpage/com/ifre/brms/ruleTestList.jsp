<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="ruleProdList" title="决策方案" actionUrl="ruleTesterController.do?datagrid&prodType=2" idField="id" fit="true" queryMode="group" sortOrder="desc" pageSize="20">
   <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
   <t:dgCol title="所属机构" field="orgId"  dictionary="orgId" width="120" query="true" extend="{style:'width:205px;'}"></t:dgCol>
   <t:dgCol title="产品" field="kknwldgId"  dictionary="kKnwldgId"  width="120" query="true" extend="{style:'width:200px;'}"></t:dgCol>
   <t:dgCol title="名称" field="name"   width="120" query="true" extend="{style:'min-width:200px;'}"></t:dgCol>
   <t:dgCol title="状态" field="status" dictionary="ruleStatus" width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgFunOpt funname="openBizObject(id,name)" title="测试"></t:dgFunOpt>
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
 	
 	function openBizObject(id,name){
  	        var rowsData = $('#ruleProdList').datagrid('getSelections');
 	        var url = "ruleTesterController.do?ruleTesterProd&id="+id+"&prodName="+name;
 	       createdetailwindow('测试决策方案', url, '100%','100%');
 	}
 	
</script>