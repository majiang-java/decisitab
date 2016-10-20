<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>

<meta charset="utf-8">
<title>jQuery File Upload Example</title>
<link href="plug-in/jquery-plugs/fileupload/bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet" />
<!-- we code these -->
<link href="plug-in/jquery-plugs/fileupload/css/dropzone.css" type="text/css" rel="stylesheet" />
	<link rel="stylesheet" type="text/css" href="plug-in/showloading/showLoading.css" />
<script src="plug-in/jquery-plugs/fileupload/js/jquery.1.9.1.min.js"></script>

<script src="plug-in/jquery-plugs/fileupload/js/vendor/jquery.ui.widget.js"></script>
<script src="plug-in/jquery-plugs/fileupload/js/jquery.iframe-transport.js"></script>
<script src="plug-in/jquery-plugs/fileupload/js/jquery.fileupload.js"></script>
<!-- bootstrap just to have good looking page -->
<script src="plug-in/jquery-plugs/fileupload/bootstrap/js/bootstrap.min.js"></script>
<script src="plug-in/selecter/jquery.cityselect.js"></script>
	<script type="text/javascript" src="plug-in/showloading/showLoading.js"></script>

<style type="text/css">
	body{
		  background-color: #f5f5f5; 
	}
	#spanfile{
		position:relative;
	}
	#fileupload{
		position: absolute;
	    font-size: 100px;
	    right: 0;
	    top: 0;
	    opacity: 0;
	    filter: alpha(opacity=0);
	    cursor: pointer
	}
	#progress{
		width:70%;
		margin-left:12%;
	}
	.control-label{
		margin-right:10px;
	}
	.control-group{
		margin-left:55px
	}
	.form-actions{
		border-top: 1px solid #999999;
		position:relative;
		top:3.5em;
		margin-bottom:0;
		padding-bottom:0;
		margin-left:-7em;
	}
</style>
</head>

<body>
	<div id="container" style="margin-top:20px">
		<form class="form-horizontal" action="fileController.do?upload" role="form" id="form" method="post">
			<div id="choosearea">
			<div class="control-group">
				<label class="control-label" for="department">机构</label> 
				<select id="department"
					name="departId" class="prov form-control">
				</select>
			</div>
			<div class="control-group">
				<label class="control-label" for="name">产品</label> 
				<select id="knowId" name="knowId" class="city form-control">
				</select>
			</div>
			<div class="control-group">
				<label class="control-label" for="name">决策方案</label> <select id="prodId" name="prodId" class="dist form-control">
				</select>
			</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="name">上传文件</label>
				  <span id="spanfile" class="btn btn-success fileinput-button">
			        <i class="glyphicon glyphicon-plus"></i>
			        <span>选择文件</span>
			        <!-- The file input field used as target for the file upload widget -->
				       <input id="fileupload" type="file" name="files[]">
						
    			 </span>
				<span id="filename"></span>
			</div>
	    <p class="control-group" style="padding-left:49px">
			上传注意事项：必须上传编辑好的决策表，同时文件的格式必须为xls格式。</br>
			系统会验证决策表是否正确。
		</p> 
		<div class="form-actions">
			<button id="subbtn" disabled  type="submit" class="btn btn-primary start"><i class="glyphicon glyphicon-upload"></i><span>开始上传</span></button>
			<button type="reset" class="btn"><span>重置</span></button>
		</div>
		</form>
		
	
		<div id="files" class="files"></div>
	</div>
	<script type="text/javascript">
		$(function(){
			$(window.parent.document).find(".ui_buttons").hide();
			//$(window.parent.document).find(".ui_buttons").find(".ui_state_highlight").click();
    		//$(window.parent.document).find("#brmsRuleTableListtb").datagrid('reload');
			$("#choosearea").citySelect({
				url: "brmsRuleTableController.do?getIndustMsg",
				prov:"",
				city:"",
				dist:"",
				nodata:"none"
				});
			
			    $('#fileupload').fileupload({
			        dataType: 'json',
			        acceptFileTypes: /(\.|\/)(xls)$/i,
			        url:"fileController.do?upload",
			        add: function (e, data) {
			        	 $("#subbtn").attr("disabled",false);
			        	  data.context = $('<span/>').appendTo('#fileupload');
			              $.each(data.files, function (index, file) {
			                	$("#filename").text(file.name)
			              });
			             $("#subbtn").click(function () {
			            	 $("#container").showLoading();
			                    $(this).prop("disabled",true).find("span").text("正在上传");
			                     data.submit();
			                     return false;
			                }); 
			        },
			        done: function (e, data) {
			        	 $("#container").hideLoading();
			        	console.log(data.result.success);
			        	if(data.result.success){
			        		var win = frameElement.api.opener;// 来源页面
			    			win.location.reload();
			        	}else{
							$("#subbtn").find("span").text("开始上传").prop("disabled",false);
							//$("<div/>").append("<a href='#' class='close' data-dismiss='alert'>&times;</a>").addClass("alert alert-error").text("上传失败").appendTo("p");
			        		//$(this).replaceWith($('<div/>').css("margin-left","8%").text('Uploading...'));
			        		$("#filename").text("");
			        		
			        		alert(data.result.msg);
			        		
			        	}
			        	
			        },
			        
			        progressall: function (e, data) {
				        var progress = parseInt(data.loaded / data.total * 100, 10);
				        $('#progress .bar').css(
				            'width',
				            progress + '%'
				        );
			   		},
			   		
					dropZone: $('#dropzone')
			    });
			
		});
	</script>
	</body>
