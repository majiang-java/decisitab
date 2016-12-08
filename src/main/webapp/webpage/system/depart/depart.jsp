<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>部门信息</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
	$(function() {
		$('#cc').combotree({
			url : 'departController.do?setPFunction&selfId=${depart.id}',
            width: 265,
            onSelect : function(node) {
//                alert(node.text);
                changeOrgType();
            }
        });
        if(!$('#cc').val()) { // 第一级，只显示公司选择项
            var orgTypeSelect = $("#orgType");
            var companyOrgType = '<option value="1" <c:if test="${orgType=='1'}">selected="selected"</c:if>><t:mutiLang langKey="common.company"/></option>';
            orgTypeSelect.empty();
            orgTypeSelect.append(companyOrgType);
        } else { // 非第一级，不显示公司选择项
            $("#orgType option:first").remove();
        }
        if($("#id").val()) {
            $('#cc').combotree('disable');
        }
        if('${empty pid}' == 'false') { // 设置新增页面时的父级
            $('#cc').combotree('setValue', '${pid}');
        }
	});
    function changeOrgType() { // 处理组织类型，不显示公司选择项
        var orgTypeSelect = $("#orgType");
        var optionNum = orgTypeSelect.get(0).options.length;
        if(optionNum == 1) {
            $("#orgType option:first").remove();
            var bumen = '<option value="2" <c:if test="${orgType=='2'}">selected="selected"</c:if>><t:mutiLang langKey="common.department"/></option>';
            var gangwei = '<option value="3" <c:if test="${orgType=='3'}">selected="selected"</c:if>><t:mutiLang langKey="common.position"/></option>';
            orgTypeSelect.append(bumen);
        }
    }
</script>
</head>
<body style="overflow-y:auto" scroll="no">
<t:formvalid formid="formobj" layout="div" dialog="true" action="systemController.do?saveDepart">
	<input id="id" name="id" type="hidden" value="${depart.id }">
	<fieldset class="step">
        <div class="form">
            <label class="Validform_label"> 机构名: </label>
            <input name="departname" class="inputxt" type="text" value="${depart.departname }"  datatype="s1-20"  style="min-width:260px;">
            <span class="Validform_checktip"><t:mutiLang langKey="departmentname.rang1to20"/></span>
        </div>
        <div class="form">
            <label class="Validform_label"> 机构描述: </label>
            <%-- <input name="description" value="${depart.description }"  style="min-width:260px;"> --%>
            <!-- <textarea rows="2" style="min-width:260px;"/> -->
            <textarea name="description" rows="2" style="min-width:260px;">${depart.description }</textarea>
        </div>
        <div class="form">
            <label class="Validform_label"> 上级机构: </label>
            <input id="cc" name="TSPDepart.id" value="${depart.TSPDepart.id}" >
        </div>
        <div class="form">
            <input type="hidden" name="orgCode" value="${depart.orgCode }">
            <label class="Validform_label"> <t:mutiLang langKey="common.org.type"/>: </label>
            <select name="orgType" id="orgType" style="min-width:265px;">
                <option value="1" <c:if test="${orgType=='1'}">selected="selected"</c:if>><t:mutiLang langKey="common.company"/></option>
                <option value="2" <c:if test="${orgType=='2'}">selected="selected"</c:if>><t:mutiLang langKey="common.department"/></option>
                <%-- <option value="3" <c:if test="${orgType=='3'}">selected="selected"</c:if>><t:mutiLang langKey="common.position"/></option> --%>
            </select>
        </div>
        <div class="form">
            <label class="Validform_label"> <t:mutiLang langKey="common.mobile"/>: </label>
            <input name="mobile" class="inputxt" value="${depart.mobile }"  style="min-width:260px;">
        </div>
        <div class="form">
            <label class="Validform_label"> <t:mutiLang langKey="common.fax"/>: </label>
            <input name="fax" class="inputxt" value="${depart.fax }"  style="min-width:260px;">
        </div>
        <div class="form">
            <label class="Validform_label"> <t:mutiLang langKey="common.address"/>: </label>
            <input name="address" class="inputxt" value="${depart.address }" datatype="s1-50" style="min-width:260px;">
            <span class="Validform_checktip"><t:mutiLang langKey="departmentaddress.rang1to50"/></span>
        </div>
        <div class="form">
            <label class="Validform_label"> 开始日期: </label>
            <input name="startDate" class="inputxt" value="${depart.startDate }" style="width: 260px" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"datatype="*">
        </div>
        <div class="form">
            <label class="Validform_label"> 结束日期: </label>
            <input name="endDate" class="inputxt" value="${depart.endDate }" style="width: 260px" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"datatype="*">
        </div>
        <div class="form">
            <label class="Validform_label"> 状态: </label>
			<select id="status" name="status" value="${depart.status}" style="min-width:265px;">
             	<option value="1" <c:if test="${depart.status eq 1}"> selected="selected"</c:if>>激活</option>
				<option value="0" <c:if test="${depart.status eq 0}"> selected="selected"</c:if>>关闭</option>
			</select>        
        </div>                       
	</fieldset>
</t:formvalid>
</body>
</html>
