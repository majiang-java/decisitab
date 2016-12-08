<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  		<table id="pretest"></table>
  </div>
 </div>
 
 <script type="text/javascript">
  var id= '${id}';
  var prodname = '${prodName}';
  $(function(){
 		$("#pretest").datagrid({
			singleSelect:true,
			collapsible:true,
			fit:true,
			columns:[[
			          {field:'value',title:'发布路径',width:600,align:'left',editor:'text'}
			          ]],
			onClickRow: onClickRow,
			toolbar:[
			 {
				text:'发布',
				iconCls:'icon-redo',
				handler:function(){publish();}
			},'-',{
				text:'添加发布路径',
				iconCls:'icon-add',
				handler:function(){insert();}
			},'-',{
				text:'删除',
				iconCls:'icon-cut',
				handler:function(){removeit();}
			}]
			          
		});
 		
 		var editIndex = undefined;
 		function endEditing(){
 			if (editIndex == undefined){return true}
 			if ($('#pretest').datagrid('validateRow', editIndex)){
 				$('#pretest').datagrid('endEdit', editIndex);
 				editIndex = undefined;
 				return true;
 			} else {
 				return false;
 			}
 		}
 		function onClickRow(index){
 			if (editIndex != index){
 				if (endEditing()){
 					$('#pretest').datagrid('selectRow', index)
 							.datagrid('beginEdit', index);
 					editIndex = index;
 				} else {
 					$('#pretest').datagrid('selectRow', editIndex);
 				}
 			}
 		}
 		function removeit(){
 			$('#pretest').datagrid('acceptChanges');
 			if (editIndex == undefined){return}
 			$('#pretest').datagrid('cancelEdit', editIndex)
 					.datagrid('deleteRow', editIndex);
 			editIndex = undefined;
 		}
 		function accept(){
 			if (endEditing()){
 				$('#pretest').datagrid('acceptChanges');
 			}
 		}

 		function insert(){
 				var row = $('#pretest').datagrid('getSelected');
 				if (row){
 					var index = $('#pretest').datagrid('getRowIndex', row);
 				} else {
 					index = 0;
 				}
 				$('#pretest').datagrid('insertRow', {
 					index: index,
 					row:{
 					}
 				});
 				$('#pretest').datagrid('selectRow',index);
 				$('#pretest').datagrid('beginEdit',index);
 		}
 		
 		function publish(){
 			$('#pretest').datagrid('acceptChanges');
 			var url = "ruleTesterController.do?prodPublish";
 			var allPath =$('#pretest').datagrid('getData');
 			var pathstr = "";
 			for(var i = 0; i< allPath.rows.length;i++){
 				if(allPath.rows[i].value){
 					pathstr +=  allPath.rows[i].value+"#";
 				}
 			}
 			$.post(url,{id:id,pathstr:pathstr},function(data){
 				if(data.success){
 					tip(data.msg);
 					var win = frameElement.api.opener;
 					win.reloadTable();
 				}else{
 					tip(data.msg);
 				}
 				
 				
 			},"json")
 			
 		}
 	});
 </script>