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
				<c:if test="${'A01' != orgCode && null != orgCode}">
					<tr>
						<td align="right">
							<label class="Validform_label">
								绑定机构:
							</label>
						</td>
						<td class="value">
							<input class="inputxt" id="sysSource" name="sysSource" 
								   value="${departname}" style="min-width:260px;" readonly="readonly">
							<input hidden="hidden" id="orgCode" name="orgCode" value="${orgCode}">
							<span class="Validform_checktip"></span>
						</td>
					</tr>
				</c:if>
				<c:if test="${'A01' == orgCode}">
					<tr>
						<td align="right">
							<label class="Validform_label">
								绑定机构:
							</label>
						</td>
						<td class="value">
							<select id = "orgCode" name = "orgCode" style="min-width:265px;" >
								<c:forEach items="${departList}"
									var="depart">
									<option value="${depart.orgCode}">${depart.departname}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
				</c:if>
				<tr>
					<td align="right">
						<label class="Validform_label">
							渠道来源:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="sysSource" name="sysSource" 
							   value="${busiSysInfoPage.sysSource}" datatype="*"  style="min-width:260px;">
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
						<%-- <input class="inputxt" id="sysDesc" name="sysDesc"
							   value="${busiSysInfoPage.sysDesc}" datatype="*" style="min-width:260px;"> --%>
						<textarea id="sysDesc" name="sysDesc" datatype="*" rows="2" style="min-width:260px;">${busiSysInfoPage.sysDesc}</textarea>
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
							   value="${busiSysInfoPage.password}" ignore="ignore" style="min-width:260px;">
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
							   value="${busiSysInfoPage.signKey}" style="min-width:260px;">
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
							   value="${busiSysInfoPage.encryptKey}" style="min-width:260px;">
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
							   value="${busiSysInfoPage.notifyUrl}" style="min-width:260px;">
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