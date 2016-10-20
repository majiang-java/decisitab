<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>决策方案</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  
   <style type="text/css">

    .addbg{
        background:#87A900;
    }
    #append{
       // border:solid #87A900 2px;
        border-top:0;
        display:none;
        position:absolute; 
        background:white;
    }
    
/*      .item{
        padding:3px 5px;
        cursor:pointer;
    } */
    .addbg{
        background:#87A900;
    }
  </style>
  
 </head>
 <body style="overflow-y: hidden" scroll="no" onload="change('${ruleProdPage.orgId}','${ruleProdPage.kknwldgId}')">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="ruleProdController.do?save">
			<input id="id" name="id" type="hidden" value="${ruleProdPage.id }">
			<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							所属机构:
						</label>
					</td> 
					<td class="value" >
						<input id="orgName" class="inputxt" readonly 
							    datatype="*">							   
						<span class="Validform_checktip"></span>
					</td> 
					<td class="value" hidden="true">
						<input class="inputxt" id="orgId" name="orgId" 
							  datatype="*">
							   
						<span class="Validform_checktip"></span>
					</td> 	
				
				</tr>
				
					  <td class="value" hidden="true">
						<input class="inputxt" id="orgCode" name="orgCode" value="${ruleProdPage.orgCode }"
							  datatype="*">
					</td> 
					
				<tr>
					<td align="right">
						<label class="Validform_label">
							产品:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="kknwldgName" name="kknwldgName" readonly 
							  datatype="*">
						<span class="Validform_checktip"></span>
					</td>
					<td class="value" hidden="true">
						<input class="inputxt" id="kknwldgId" name="kknwldgId" 
							   datatype="*">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							名称:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="name" name="name" ignore="ignore"
							   value="${ruleProdPage.name}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							描述:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="descp" name="descp" ignore="ignore"
							   value="${ruleProdPage.descp}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							方案组ID:
						</label>
					</td>
					<td class="value">
						<%-- <input class="inputxt" id="allName" name="allName" ignore="ignore"
							   value="${rulePckgPage.allName}">
						<span class="Validform_checktip"></span> --%>
						<div id="container">
                        <div id="content">
                        <div class="first"><input class="inputxt" id="groupId" name="groupId" ignore="ignore"
							   value="${ruleProdPage.groupId}" onKeyup="getContent(this);"/></div>
                         <div id="append" class="inputxt"></div>
                           </div>
                        </div>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							方案ID:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="artifactId" name="artifactId" ignore="ignore"
							   value="${ruleProdPage.artifactId}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							版本号:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="versionId" name="versionId" ignore="ignore"
							   value="${ruleProdPage.versionId}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							版本描述:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="versionDesc" name="versionDesc" ignore="ignore"
							   value="${ruleProdPage.versionDesc}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr  hidden="true">
					<td align="right">
						<label class="Validform_label">
							状态:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="status" name="status" ignore="ignore"
							   value="0" datatype="n" >
						<span class="Validform_checktip"></span>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>
 <script>
   function change(a,b){
	   var aa=a.split(",");
	   var bb=b.split(",");
	  document.getElementById("kknwldgName").value=bb[1];
	  document.getElementById("kknwldgId").value=bb[0];
	  document.getElementById("orgName").value=aa[1];
	  document.getElementById("orgId").value=aa[0];
   }
   
   
   
   var packdata = [];
           $(document).ready(function(){
        	          	   
         	   //ajax 对data赋值
       	    $.ajax({
       			type : "POST",
       			url : "ruleProdController.do?getGroupList",
       			success : function(data) {
       				//var message="接口调用结果："+JSON.parse(JSON.parse(data).obj).status+"\n"+"日志信息："+JSON.parse(JSON.parse(data).obj).msg;
       				console.info(JSON.parse(data).obj);
       				packdata=JSON.parse(data).obj;
       				console.info(packdata);
       			},
       			error : function(data) {
       				var result=data.responseText;
       				console.info("result:" + result);
       				 document.getElementById("sourceCode").value=result;
       			}
       		}); 
        	   
       	   
               $(document).keydown(function(e){
                   e = e || window.event;
                   var keycode = e.which ? e.which : e.keyCode;
                   if(keycode == 38){
                       if(jQuery.trim($("#append").html())==""){
                           return;
                       }
                       movePrev();
                   }else if(keycode == 40){
                       if(jQuery.trim($("#append").html())==""){
                           return;
                       }
                       $("#groupId").blur();
                       if($(".item").hasClass("addbg")){
                           moveNext();
                       }else{
                           $(".item").removeClass('addbg').eq(0).addClass('addbg');
                       }
                      
                   }else if(keycode == 13){
                       dojob();
                   }
               });

               var movePrev = function(){
                   $("#groupId").blur();
                   var index = $(".addbg").prevAll().length;
                   if(index == 0){
                       $(".item").removeClass('addbg').eq($(".item").length-1).addClass('addbg');
                   }else{
                       $(".item").removeClass('addbg').eq(index-1).addClass('addbg');
                   }
               }
              
               var moveNext = function(){
                   var index = $(".addbg").prevAll().length;
                   if(index == $(".item").length-1){
                       $(".item").removeClass('addbg').eq(0).addClass('addbg');
                   }else{
                       $(".item").removeClass('addbg').eq(index+1).addClass('addbg');
                   }
                  
               }
              
               var dojob = function(){
                   $("#groupId").blur();
                   var value = $(".addbg").text();
                   if(value==null||value==""){
         	        	
         	        }else{
         	           $("#groupId").val(value);
         	        }
                   $("#append").hide().html("");
               }
           });
           function getContent(obj){
               var kw = jQuery.trim($(obj).val());
               if(kw == ""){
                   $("#append").hide().html("");
                   return false;
               }
               var html = "";
               for (var i = 0; i < packdata.length; i++) {
                   if (packdata[i].indexOf(kw) >= 0) {
                       html = html + "<div class='item' onmouseenter='getFocus(this)' onClick='getCon(this);'>" + packdata[i] + "</div>"
                   }
               }
               if(html != ""){
                   $("#append").show().html(html);
               }else{
                   $("#append").hide().html("");
               }
           }
           function getFocus(obj){
               $(".item").removeClass("addbg");
               $(obj).addClass("addbg");
           }
           function getCon(obj){
               var value = $(obj).text();
               $("#groupId").val(value);
               $("#append").hide().html("");
           }
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
 </script>