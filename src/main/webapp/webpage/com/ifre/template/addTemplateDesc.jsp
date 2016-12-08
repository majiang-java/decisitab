<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>模版类型明细</title>
<t:base type="ckfinder,ckeditor,jquery,easyui,tools,DatePicker"></t:base>
<link rel="stylesheet" type="text/css" href="plug-in/jquery-ui/css/ui-lightness/jquery-ui-1.9.2.custom.min.css">
<style type="text/css">
	#wrapper{
	    width: 80% !important;
	}
	#steps{
		width:79% !important;
		height:100% !important;
		    margin: auto !important;
	}
	#formobj{
		margin:auto;
	}
</style>
	<script type="text/javascript">
	function openDepartmentSelect() {
		//$.dialog.setting.zIndex = getzIndex(); 
		var windowapi = frameElement==null?window.top:frameElement.api, W = windowapi==null?window.top:windowapi.opener;//内容页中调用窗口实例对象接口
        var zIndex = W==null?2101:W.$.dialog.setting.zIndex+1;
		var orgIds = $("#orgIds").val();
	
		$.dialog({content: 'url:departController.do?departSelect&orgIds='+orgIds, zIndex: zIndex, title: '组织机构列表', lock: true, width: '400px', height: '350px', opacity: 0.4, button: [
		   {name: '<t:mutiLang langKey="common.confirm"/>', callback: callbackDepartmentSelect, focus: true},
		   {name: '<t:mutiLang langKey="common.cancel"/>', callback: function (){}}
	   ]});
	}
	
	function callbackDepartmentSelect() {
		  var iframe = this.iframe.contentWindow;
		  var treeObj = iframe.$.fn.zTree.getZTreeObj("departSelect");
		  var nodes = treeObj.getCheckedNodes(true);
		  if(nodes.length>0){
		  var ids='',names='';
		  for(i=0;i<nodes.length;i++){
		     var node = nodes[i];
		     ids += node.id+',';
		    names += node.name+',';
		 }
		 $('#departname').val(names);
		 $('#departname').blur();		
		 $('#orgIds').val(ids);		
		}
	}
	
	function callbackClean(){
		$('#departname').val('');
		$('#orgIds').val('');	
	}
	
	$(function(){
		$("#departname").prev().hide();
	});
	</script>
</head>
<body>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="div" action="templateDescController.do?save">
	<input id="id" name="id" type="hidden" value="${templateDesc.id}">
	<table  cellpadding="0" height="100%" cellspacing="1" class="formtable">
		<tr>
			<td align="right"><label class="Validform_label"> 类别code: </label></td>
			<td class="value"><input type="text" name="typecode" value="${ templateDesc.typecode}"></td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label"> 类别名称: </label></td>
			<td class="value"><input type="text" name="typename" value="${ templateDesc.typename}"></td>
			
		</tr>
		<tr>
			<td align="right"><label class="Validform_label"> <t:mutiLang langKey="common.department"/>: </label></td>
			<td class="value">
                <%--<select class="easyui-combobox" data-options="multiple:true, editable: false" id="orgSelect" datatype="*">--%>
                <input id="departname" name="departname" type="text" readonly="readonly" class="inputxt" datatype="*" value="${departname}">
                <input id="orgIds" name="orgIds" type="hidden" value="${orgIds}">
                <a href="#" class="easyui-linkbutton" plain="true" icon="icon-search" id="departSearch" onclick="openDepartmentSelect()">选择</a>
                <a href="#" class="easyui-linkbutton" plain="true" icon="icon-redo" id="departRedo" onclick="callbackClean()">清空</a>
                <span class="Validform_checktip"><t:mutiLang langKey="please.muti.department"/></span>
            </td>
         </tr>
		<tr>
			<td align="right"><label class="Validform_label"> 类别名称: </label></td>
			<td class="value"><t:ckfinder name="image" uploadType="Images" value="${templateDesc.image}" width="100" height="60" buttonClass="ui-button" buttonValue="上传图片"></t:ckfinder></td>
		</tr>
	<%-- 	<tr>
			<td align="right"><label class="Validform_label"> 附件: </label></td>
			<td class="value"><t:ckfinder name="attachment" uploadType="Files" value="${templateDesc.attachment}" buttonClass="ui-button" buttonValue="上传附件"></t:ckfinder></td>
		</tr> --%>
		<tr>
			<td align="right"><label class="Validform_label"> 类别描述: </label></td>
			<td class="value"><t:ckeditor name="remark" isfinder="true" value="${templateDesc.remark}" type="width:750"></t:ckeditor></td>
		</tr>
	</table>
</t:formvalid>
</div>
</div>
</body>