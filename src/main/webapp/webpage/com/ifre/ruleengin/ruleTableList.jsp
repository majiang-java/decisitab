<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>

<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<link href="./css/skin.css" rel="stylesheet" />

<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  		<div id="brmsRuleTableList"  toolbar="#brmsRuleTableListtb"></div>
  		<div id="brmsRuleTableListtb" style="padding:3px; height: auto">
    <div name="searchColums">
        <input id="_sqlbuilder" name="sqlbuilder" type="hidden" />
        <form id='brmsRuleTableListForm'>
            <input id="isSearch" name="isSearch" value="1" type="hidden" />
            <link rel="stylesheet" href="plug-in/Validform/css/style.css" type="text/css">
            <link rel="stylesheet" href="plug-in/Validform/css/tablefrom.css" type="text/css">
            <script type="text/javascript" src="plug-in/Validform/js/Validform_v5.3.1_min_zh-cn.js">
            </script>
            <script type="text/javascript" src="plug-in/Validform/js/Validform_Datatype_zh-cn.js">
            </script>
            <script type="text/javascript" src="plug-in/Validform/js/datatype_zh-cn.js">
            </script>
            <span style="display:-moz-inline-box;display:inline-block;">
                <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 80px;text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; "
                title="机构">
                    机构：
                </span>
                <input id="ccc" name="orgId" value="">
            </span>
            <span style="display:-moz-inline-box;display:inline-block;">
                <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 80px;text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; "
                title="产品">
                    产品：
                </span>
                <input onkeypress="EnterPress(event)" onkeydown="EnterPress()" type="text"
                name="prodId" class="inuptxt" style="width: 100px" />
            </span>
        </form>
    </div>
    <div style="height:30px;" class="datagrid-toolbar">
        <span style="float:left;">
            <a href="#" class="easyui-linkbutton" plain="true" icon="icon-edit" onclick="add('创建','brmsRuleTableController.do?createDecitable&prodType=${prodType}','brmsRuleTableList',null,null)">
                创建
            </a>
            <a href="#" class="easyui-linkbutton" plain="true" icon="icon-edit" onclick="update('编辑','brmsRuleTableController.do?editDecitable','brmsRuleTableList','100%','100%')">
                编辑
            </a>
        </span>
        <span style="float:right">
            <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="brmsRuleTableListsearch()">
                查询
            </a>
            <a href="#" class="easyui-linkbutton" iconCls="icon-reload" onclick="searchReset('brmsRuleTableList')">
                重置
            </a>
        </span>
    </div>
  </div>
  
 </div>
 <script>
 	var prodType = ${prodType};
 	$(function(){
 		
 		 $('#brmsRuleTableList').treegrid({
 		      idField: 'id',
 		      treeField: 'text',
 		      title: '决策表',
 		      url: 'brmsRuleTableController.do?selfRuleTreeGrid&prodType='+prodType+'&field=id,orgId,prodId,salience,',
 		      fit: true,
 		      queryParams: {},
 		      closed: false,  
 		      loadMsg: '数据加载中...',
 		      pageSize: 20,
 		      pagination: true,
 		      pageList: [20, 40, 60],
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
 		        field: 'note',
 		        title: '机构',
 		        width: 120,
 		      },
 		      {
 		        field: 'text',
 		        title: '产品',
 		        width: 120
 		      },
 		      {
 		        field: 'src',
 		        title: '优先级',
 		        width: 120
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
 		          href += "[<a href='#' onclick=delObj('brmsRuleTableController.do?del&id=" + rec.id + "&src=" + rec.salience + "','brmsRuleTableList')>";
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
 	
 			 $('#ccc').combotree({
 				url : 'departController.do?setPFunction&selfId=${depart.id}',
 		       width: 155,
 		       onSelect : function(node) {
 		       //  alert(node.text);
 		       //  changeOrgType();
 		       }
 		   });   
 		 
 		 
 	});
    function brmsRuleTableListsearch() {
        if ($("#brmsRuleTableListForm").Validform({
            tiptype: 3
        }).check()) {
            var queryParams = $('#brmsRuleTableList').datagrid('options').queryParams;
            $('#brmsRuleTableListtb').find('*').each(function() {
                queryParams[$(this).attr('name')] = $(this).val();
            });
            $('#brmsRuleTableList').treegrid({
                url: 'brmsRuleTableController.do?selfRuleTreeGrid&prodType='+prodType+'&field=id,orgId,prodId,salience,',
                pageNumber: 1
            });
        }
    }
    function reloadTable() {
        try {
            $('#' + gridname).datagrid('reload');
            $('#' + gridname).treegrid('reload');
        } catch(ex) {}
    }
 </script>