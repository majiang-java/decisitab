<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
	$('#addObjPropBtn').linkbutton({   
	    iconCls: 'icon-add'  
	});  
	$('#delObjPropBtn').linkbutton({   
	    iconCls: 'icon-remove'  
	}); 
	$('#createObjPropBtn').linkbutton({   
	    iconCls: 'icon-add'  
	}); 
	$('#addObjPropBtn').bind('click', function(){   
 		 var tr =  $("#add_objProp_table_template tr").clone();
 		 console.info(tr);
	 	 $("#add_objProp_table").append(tr);
	 	 resetTrNum('add_objProp_table');
    });  
	$('#delObjPropBtn').bind('click', function(){   
      	$("#add_objProp_table").find("input:checked").parent().parent().remove();   
        resetTrNum('add_objProp_table'); 
    }); 
	
	$('#createObjPropBtn').bind('click', function(){   
		 alert($('#formAddHandlingFee').serialize());
		  $.ajax({
               type: "POST",
               dataType: "html",
               url: "bizObjController?savePage",
               data: $('#formAddHandlingFee').serialize(),
               success: function (data) {
                   var strresult=data;
                   alert(strresult);
                   //加载最大可退金额
                  
               },
               error: function(data) {
                   alert("error:"+data.responseText);
                }
    }); 
	});
	
    $(document).ready(function(){
    	$(".datagrid-toolbar").parent().css("width","auto");
    });
</script>
<div style="padding: 3px; height: 25px;width:auto;" class="datagrid-toolbar">
	<a id="addObjPropBtn" href="#">添加</a> <a id="delObjPropBtn" href="#">删除</a> 
	<a id="createObjPropBtn" href="#">生成代码</a> 
</div>
<!-- <div style="width: auto;height: 500px;overflow-y:auto;overflow-x:scroll;"> -->
<div id="Container">
<div style="width: auto; height: 500px; float:left">
<form id="formAddHandlingFee" >
<table border="0" cellpadding="2" cellspacing="0" id="objProp_table">
	<tr bgcolor="#E6E6E6">
		<td align="center" bgcolor="#EEEEEE">序号</td>
		<td align="center" bgcolor="#EEEEEE">操作</td>
				  <td align="left" bgcolor="#EEEEEE">代码</td>
				  <td align="left" bgcolor="#EEEEEE">名称</td>
				  <td align="left" bgcolor="#EEEEEE">描述</td>
				  <td align="left" bgcolor="#EEEEEE">类型</td>
				  <td align="left" bgcolor="#EEEEEE">是否为List</td>
	</tr>
	<tbody id="add_objProp_table">	
 	<c:if test="${fn:length(objPropList)  <= 0 }">
			<tr>
				<td align="center"><div style="width: 25px;" name="xh">1</div></td>
				 <td align="center"><input style="width:20px;"  type="checkbox" name="ck"/></td>
				  <td align="left"><input name="objPropList[0].propCode" maxlength="100" type="text" style="width:120px;" ></td>
				  <td align="left"><input name="objPropList[0].name" maxlength="120" type="text" style="width:120px;" ></td>
				  <td align="left"><input name="objPropList[0].descp" maxlength="300" type="text" style="width:120px;" ></td>
				  <td align="left"><input name="objPropList[0].type" maxlength="3" type="text" style="width:120px;" ></td>
				  <td align="left"><input name="objPropList[0].isList" maxlength="200" type="text" style="width:120px;" ></td>
   			</tr>
	</c:if>
	<c:if test="${fn:length(objPropList)  > 0 }">
		<c:forEach items="${objPropList}" var="poVal" varStatus="stuts">
			<tr>
				<td align="center"><div style="width: 25px;" name="xh">${stuts.index+1 }</div></td>
				<td align="center"><input style="width:20px;"  type="checkbox" name="ck" /></td>
				   <input name="objPropList[${stuts.index }].id"  value="${poVal.id }" type="hidden" >
				   <td align="left"><input name="objPropList[${stuts.index }].propCode" maxlength="100" value="${poVal.propCode }" type="text" style="width:120px;"></td>
				   <td align="left"><input name="objPropList[${stuts.index }].name" maxlength="120" value="${poVal.name }" type="text" style="width:120px;"></td>
				   <td align="left"><input name="objPropList[${stuts.index }].descp" maxlength="300" value="${poVal.descp }" type="text" style="width:120px;"></td>
				   <td align="left"><input name="objPropList[${stuts.index }].type" maxlength="3" value="${poVal.type }" type="text" style="width:120px;"></td>
				   <td align="left"><input name="objPropList[${stuts.index }].isList" maxlength="200" value="${poVal.isList }" type="text" style="width:120px;"></td>
   			</tr>
		</c:forEach>
	</c:if>	 
	</tbody>
</table>
</form>
</div>

