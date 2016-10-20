<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>部门信息</title>
<t:base type="jquery,easyui,tools"></t:base>
<script type="text/javascript">
	$(function() {
 		 $('#ccc').combotree({
			url : 'departController.do?setPFunction&selfId=${depart.id}',
            width: 155,
            onSelect : function(node) {
            //  alert(node.text);
            //  changeOrgType();
            }
        });    
  
	});


</script>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" layout="div" dialog="true" action="knwldgLibController.do?save">
	<input id="id" name="id" type="hidden" value="${knwldgLibPage.id  }">
	<fieldset class="step">
              <div class="form">
            <label class="Validform_label"> 所属机构: </label>
            <input id="ccc"  name="orgId" value="${knwldgLibPage.orgId}">
       </div>
<!--        
        <div class="form">
            <label class="Validform_label"> 所属机构ID: </label>
            <input name="orgId" class="inputxt" value="${knwldgLibPage.orgId}"  datatype="s1-20">
            <span class="Validform_checktip"></span>
        </div>  -->
        
        
         <div class="form">
			<label class="Validform_label">名称:</label>
			<input class="inputxt" id="name" name="name" ignore="ignore"
							   value="${knwldgLibPage.name}">
        </div>
       
        <div class="form">
            <label class="Validform_label"> 描述:</label>
            <input name="descp" class="inputxt" value="${knwldgLibPage.descp }">
        </div>
        
        
		<div class="form">
			 <label class="Validform_label">协议:</label>
			 <input class="inputxt" id="prtcl" name="prtcl" ignore="ignore" value="${knwldgLibPage.prtcl}">
						<span class="Validform_checktip"></span>
       </div>
       
       <div class="form">
			<label class="Validform_label">分支名:</label>
						<input class="inputxt" id="brchName" name="brchName" ignore="ignore"  value="${knwldgLibPage.brchName}">
						<span class="Validform_checktip"></span>
      </div>
      
	  <div class="form">
						<label class="Validform_label">状态:</label>
						<select class="inputxt" id="status" name="status" ignore="ignore" value="${knwldgLibPage.status}">
			             <option value=1 <c:if test="${knwldgLibPage.status eq 1}"> selected="selected"</c:if>>可用</option>
						<option value=0 <c:if test="${knwldgLibPage.status eq 0}"> selected="selected"</c:if>>禁用</option>
						</select> 
						
	 </div>

        
        
	</fieldset>
</t:formvalid>
</body>
</html>
