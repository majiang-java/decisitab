<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>

<div class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
        <t:datagrid name="typeValueList" title="变量取值列表"
                    actionUrl="modelVarController.do?typeGrid&typegroupid=${typegroupid}&prodType=1" idField="id"
                    queryMode="group">
            <t:dgCol title="common.code" field="id" hidden="true"></t:dgCol>
            <t:dgCol title="变量取值" field="typename"></t:dgCol>
            <t:dgCol title="第一码值" field="codevalue1"></t:dgCol>
            <t:dgCol title="第二码值" field="codevalue2"></t:dgCol>
            <t:dgCol title="分值" field="score"></t:dgCol>
            <t:dgCol title="common.operation" field="opt"></t:dgCol>
            <t:dgDelOpt url="modelVarController.do?delType&id={id}" title="common.delete"></t:dgDelOpt>
            <t:dgToolBar title="common.add.param" langArg="变量取值" icon="icon-add" url="modelVarController.do?addorupdateType&typegroupid=${typegroupid}&prodType=${prodType}" funname="add"></t:dgToolBar>
            <t:dgToolBar title="common.edit.param" langArg="变量取值" icon="icon-edit" url="modelVarController.do?addorupdateType&typegroupid=${typegroupid}&prodType=${prodType}" funname="update"></t:dgToolBar>
        </t:datagrid>
    </div>
</div>
<script>
    function addType(title,addurl,gname,width,height) {
        alert($("#id").val());
        add(title,addurl,gname,width,height);
    }
</script>

