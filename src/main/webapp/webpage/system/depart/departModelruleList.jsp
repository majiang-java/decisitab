<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<input id="departid" name="departid" type="hidden" value="${departid}">
<t:datagrid name="departModelruleList" title="common.operation"
            actionUrl="departController.do?modelruleDatagrid&departId=${departid}" fit="true" fitColumns="true" idField="id" queryMode="group">
	<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="类别" field="type" replace="模型_1,规则_2" query="true"></t:dgCol>
	<t:dgCol title="方案名" sortable="false" field="name" query="true"></t:dgCol>
	<t:dgCol title="common.status" sortable="true" field="rightStatus" replace="激活_1,关闭_0"></t:dgCol>
	<t:dgCol title="common.operation" field="opt"></t:dgCol>
	<t:dgFunOpt title="激活" funname="activate(title,id)"></t:dgFunOpt>
	<t:dgFunOpt title="关闭" funname="inactivate(title,id)"></t:dgFunOpt>
	<t:dgToolBar title="全部激活" icon="icon-edit" url="ruleProdController.do?allactivate&departid=${departid}&activatevalue=1" funname="allactivate"></t:dgToolBar>
	<t:dgToolBar title="全部关闭" icon="icon-edit" url="ruleProdController.do?allactivate&departid=${departid}&activatevalue=0" funname="allinactivate"></t:dgToolBar>
</t:datagrid>


<script type="text/javascript">

function activate(title,id) {
	var url = "ruleProdController.do?activate&id=" + id + "&activatevalue=1";
	$.dialog.confirm('确认激活方案吗', function(){
		lockuploadify(url, '&id');
	}, function(){
	});
}
function inactivate(title,id) {
	var url="ruleProdController.do?activate&id=" + id + "&activatevalue=0";
	$.dialog.confirm('确认关闭方案吗', function(){
		lockuploadify(url, '&id');
	}, function(){
	});
}

function allactivate(title,url, departid) {
	$.dialog.confirm('确认激活全部方案吗', function(){
		alllockuploadify(url, '${departid}');
	}, function(){
	});
}
function allinactivate(title,url, departid) {
	$.dialog.confirm('确认关闭全部方案吗', function(){
		alllockuploadify(url, '${departid}');
	}, function(){
	});
}

function alllockuploadify(url, id) {
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : url,// 请求的action路径
		error : function() {// 请求失败处理函数
		
		
		},
		success : function(data) {
			var d = $.parseJSON(data);
			if (d.success) {
			var msg = d.msg;
				tip(msg);
				reloadTable();
				$('#modelruleListpanel').panel("refresh", "departController.do?modelruleList&departid=" + id);
			}
			
		}
	});
}

function lockuploadify(url, id) {
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : url,// 请求的action路径
		error : function() {// 请求失败处理函数
		
		
		},
		success : function(data) {
			var d = $.parseJSON(data);
			if (d.success) {
			var msg = d.msg;
				tip(msg);
				reloadTable();
			}
			
		}
	});
}

</script>