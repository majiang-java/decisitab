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
            width: 265,
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
	<input id="id" name="id" type="hidden" value="${knwldgLibPage.id  }" >
	<fieldset class="step">
              <div class="form">
            <label class="Validform_label"> 所属机构: </label>
            <input id="ccc"  name="orgId" value="${knwldgLibPage.orgId}"  style="min-width:260px;">
       </div>
        
         <div class="form">
			<label class="Validform_label">名称:</label>
			<input class="inputxt" id="name" name="name" ignore="ignore"
							   value="${knwldgLibPage.name}" style="min-width:260px;">
        </div>
       
        <div class="form">
            <label class="Validform_label"> 描述:</label>
            <%-- <input name="descp" class="inputxt" value="${knwldgLibPage.descp }" style="min-width:260px;"> --%>
            <textarea name="descp" rows="2" style="min-width:260px;">${knwldgLibPage.descp }</textarea>
        </div>
        
	  <div class="form">
			<label class="Validform_label">状态:</label>
	            <c:if test="${empty knwldgLibPage.status}"><t:dictSelect field="status" typeGroupCode="knwStatus" hasLabel="false" defaultVal="1" type="radio"/></c:if>
	            <c:if test="${not empty knwldgLibPage.status}"><t:dictSelect field="status" typeGroupCode="knwStatus" hasLabel="false" defaultVal="${knwldgLibPage.status}" type="radio"/></c:if>
	  			
			  
	 </div>
        
	</fieldset>
</t:formvalid>
</body>
</html>
