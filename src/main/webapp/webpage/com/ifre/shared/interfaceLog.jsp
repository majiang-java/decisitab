<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>通讯日志</title>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<style type="text/css">
.headShow{
	widows: 100%;
	padding: 10px;
	margin-top: 10px;
	background-color: #eee;
	border-bottom: 1px solid #ddd;
}
</style>
<script type="text/javascript">
	$(document).ready(
			function() {
				var receiveJson = '${interfaceLogPage.receiveMsg}';
				var sendJson = '${interfaceLogPage.sendMsg}';
				var resultJson = '${interfaceLogPage.result}';

				var receivemsg = format(receiveJson);
				var sendmsg = format(sendJson);
				var resultmsg = format(resultJson);

				if (receivemsg == "jsonEmpty") {
					receivemsg = "";
				} else if (receivemsg == "jsonError") {
					receivemsg = "<font style=\"color:#CC0000\">" + receiveJson
							+ "</font>";
				}
				$("#receiveMsg").html("<pre>" + receivemsg + "</pre>");

				if (sendmsg == "jsonEmpty") {
					sendmsg = "";
				} else if (sendmsg == "jsonError") {
					sendmsg = "<font style=\"color:#CC0000\">" + sendJson
							+ "</font>";
				}
				$("#sendMsg").html("<pre>" + sendmsg + "</pre>");

				if (resultmsg == "jsonEmpty") {
					resultmsg = "";
				} else if (resultmsg == "jsonError") {
					if (resultJson.trim() != "SUCCESS"
							&& resultJson.trim() != "REVERSED") {
						resultmsg = "<font color=\"red\">" + resultJson
								+ "</font>";
					} else {
						resultmsg = resultJson;
					}
				}
				$("#resultMsg").html("<pre>" + resultmsg + "</pre>");

				$("#head").click(function() {
					$("#headbody").toggle();
				});
				
				$("#receivebtnhead").click(function() {
					$("#receivebtnArea").toggle();
				});

				$("#sendhead").click(function() {
					$("#sendArea").toggle();
				});

				$("#resulthead").click(function() {
					$("#resultArea").toggle();
				});

			});

	function format(txt, compress/*是否为压缩模式*/) {/* 格式化JSON源码(对象转换为JSON文本) */
		var indentChar = '    ';
		if (/^\s*$/.test(txt)) {
			return "jsonEmpty";
		}
		try {
			var data = eval('(' + txt + ')');
		} catch (e) {
			return "jsonError";
		}
		;
		var draw = [], last = false, This = this, line = compress ? '' : '\n', nodeCount = 0, maxDepth = 0;

		var notify = function(name, value, isLast, indent/*缩进*/, formObj) {
			nodeCount++;/*节点计数*/
			for (var i = 0, tab = ''; i < indent; i++)
				tab += indentChar;/* 缩进HTML */
			tab = compress ? '' : tab;/*压缩模式忽略缩进*/
			maxDepth = ++indent;/*缩进递增并记录*/
			if (value && value.constructor == Array) {/*处理数组*/
				draw.push(tab + (formObj ? ('"' + name + '":') : '') + '['
						+ line);/*缩进'[' 然后换行*/
				for (var i = 0; i < value.length; i++)
					notify(i, value[i], i == value.length - 1, indent, false);
				draw.push(tab + ']' + (isLast ? line : (',' + line)));/*缩进']'换行,若非尾元素则添加逗号*/
			} else if (value && typeof value == 'object') {/*处理对象*/
				draw.push(tab + (formObj ? ('"' + name + '":') : '') + '{'
						+ line);/*缩进'{' 然后换行*/
				var len = 0, i = 0;
				for ( var key in value)
					len++;
				for ( var key in value)
					notify(key, value[key], ++i == len, indent, true);
				draw.push(tab + '}' + (isLast ? line : (',' + line)));/*缩进'}'换行,若非尾元素则添加逗号*/
			} else {
				if (typeof value == 'string')
					value = '"' + value + '"';
				draw.push(tab + (formObj ? ('"' + name + '":') : '') + value
						+ (isLast ? '' : ',') + line);
			}
			;
		};
		var isLast = true, indent = 0;
		notify('', data, isLast, indent, false);
		return draw.join('');
	}
</script>

<div class="headShow" id="head">日志信息 </div>
<div class="panel-body" id="headbody" style="padding: 5px">
	<div class="row" style="padding-top: 5px;padding-left: 25px ">
		<label >渠道来源:</label> 
		<label >${interfaceLogPage.sysSource}</label>
	</div>
	<div class="row" style="padding-top: 5px;padding-left: 25px ">
		<label >渠道描述:</label> 
		<label >${interfaceLogPage.sysDesc}</label>
	</div>
	<div class="row" style="padding-top: 5px;padding-left: 25px ">
		<label >业务编码:</label> 
		<label >${interfaceLogPage.businessNum}</label>
	</div>
	<div class="row" style="padding-top: 5px;padding-left: 25px ">
		<label >业务类型:</label> 
		<t:dictSelect typeGroupCode="busiType" defaultVal="${interfaceLogPage.businessType}" field="" type="text"  hasLabel="false" ></t:dictSelect>
	</div>
	<div class="row" style="padding-top: 5px;padding-left: 25px;display: none ">
		<label >业务子类型:</label> 
		<label >${interfaceLogPage.subBusinessType}</label>
	</div>
	<div class="row" style="padding-top: 5px;padding-left: 25px ">
		<label >处理状态:</label> 
		<t:dictSelect typeGroupCode="handleStat" defaultVal="${interfaceLogPage.handleStatus}" hasLabel="false" field="" type="text" ></t:dictSelect>
		<%-- <label  id="resultMark">${interfaceLogPage.handleStatus == 0?'成功':'失败'}</label> --%>
		<%-- <label >${interfaceLogPage.handleStatus}</label> --%>
	</div>
	<div class="row" style="padding-top: 5px;padding-left: 25px;display: none"> 
		<label >备注:</label> 
		<label >${interfaceLogPage.remark}</label>
	</div>
	<div class="row" style="padding-top: 5px;padding-left: 25px "> 
		<label >创建时间:</label> 
		<label >${interfaceLogPage.createDate}</label>
	</div>
</div>

<div class="headShow" id="receivebtnhead">接收信息</div>
<div class="panel-body" id="receivebtnArea" style="display: none">
	<div id="receiveMsg" style="padding: 5px" ></div>
</div>

<div class="headShow" id="sendhead" >发送信息</div>
<div class="panel-body" id="sendArea" style="display: none">
	<div id="sendMsg" style="padding: 5px"></div>
</div>

<div class="headShow" id="resulthead" >处理结果信息</div>
<div class="panel-body" id="resultArea" style="display: none">
	<div id="resultMsg" style="padding: 5px"></div>
</div>
 