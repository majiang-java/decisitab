<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="main_typegroup_list" class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
        <t:datagrid name="typeGridTree" title="模型变量" actionUrl="modelVarController.do?typeGroupGrid&prodType=1" queryMode="group" idField="id" treegrid="false" pageSize="20" sortOrder="desc" sortName="id" onLoadSuccess="loadSuccess">
            <t:dgCol title="common.code" field="id" hidden="true"></t:dgCol>
            <t:dgCol title="变量名称" field="varTypegroupcode" width="100" treefield="code" query="true" extend="{style:'width:150px'}"></t:dgCol>
            <t:dgCol title="变量描述" field="varTypegroupname" width="100" query="true" extend="{style:'width:220px'}"></t:dgCol>
            <t:dgCol title="所属方案" field="ruleProdEntity.name" width="100" query="true" extend="{style:'width:150px'}"></t:dgCol> 
            <t:dgCol title="common.operation" field="opt" width="100"></t:dgCol>
            <t:dgDelOpt url="modelVarController.do?delTypeGroup&id={id}" title="common.delete"></t:dgDelOpt>
            <t:dgFunOpt funname="queryTypeValue(id,varTypegroupname)" title="变量取值"></t:dgFunOpt>
            <t:dgToolBar title="common.add.param" langArg="变量" icon="icon-add" url="modelVarController.do?aouTypeGroup&prodType=1" funname="add"></t:dgToolBar>
            <t:dgToolBar title="common.edit" icon="icon-edit" url="modelVarController.do?aouTypeGroup" funname="update"></t:dgToolBar>
        </t:datagrid>
    </div>
</div>

<div data-options="region:'east',
	title:'mytitle',
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
    <div class="easyui-panel" style="padding:0px;border:0px" fit="true" border="false" id="userListpanel"></div>
</div>

<script type="text/javascript">
    $(function() {
        var li_east = 0;
    });

    function queryTypeValue(typegroupid, varTypegroupname){
        var title = '模型变量: ' + varTypegroupname;
        if(li_east == 0){
            $('#main_typegroup_list').layout('expand','east');
        }
        $('#main_typegroup_list').layout('panel','east').panel('setTitle', title);
        $('#userListpanel').panel("refresh", "modelVarController.do?goTypeGrid&prodType=1&typegroupid=" + typegroupid);
    }
    function loadSuccess() {
        $('#main_typegroup_list').layout('panel','east').panel('setTitle', "");
        $('#main_typegroup_list').layout('collapse','east');
        $('#userListpanel').empty();
    }
</script>
