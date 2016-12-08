<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>方案包</title>
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
 <body style="overflow-y: hidden" scroll="no" onload="change('${rulePckgPage.prodId}')">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="rulePckgController.do?save">
			<input id="id" name="id" type="hidden" value="${rulePckgPage.id }">
			<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							决策方案:
						</label>
					</td>
					<td class="value"  hidden="true">
						<input class="inputxt" id="prodId" name="prodId"  datatype="*">
						<span class="Validform_checktip"></span>
					</td>
					<td class="value">
						<input class="inputxt" id="prodName" name="prodName" readonly
							    datatype="*" style="min-width:260px;">
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
							   value="${rulePckgPage.name}" style="min-width:260px;">
						<span class="Validform_checktip"></span>
					</td> 
<!--   <td class="value">
   <div   style="position:relative;">    
  <span   style="margin-left:100px;width:18px;overflow:hidden;">  
  <select id='FROM_VALUE'   style="width:118px;margin-left:-100px"   onchange="this.parentNode.nextSibling.value=this.value">  
  <option   value="aaaa">   aaaa   </option>  
  <option   value="bbbb">   bbbb   </option>  
  <option   value="cccc">   ccccc   </option>  
  </select></span><input   name="FROM_VALUE"   style="width:100px;position:absolute;left:0px;">  
  </div>   
</td>  -->				
				
				
				
				
				
				
				
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							全名:
						</label>
					</td>
					<td class="value">
						<%-- <input class="inputxt" id="allName" name="allName" ignore="ignore"
							   value="${rulePckgPage.allName}">
						<span class="Validform_checktip"></span> --%>
						<div id="container">
                        <div id="content">
                        <div class="first"><input class="inputxt" id="allName" name="allName" ignore="ignore"
							   value="${rulePckgPage.allName}" onKeyup="getContent(this);" onblur="combine()" style="min-width:260px;"/></div>
                         <div id="append" class="inputxt"></div>
                           </div>
                        </div>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							描述:
						</label>
					</td>
					<td class="value">
						<%-- <input class="inputxt" id="descp" name="descp"  
							   value="${rulePckgPage.descp}" style="min-width:260px;">
						<span class="Validform_checktip"></span> --%>
						<textarea id="descp" name="descp" rows="2" style="min-width:260px;">${rulePckgPage.descp}</textarea>
					</td>
				</tr>
<%-- 				<tr>
					<td align="right">
						<label class="Validform_label">
							父包ID:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="parentPckgId" name="parentPckgId" ignore="ignore"
							   value="${rulePckgPage.parentPckgId}">
						<span class="Validform_checktip"></span>
					</td>
				</tr> --%>
			</table>
		</t:formvalid>
		
<!--  <form id="form" name="myForm">  
    <div id="container">
    <div id="content">
        <div class="first"><input id="kw" onKeyup="getContent(this);" /></div>
        <div id="append"></div>
    </div>
    </div>
</form> -->


 </body>
 
<script type="text/javascript">
 
var packdata = [];
 
 $(document).ready(function(){	 
	    //ajax 对data赋值
	    $.ajax({
			type : "POST",
			url : "rulePckgController.do?fullName",
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
	            $("#allName").blur();
	            var a=$("#allName").val();
	            var b=$("#name").val().length;
		    	var c=a.substring(0,a.length-b-1);
	            nocombine(c);
	            if($(".item").hasClass("addbg")){
	                moveNext($("#allName").val());
	              //  nocombine($("#allName").val());
	            }else{
	                $(".item").removeClass('addbg').eq(0).addClass('addbg');
	            } 
	         //选中 
	        }else if(keycode == 13){
	        	var value = "."+$("#name").val();
	    	    var allValue=$("#allName").val().substr($("#allName").val().length-value.length);
	    	    if(allValue==value){

	    	    }else{
 	            dojob();
	            combine(); 
	    	    }
	    	    
	    	    
	    	    
	    	    var value2 = "."+$("#name").val();
	     	     var allValue=$("#allName").val();
	     	     var value1=allValue.substr(allValue.length-value2.length);
	     	     if(value1==value2){
	     	    	$("#parentPckgId").val(allValue.substr(0,allValue.length-value2.length)); 
	     	     }else{
	     	      $("#parentPckgId").val(allValue);
	           }
	        }
	    });


		
	    var movePrev = function(){
	      //  $("#allName").blur();
	        var index = $(".addbg").prevAll().length;
	        if(index == 0){
	            $(".item").removeClass('addbg').eq($(".item").length-1).addClass('addbg');
	        }else{
	            $(".item").removeClass('addbg').eq(index-1).addClass('addbg');
	        }
	    }
	   
	    var moveNext = function(a){
	        var index = $(".addbg").prevAll().length;
	        if(index == $(".item").length-1){
	            $(".item").removeClass('addbg').eq(0).addClass('addbg');
	        }else{
	            $(".item").removeClass('addbg').eq(index+1).addClass('addbg');
	        }
	       
	    }
	   
	    var dojob = function(){
	        $("#allName").blur();
  	        var value = $(".addbg").text();
  	        if(value==null||value==""){
  	        	
  	        }else{
	        $("#allName").val(value);  
  	        }
  	        $("#append").hide().html("");
  	        
  	    
	    }
	});
 
 
 
  function change(a){
	   var aa=a.split(",");
	  document.getElementById("prodName").value=aa[1];
	  document.getElementById("prodId").value=aa[0];
	  console.info(aa);
	  
   }
  
  
  
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
	    var value = $(obj).text()+"."+$("#name").val();
	    $("#allName").val(value);
	    $("#append").hide().html("");
	}
  
	function combine(){
	    var value = "."+$("#name").val();
	    var allValue=$("#allName").val().substr($("#allName").val().length-value.length);
	    if(allValue==value){
	    $("#allName").val($("#allName").val());
	    }
	    else{
	    $("#allName").val($("#allName").val()+value); 		    
	}
	   
	    
	    var value2 = "."+$("#name").val();
	     var allValue=$("#allName").val();
	     var value1=allValue.substr(allValue.length-value2.length);
	     if(value1==value2){
	    	$("#parentPckgId").val(allValue.substr(0,allValue.length-value2.length)); 
	     }else{
	      $("#parentPckgId").val(allValue);
      }   
	    
	    
	}
	function nocombine(a){
	    $("#allName").val(a); 
	}
  
 </script>