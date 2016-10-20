<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>条件描述</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="brmsConditionDetailController.do?save">
			<input id="id" name="id" type="hidden" value="${brmsConditionDetailPage.id }">
			<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							条件ID:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="condId" name="condId" 
							   value="${brmsConditionDetailPage.condId}" datatype="*">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							序号:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="seq" name="seq" 
							   value="${brmsConditionDetailPage.seq}" datatype="n">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							条件值:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="condValue" name="condValue" ignore="ignore"
							   value="${brmsConditionDetailPage.condValue}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>