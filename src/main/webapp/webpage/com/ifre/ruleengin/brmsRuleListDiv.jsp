<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>

<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<script type = "text/javascript"> 
$(function() {
    storage = $.localStorage;
    if (!storage) storage = $.cookieStorage;
    $('#brmsRuleTableList').treegrid({
        idField: 'id',
        treeField: 'text',
        title: '数据维护',
        url: 'brmsRuleTableController.do?ruleTreeGrid',
        fit: true,
        loadMsg: '数据加载中...',
        pageSize: 10,
        pagination: false,
        pageList: [10, 20, 30],
        sortOrder: 'asc',
        rownumbers: true,
        singleSelect: true,
        fitColumns: true,
        striped: true,
        showFooter: true,
        frozenColumns: [[]],
        columns: [[{
            field: 'id',
            title: '编号',
            hidden: true
        },
        {
            field: 'text',
            title: 'DEMO名称',
            width: 100
        },
        {
            field: 'null',
            title: '操作',
            width: 100,
            formatter: function(value, rec, index) {
                if (!rec.id) {
                    return '';
                }
                var href = '';
                href += "[<a href='#' onclick=delObj('brmsRuleTableController.do?del&id=" + rec.id + "','brmsRuleTableList')>";
                href += "删除</a>]";
                return href;
            }
        }]],
        onLoadSuccess: function(data) {
            $("#brmsRuleTableList").treegrid("clearSelections");
        },
        onClickRow: function(rowData) {
            rowid = rowData.id;
            gridname = 'brmsRuleTableList';
        }
    });
    $('#brmsRuleTableList').treegrid('getPager').pagination({
        beforePageText: '',
        afterPageText: '/{pages}',
        displayMsg: '{from}-{to}共 {total}条',
        showPageList: true,
        showRefresh: true
    });
    $('#brmsRuleTableList').treegrid('getPager').pagination({
        onBeforeRefresh: function(pageNumber, pageSize) {
            $(this).pagination('loading');
            $(this).pagination('loaded');
        }
    });
    try {
        restoreheader();
    } catch(ex) {}
    
    $('#ccc').combotree({
		  url : 'departController.do?setPFunction&selfId=${depart.id}',
	      width: 155,
	      onSelect : function(node) {
	      //  alert(node.text);
	      //  changeOrgType();
	      }
 	 });   
});
function reloadTable() {
    try {
        $('#' + gridname).datagrid('reload');
        $('#' + gridname).treegrid('reload');
    } catch(ex) {}
}
function reloadbrmsRuleTableList() {
    $('#brmsRuleTableList').treegrid('reload');
}
function getbrmsRuleTableListSelected(field) {
    return getSelected(field);
}
function getSelected(field) {
    var row = $('#' + gridname).treegrid('getSelected');
    if (row != null) {
        value = row[field];
    } else {
        value = '';
    }
    return value;
}
function getbrmsRuleTableListSelections(field) {
    var ids = [];
    var rows = $('#brmsRuleTableList').treegrid('getSelections');
    for (var i = 0; i < rows.length; i++) {
        ids.push(rows[i][field]);
    }
    ids.join(',');
    return ids
};
function getSelectRows() {
    return $('#brmsRuleTableList').datagrid('getChecked');
}
function saveHeader() {
    var columnsFields = null;
    var easyextends = false;
    try {
        columnsFields = $('#brmsRuleTableList').datagrid('getColumns');
        easyextends = true;
    } catch(e) {
        columnsFields = $('#brmsRuleTableList').datagrid('getColumnFields');
    }
    var cols = storage.get('brmsRuleTableListhiddenColumns');
    var init = true;
    if (cols) {
        init = false;
    }
    var hiddencolumns = [];
    for (var i = 0; i < columnsFields.length; i++) {
        if (easyextends) {
            hiddencolumns.push({
                field: columnsFields[i].field,
                hidden: columnsFields[i].hidden
            });
        } else {
            var columsDetail = $('#brmsRuleTableList').datagrid("getColumnOption", columnsFields[i]);
            if (init) {
                hiddencolumns.push({
                    field: columsDetail.field,
                    hidden: columsDetail.hidden,
                    visible: (columsDetail.hidden == true ? false: true)
                });
            } else {
                for (var j = 0; j < cols.length; j++) {
                    if (cols[j].field == columsDetail.field) {
                        hiddencolumns.push({
                            field: columsDetail.field,
                            hidden: columsDetail.hidden,
                            visible: cols[j].visible
                        });
                    }
                }
            }
        }
    }
    storage.set('brmsRuleTableListhiddenColumns', JSON.stringify(hiddencolumns));
}
function restoreheader() {
    var cols = storage.get('brmsRuleTableListhiddenColumns');
    if (!cols) return;
    for (var i = 0; i < cols.length; i++) {
        try {
            if (cols.visible != false) $('#brmsRuleTableList').datagrid((cols[i].hidden == true ? 'hideColumn': 'showColumn'), cols[i].field);
        } catch(e) {}
    }
}
function resetheader() {
    var cols = storage.get('brmsRuleTableListhiddenColumns');
    if (!cols) return;
    for (var i = 0; i < cols.length; i++) {
        try {
            $('#brmsRuleTableList').datagrid((cols.visible == false ? 'hideColumn': 'showColumn'), cols[i].field);
        } catch(e) {}
    }
}
function brmsRuleTableListsearch() {
    if ($("#brmsRuleTableListForm").Validform({
        tiptype: 3
    }).check()) {
        var queryParams = $('#brmsRuleTableList').datagrid('options').queryParams;
        $('#brmsRuleTableListtb').find('*').each(function() {
            queryParams[$(this).attr('name')] = $(this).val();
        });
        $('#brmsRuleTableList').treegrid({
            url: 'demoController.do?demoGrid&field=id,functionName,functionUrl,',
            pageNumber: 1
        });
    }
}
function dosearch(params) {
    var jsonparams = $.parseJSON(params);
    $('#brmsRuleTableList').treegrid({
        url: 'demoController.do?demoGrid&field=id,functionName,functionUrl,',
        queryParams: jsonparams
    });
}
function brmsRuleTableListsearchbox(value, name) {
    var queryParams = $('#brmsRuleTableList').datagrid('options').queryParams;
    queryParams[name] = value;
    queryParams.searchfield = name;
    $('#brmsRuleTableList').treegrid('reload');
}
$('#brmsRuleTableListsearchbox').searchbox({
    searcher: function(value, name) {
        brmsRuleTableListsearchbox(value, name);
    },
    menu: '#brmsRuleTableListmm',
    prompt: '请输入查询关键字'
});
function EnterPress(e) {
    var e = e || window.event;
    if (e.keyCode == 13) {
        brmsRuleTableListsearch();
    }
}
function searchReset(name) {
    $("#" + name + "tb").find(":input").val("");
    var queryParams = $('#brmsRuleTableList').datagrid('options').queryParams;
    $('#brmsRuleTableListtb').find('*').each(function() {
        queryParams[$(this).attr('name')] = $(this).val();
    });
    $('#brmsRuleTableList').treegrid({
        url: 'demoController.do?demoGrid&field=id,functionName,functionUrl,',
        pageNumber: 1
    });
} 

</script>
<table width="100%" id="brmsRuleTableList"
	toolbar="#brmsRuleTableListtb"></table>
<div id="brmsRuleTableListtb" style="padding: 3px; height: auto">
	<div name="searchColums">
		<input id="_sqlbuilder" name="sqlbuilder" type="hidden" />
		<form id='brmsRuleTableListForm'>
			<link rel="stylesheet" href="plug-in/Validform/css/style.css"
				type="text/css">
			<link rel="stylesheet" href="plug-in/Validform/css/tablefrom.css"
				type="text/css">
			<script type="text/javascript"
				src="plug-in/Validform/js/Validform_v5.3.1_min_zh-cn.js"></script>
			<script type="text/javascript"
				src="plug-in/Validform/js/Validform_Datatype_zh-cn.js"></script>
			<script type="text/javascript"
				src="plug-in/Validform/js/datatype_zh-cn.js"></script>
			<span style="display: -moz-inline-box; display: inline-block;"><span
				style="vertical-align: middle; display: -moz-inline-box; display: inline-block; width: 80px; text-align: right; text-overflow: ellipsis; -o-text-overflow: ellipsis; overflow: hidden; white-space: nowrap;"
				title="组织机构">组织机构：</span><input id="ccc" name="orgId" value=""></span><span
				style="display: -moz-inline-box; display: inline-block;"><span
				style="vertical-align: middle; display: -moz-inline-box; display: inline-block; width: 80px; text-align: right; text-overflow: ellipsis; -o-text-overflow: ellipsis; overflow: hidden; white-space: nowrap;"
				title="产品">产品：</span><select name="knowId" WIDTH="100"
				style="width: 134px">
					<option value="">---请选择---</option>
					<option value="4028d0f057adfbfa0157ae0750ed0017">124</option>
			</select></span><span style="display: -moz-inline-box; display: inline-block;"><span
				style="vertical-align: middle; display: -moz-inline-box; display: inline-block; width: 80px; text-align: right; text-overflow: ellipsis; -o-text-overflow: ellipsis; overflow: hidden; white-space: nowrap;"
				title="产品">产品：</span><select name="prodId" WIDTH="100"
				style="width: 134px">
					<option value="">---请选择---</option>
					<option value="4028d0f057adfbfa0157ae0777100019">235</option>
			</select></span>
		</form>
	</div>
	<div style="height: 30px;" class="datagrid-toolbar">
		<span style="float: left;"><a href="#"
			class="easyui-linkbutton" plain="true" icon="icon-newupload"
			onclick="add('上传','fileController.do?uploadview','brmsRuleTableList',null,null)">上传</a><a
			href="#" class="easyui-linkbutton" plain="true" icon="icon-newedit"
			onclick="update('编辑','brmsRuleTableController.do?editDecitable','brmsRuleTableList','100%','100%')">编辑</a></span><span
			style="float: right"><a href="#" class="easyui-linkbutton"
			iconCls="icon-search" onclick="brmsRuleTableListsearch()">查询</a><a
			href="#" class="easyui-linkbutton" iconCls="icon-reload"
			onclick="searchReset('brmsRuleTableList')">重置</a></span>
	</div>