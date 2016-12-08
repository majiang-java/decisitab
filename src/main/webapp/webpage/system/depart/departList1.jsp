<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="main_depart_list" class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
        <t:datagrid name="departList" title="common.department.list"
                    actionUrl="departController.do?departgrid&listFlag=yes"
                    treegrid="true" idField="departid" pagination="false" checkbox="true" >
            <t:dgCol title="common.id" field="id" treefield="id" hidden="true"></t:dgCol>
            <t:dgCol title="common.department.name" field="departname" treefield="text"></t:dgCol>
            <t:dgCol title="position.desc" field="description" treefield="src"></t:dgCol>
            <t:dgCol title="开通日期" field="startDate" treefield="fieldMap.startDate" formatter="yyyy年MM月dd日"></t:dgCol>
            <t:dgCol title="结束日期" field="endDate" treefield="fieldMap.endDate" formatter="yyyy年MM月dd日"></t:dgCol>
            <t:dgCol title="账户余额" field="balance" treefield="fieldMap.balance"></t:dgCol>
            <t:dgCol title="状态" field="status" treefield="fieldMap.status" replace="激活_1,关闭_0"></t:dgCol>
            <t:dgCol title="common.operation" field="opt"></t:dgCol>
            <t:dgDelOpt url="departController.do?del&id={id}" title="common.delete" operationCode="delete"></t:dgDelOpt>
            <t:dgFunOpt funname="queryModelsByDepart(id)" title="机构权限" operationCode="property"></t:dgFunOpt>
           <t:dgToolBar title="common.add.param" langArg="common.department" icon="icon-add" funname="addOrg" operationCode="add"></t:dgToolBar> 
            <t:dgToolBar title="common.edit.param" langArg="common.department" icon="icon-edit" url="departController.do?update" funname="update" operationCode="edit"></t:dgToolBar>
        </t:datagrid>
      
    </div>
</div>
<div data-options="region:'east',
	title:'机构权限',
	collapsed:true,
	split:true,
	border:false,
	onExpand : function(){
		li_east = 1;
	},
	onCollapse : function() {
	    li_east = 0;
	}"
	style="width: 400px; overflow: hidden;" id="eastPanel">
    <div class="easyui-panel" style="padding:0px;border:0px" fit="true" border="false" id="modelruleListpanel"></div>
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
<!--
    $(function() {
        var li_east = 0;
    });
	function addOrg() {
		var id = "";
		var realName = "${user.userName}";
		var rowsData = $('#departList').datagrid('getSelections');
		if (realName == "admin"){
	      if (rowsData.length == 1) {
	          id = rowsData[0].id;
	      }
		} else {
	    	if (!rowsData || rowsData.length==0) {
	    		tip('请选择新机构所属上级机构');
	    		return;
	    	} else {
	    		id = rowsData[0].id;
	    	}
		}
	    var url = "departController.do?add&id=" + id;
	    add('<t:mutiLang langKey="common.add.param" langArg="common.department"/>', url, "departList");
	}

    function queryModelsByDepart(departid){
        var title = '机构权限';
        if(li_east == 0 || $('#main_depart_list').layout('panel','east').panel('options').title != title){
            $('#main_depart_list').layout('expand','east');
        }
        <%--$('#eastPanel').panel('setTitle','<t:mutiLang langKey="member.list"/>');--%>
        $('#main_depart_list').layout('panel','east').panel('setTitle', title);
        $('#main_depart_list').layout('panel','east').panel('resize', {width: 500});
        $('#modelruleListpanel').panel("refresh", "departController.do?modelruleList&departid=" + departid);
    }
//-->
</script>