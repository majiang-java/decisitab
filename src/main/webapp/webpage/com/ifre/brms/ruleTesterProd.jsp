<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
 <t:base type="jquery,easyui,tools,DatePicker"></t:base>
<style>
	.label{
	   vertical-align:middle;
	   display:-moz-inline-box;
	   display:inline-block;
	   width: 80px;
	   text-align:right;
	   text-overflow:ellipsis;
	   -o-text-overflow:ellipsis; 
	   overflow: hidden;
	   white-space:nowrap;
       margin-left: 16em;
       margin-right: 20px;
	}
</style>
<div id="cc" class="easyui-layout" fit="true">  
  	 <input type="hidden" value="${id }">
     <div region="west" title="输入区域" style="width:700em;">
	     <div class="easyui-tabs"  fit="true">  
	     	<!-- 	<div data-options="region:'center'" title="模型输入" style="width:400px">
	     			<table id="tt"></table>
	     		</div>
				<div data-options="region:'east'" title="预测输入(非必输)" style="width:300px">
					<table id="pretest"></table>
				</div> -->
				<div title="模型输入" iconCls='icon-ok'  style="background:#eee;">
					<!-- <div class="headContent " style="display:-moz-inline-box;display:inline-block;">
						<span class="label">模型类型</span><input readonly id="modelType" />
					</div> -->
		       		<table id="tt"></table>
				</div>  
		        <div title="预测值输入" closable="false"  style="background:#eee;">
		       		<table id="pretest"></table>
		       </div>  
	     </div>
     	
     </div>  
     <div region="center"  title="显示区域" fit="true">
     	  <div class="easyui-tabs" fit="true">  
		       <div title="数据结果" iconCls='icon-ok'  style="background:#eee;">
		       		<table id="resultTab" ></table>
				</div>  
		       <div title="预测结果" closable="false"  style="background:#eee;">
		       		<table id="pretestResult" ></table>
		       </div>  
   		 </div>  
     </div>   
</div>   

<script type="text/javascript">
var dataList = [];
var id= '${id}';
var prodname = '${prodName}';
var predDataGlobel;
$(function(){
	var modelTypeList = [{key:"",text:""},{key:"71",text:"反欺诈"},{key:"25",text:"违约"}];
	
	$("#modelType").combobox({
		 valueField:'key',
		 textField:'text',
		 data:modelTypeList,
		 required:true,
		 editable:false
	});
	
	
	$.post("ruleTesterController.do?getTesterSelectData",{id:id},function(res){
		for(var i = 0; i< res.length; i++){
			var a = {};
			a.text = res[i].text;
			a.value = res[i].value;
			dataList.push(a);
		}
		//dataList.pop();
		//console.log(dataList);
		$('#tt').datagrid({
			fit:true,
			title:prodname,
			iconCls:'icon-edit',
			singleSelect:true,
			idField:'itemid',
			onClickRow: onClickRow,
			//url:'data/datagrid_data.json',
			columns:[[
				{field:'testKey',title:'测试值',width:250,
				/* 	formatter:function(value){
						debugger;
						for(var i=0; i<dataList.length; i++){
							if (dataList[i].value == value) return dataList[i].text;
						}
						return value;
					}, */
					editor:{
						type:'combobox',
						options:{
							valueField:'value',
							textField:'text',
							data:dataList,
							required:true
						}
					}
				},
				{field:'value',title:'值',width:100,align:'right',editor:'text'},
				{field:'description',title:'描述',width:100,align:'right',editor:'text'},
				
			]],
			toolbar:[{
				text:'添加',
				iconCls:'icon-add',
				handler:function(){insertOne();}
			},'-',{
				text:'删除',
				iconCls:'icon-cut',
				handler:function(){removeit();}
			},'-',{
				text:'临时保存',
				iconCls:'icon-save',
				handler:function(){accept();}
			},'-',{
				text:'开始测试',
				iconCls:'icon-ok',
				handler:function(){mainTest();}
			},'-',{
				text:'产品发布',
				iconCls:'icon-redo',
				handler:function(){showPablish();}
			}]
			
		});
	},"json");
	$.post("ruleTesterController.do?getPredData",{id:id},function(predData){
		predDataGlobel = predData;
		$("#pretest").datagrid({
			singleSelect:true,
			collapsible:true,
			data:predData,
			method:'post',
			columns:[[/* {field:'code',title:'代码',width:100,align:'right'}, */
			          {field:'name',title:'名称',width:100,align:'right'},
			          {field:'value',title:'值',width:80,align:'right',editor:'text'}
			          ]],
			onClickRow: onClickPreRow
			          
		});

		
	},"json");

  });



	var editIndex = undefined;
	function endEditing(){
		if (editIndex == undefined){return true}
		if ($('#tt').datagrid('validateRow', editIndex)){
			$('#tt').datagrid('endEdit', editIndex);
			editIndex = undefined;
			return true;
		} else {
			return false;
		}
	}
	function onClickRow(index){
		if (editIndex != index){
			if (endEditing()){
				$('#tt').datagrid('selectRow', index)
						.datagrid('beginEdit', index);
				editIndex = index;
			} else {
				$('#tt').datagrid('selectRow', editIndex);
			}
		}
	}
	function removeit(){
		if (editIndex == undefined){return}
		$('#tt').datagrid('cancelEdit', editIndex)
				.datagrid('deleteRow', editIndex);
		editIndex = undefined;
	}
	function accept(){
		if (endEditing()){
			$('#tt').datagrid('acceptChanges');
		}
	}

	var editPretestIndex = undefined;
	var    arr=$('#dg').datagrid('getData');
	function endMainEditing(){
		if (editPretestIndex == undefined){return true}
		if ($('#pretest').datagrid('validateRow', editPretestIndex)){
			$('#pretest').datagrid('endEdit', editPretestIndex);
			editPretestIndex = undefined;
			return true;
		} else {
			return false;
		}
	}
	function onClickPreRow(index){
		$('#pretest').datagrid('acceptChanges');
		if (editPretestIndex != index){
			if (endMainEditing()){
				$('#pretest').datagrid('selectRow', index)
						.datagrid('beginEdit', index);
				editIndex = index;
			} else {
				$('#tt').datagrid('selectRow', editIndex);
			}
		}
	}

function insertOne(){
	var row = $('#tt').datagrid('getSelected');
	if (row){
		var index = $('#tt').datagrid('getRowIndex', row);
	} else {
		index = 0;
	}
	$('#tt').datagrid('insertRow', {
		index: index,
		row:{
		}
	});
	$('#tt').datagrid('selectRow',index);
	$('#tt').datagrid('beginEdit',index);
}

function mainTest(){
	$('#tt').datagrid('acceptChanges');
	var arr=$('#tt').datagrid('getData');
	var colRows = arr.rows;
/* 	var modeltype = {};
	modeltype.testKey = 'type'
	modeltype.value = $('#modelType').combobox('getValue');
	colRows.push(modeltype);
	arr.rows = colRows;
	arr.total = arr.total + 1; */
	var arrStr = JSON.stringify(arr);
	$.post("ruleTesterController.do?mainTest",{data:arrStr,id:id},function(datec){
		//console.log(res);
		
		if(datec.success){
			var res = datec.obj;
			debugger;
			var row = [];
		/* 	for(var k in res) {
				var a = {};
			   // console.log(obj[k]);
			   var predRows = predDataGlobel.rows;
			    for(var i = 0; i < predRows.length;i++ ){
			    	if(predRows[i].code === k){
			    		a.desc = predRows[i].name;
			    	}
			    }
			 	a.code = k;
			 	a.value =  res[k];
			 	row.push(a);
			} */
			var predRows = predDataGlobel.rows;
		    for(var i = 0; i < predRows.length;i++ ){
		    	var a = {};
		    	for(var k in res) {
			    	if(predRows[i].code === k){
			    		a.desc = predRows[i].name;
			    		a.code = k;
			    		a.value = res[k];
			    	}
		    	}
		    	row.push(a);
		    }
			var maindata = {};
			maindata.total = row.length;
			maindata.rows = row;
			
			$("#resultTab").datagrid({
				singleSelect:true,
				collapsible:true,
				data:maindata,
				fit:true,
				method:'post',
				columns:[[/* {field:'code',title:'代码',width:150,align:'right'}, */
				          {field:'desc',title:'描述',width:150,align:'right'},
				          {field:'value',title:'值',width:200,align:'right',editor:'text'}
				          ]],
				          
			});
			
			var clkc  = [];
			$('#pretest').datagrid('acceptChanges');
			var pretestCls=$('#pretest').datagrid('getData');
			
			//console.log(pretestCls);
			var pretestClsTest = pretestCls.rows;
			for(var i = 0; i < pretestClsTest.length; i++){
				var a = {};
				var pretestObj = pretestClsTest[i];
				for(var j=0; j< row.length; j++){
					if(pretestObj.code === row[j].code){
						a.code = row[j].code;
						a.descp = row[j].desc;
						a.value = row[j].value;
						if(pretestObj.value ==row[j].value){
							a.image = 1;
						}else{
							a.image = 2;
						}
					}
				}
				clkc.push(a);
			}
			
			var resultTabResult = {};
			resultTabResult.rows = clkc;
			resultTabResult.total = clkc.length;
			console.log(resultTabResult);
			$("#pretestResult").datagrid({
				singleSelect:true,
				collapsible:true,
				data:resultTabResult,
				fit:true,
				method:'post',
				columns:[[/* {field:'code',title:'代码',width:150,align:'right'}, */
				          {field:'descp',title:'描述',width:150,align:'right'},
				          {field:'value',title:'值',width:200,align:'right',editor:'text'},
				          {field:'image',title:'结果',width:150,height:80,align:'left',
				        	  formatter:function(value, rec){//使用formatter格式化刷子
				        		  if(value == 1){
				        	 		 return '<img src="./plug-in/easyui/themes/icons/ok.png"/>';
				        		  }else{
				        			  return '<img src="./plug-in/easyui/themes/icons/no.png"/>';
				        		  }
				        	  }},
				          
				          ]],
				          
			});
		}else{
			tip(datec.msg);
		}
	},"json");
}

function showPablish(){
	 var url = "ruleTesterController.do?showPublishPage&id="+id+"&prodName="+name;
	 createdetailwindow('决策方案发布', url);
}



</script>

