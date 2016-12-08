



<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>决策打包</title>
<script type="text/javascript" src="plug-in/mutiLang/zh-cn.js">
</script><script type="text/javascript" src="plug-in/jquery/jquery-1.8.3.js">
</script><script type="text/javascript" src="plug-in/jquery/jquery.cookie.js" >
</script><script type="text/javascript" src="plug-in/jquery-plugs/storage/jquery.storageapi.min.js" >
</script><script type="text/javascript" src="plug-in/tools/dataformat.js">
</script>
<link id="easyuiTheme" rel="stylesheet" href="plug-in/easyui/themes/default/easyui.css" type="text/css"></link>
<link rel="stylesheet" href="plug-in/easyui/themes/icon.css" type="text/css"></link>
 <link rel="stylesheet" href="plug-in/Validform/css/divfrom.css" type="text/css"/>
 <link rel="stylesheet" href="plug-in/Validform/css/style.css" type="text/css"/>
 <link rel="stylesheet" href="plug-in/Validform/css/tablefrom.css" type="text/css"/>
<link rel="stylesheet" type="text/css" href="plug-in/accordion/css/accordion.css">
<script type="text/javascript" src="plug-in/easyui/jquery.easyui.min.1.3.2.js">
</script><script type="text/javascript" src="plug-in/easyui/locale/zh-cn.js">
</script><script type="text/javascript" src="plug-in/tools/syUtil.js">
</script><script type="text/javascript" src="plug-in/easyui/extends/datagrid-scrollview.js">
</script><link rel="stylesheet" href="plug-in/tools/css/common.css" type="text/css"></link>
<script type="text/javascript" src="plug-in/lhgDialog/lhgdialog.min.js">
</script><script type="text/javascript" src="plug-in/tools/curdtools_zh-cn.js">
</script><script type="text/javascript" src="plug-in/tools/easyuiextend.js">
</script><script type="text/javascript" src="plug-in/jquery-plugs/hftable/jquery-hftable.js"></script>
<script type="text/javascript" src="plug-in/tools/json2.js" ></script>
<script type="text/javascript">
 	function cal(data){
		if (data.success) {
			parent.tip(data.msg);
			parent.reloadTable();
			$("#btnreset_1").click();//清空表单
		}else{
			parent.$.messager.alert("错误",data.msg);
		}
	} 
	
	$(document).ready(function(){
 		var a=document.getElementById("btnsub_1");
		var b=document.getElementById("btnreset_1");
		if(a.value=="配置POM文件")
			b.disabled=1; 
	})
	
	function makePom(){
		var data1=($('#formobj').serialize());
		console.info(data1);
		$.ajax({
			type : "POST",
			url : "ruleProdController.do?makePom",
			data : data1,	
			success : function(data) {
				//var message="接口调用结果："+JSON.parse(JSON.parse(data).obj).status+"\n"+"日志信息："+JSON.parse(JSON.parse(data).obj).msg;
				console.info(JSON.parse(data).obj);
				document.getElementById("btnsub_1").value="开始打包";
				document.getElementById("labelinfo").innerHTML="配置文件生成成功!";
				document.getElementById("btnreset_1").disabled=false;
			},
			error : function(data) {
				var result=data.responseText;
				console.info("result:" + result);
				 document.getElementById("sourceCode").value=result;
			}
		});
	};
	
	
	

	
	//打包方法
	function makeJar(){
		var data1=($('#formobj').serialize());
		console.info(data1);
		$.ajax({
			type : "POST",
			url : "ruleProdController.do?makeJarOuter",
			data : data1,	
			success : function(data) {
				//var message="接口调用结果："+JSON.parse(JSON.parse(data).obj).status+"\n"+"日志信息："+JSON.parse(JSON.parse(data).obj).msg;
				console.info(JSON.parse(data).obj);
				document.getElementById("btnsub_1").value="完成";
				document.getElementById("labelinfo").innerHTML="打包成功!";
				document.getElementById("btnreset_1").disabled=true;
			},
			error : function(data) {
				var result=data.responseText;
				console.info("result:" + result);
				 document.getElementById("sourceCode").value=result;
			}
		});
	};
	
	
	function make(){
		var a=document.getElementById("btnsub_1").value;
		if(a=="配置POM文件"){
			console.info(11111);
			makePom();
		}else if(a=="开始打包"){
			makeJar();
		}else if(a=="完成"){
			 var win = frameElement.api.opener;
	         frameElement.api.close();
	         win.reloadTable();
		}
		
	}
	
    //回退方法
	function returnBack(){
		var a=document.getElementById("btnsub_1");
		var b=document.getElementById("btnreset_1");
		if(a.value=="配置POM文件"){
			b.disabled=true;
		}else if(a.value=="开始打包"){			
			a.value="配置POM文件";
			document.getElementById("labelinfo").innerHTML=null;
		}
		
	}
	
</script>
  
  
<link rel="stylesheet" href="plug-in/easyui/themes/main.css" />

</head>
<body style="overflow-y: hidden" scroll="no">
  <div id="content">
  <div id="wrapperone">
  <div id="steps" >
   <form id="formobj"  action="ruleProdController.do?datagrid" name="formobj" method="post">
     <!-- <input type="hidden" id="btn_sub" class="btn_sub"/>   -->
	<input type="hidden" name="id" id="id" value="${RuleProdEntity.id}">
	<fieldset class="step" >
		<div class="form"><label class="Validform_label" style="width:70px"> 存放地址： </label> <input name="name" class="inputxt" value="${RuleProdEntity.name}"> <span class="Validform_checktip"></span></div>
	<div class="form"><label class="Validform_label" style="width:70px"> POM配置： </label> <textarea cols="89" name="descp" rows="19.5" >${RuleProdEntity.descp}</textarea></div>
	<div class="form" align="right">
	    <label class="Validform_label" id="labelinfo" style="width:300px;color:red"> </label>
		<input id="btnsub_1" type="button" class="button" value="配置POM文件" onclick="make()" data-options="plain:true"/>
		<input id="btnreset_1" type="button" class="button" value="回  退" onclick="returnBack()" data-options="plain:true"/>
	</div>
	</fieldset>

 
 <script type="text/javascript" src="plug-in/Validform/js/Validform_v5.3.1_min_zh-cn.js">
 </script><script type="text/javascript" src="plug-in/Validform/js/Validform_Datatype_zh-cn.js">
 </script><script type="text/javascript" src="plug-in/Validform/js/datatype_zh-cn.js"></script>
 <SCRIPT type="text/javascript" src="plug-in/Validform/plugin/passwordStrength/passwordStrength-min.js"></SCRIPT> 
 <script type="text/javascript">
 $(function(){
	   $("#formobj").Validform({
	      tiptype:4,
/* 	      btnSubmit:"#btn_sub",
	      btnReset:"#btn_reset", */
	      ajaxPost:true,
	      usePlugin: {
	    	         passwordstrength:{minLen:6,maxLen:18,trigger:function(obj,error){if(error){obj.parent().next().find(".Validform_checktip").show();obj.find(".passwordStrength").hide();}else{$(".passwordStrength").show();obj.parent().next().find(".Validform_checktip").hide();}}}
	                 },
	      callback:function(data){
	    	         var win = frameElement.api.opener;if(data.success==true){frameElement.api.close();win.tip(data.msg);}else{if(data.responseText==''||data.responseText==undefined){$.messager.alert('错误', data.msg);$.Hidemsg();}else{try{var emsg = data.responseText.substring(data.responseText.indexOf('错误描述'),data.responseText.indexOf('错误信息')); $.messager.alert('错误',emsg);$.Hidemsg();}catch(ex){$.messager.alert('错误',data.responseText+"");$.Hidemsg();}} return false;}win.reloadTable();
	    	                   }
	                 }
 );});
 </script></form></div></div></div> 
</body>
</html>