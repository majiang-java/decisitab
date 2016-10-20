<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>渠道来源信息</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="busiSysInfoController.do?save">
			<input id="id" name="id" type="hidden" value="${busiSysInfoPage.id }">
			<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							渠道来源:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="sysSource" name="sysSource" 
							   value="${busiSysInfoPage.sysSource}" datatype="*">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							渠道描述:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="sysDesc" name="sysDesc"
							   value="${busiSysInfoPage.sysDesc}" datatype="*">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							密码:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="password" name="password" 
							   value="${busiSysInfoPage.password}" ignore="ignore>
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							签名秘钥:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="signKey" name="signKey" ignore="ignore"
							   value="${busiSysInfoPage.signKey}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							加密秘钥:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="encryptKey" name="encryptKey" ignore="ignore"
							   value="${busiSysInfoPage.encryptKey}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							回调地址:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="notifyUrl" name="notifyUrl" ignore="ignore"
							   value="${busiSysInfoPage.notifyUrl}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<%-- <tr>
					<td align="right">
						<label class="Validform_label">
							标识:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="marking" name="marking" ignore="ignore"
							   value="${busiSysInfoPage.marking}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							记录删除标志:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="status" name="status" 
							   value="${busiSysInfoPage.status}" datatype="*">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							排序:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="sorts" name="sorts" 
							   value="${busiSysInfoPage.sorts}" datatype="*">
						<span class="Validform_checktip"></span>
					</td>
				</tr> --%>
			</table>
		</t:formvalid>
 </body>