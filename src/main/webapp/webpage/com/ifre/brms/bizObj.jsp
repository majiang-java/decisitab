<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>业务对象</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
	$('#addObjPropBtn').linkbutton({
		iconCls : 'icon-add'
	});
	$('#delObjPropBtn').linkbutton({
		iconCls : 'icon-remove'
	});
	$('#createObjPropBtn').linkbutton({
		iconCls : 'icon-add'
	});

	
	function change(a,b) {
		 var aa=a.split(",");
		   var bb=b.split(",");
		   console.info(aa);
		   console.info(bb);	
		   console.info(bb[1]);
		  document.getElementById("pckgName").value=bb[1];
		  document.getElementById("pckgId").value=bb[0];
		  document.getElementById("prodName").value=aa[1];
		  document.getElementById("prodId").value=aa[0];
		  
		//  document.getElementById("sourceCode").value=c;
		//console.info(c);
	};
	
	function addProp(){
		var tr = $("#add_objProp_table_template tr").clone();
		$("#add_objProp_table").append(tr);
		resetTrNum('add_objProp_table');
	}
	$('#delObjPropBtn').bind(
			'click',
			function() {
				$("#add_objProp_table").find("input:checked").parent().parent()
						.remove();
				resetTrNum('add_objProp_table');
			});
	
	function delProp(){
		$("#add_objProp_table").find("input:checked").parent().parent()
		.remove();
        resetTrNum('add_objProp_table');
	}

	function createProp(){
		var codeValue=document.getElementById("sourceCode").value;
		codeValue=codeValue.replace(/\n/g, '_@#').replace(/\r/g, '_#@');
		console.info("................:"+codeValue);
		var data1=($('#formobj').serialize());
		console.info("1111111111111:"+data1);
		//data1=data.replace("%5B","[").replace("%5D","]");
		//alert(data1);
		$.ajax({
			type : "POST",
			dataType : "json",
			url : "bizObjController.do?savePage",
			data : data1,	
			success : function(data) {
				console.info(data);
			    data=data.replace(/_#@/g,'\n').replace('_#@','/\r/g' );
				console.info("result:" + data);
			    document.getElementById("sourceCode").value=data;
				//加载最大可退金额

			},
			error : function(data) {
				var result=data.responseText;
				result=result.substring(1,data.responseText.length-1).replace('_#@',/\n/g).replace('_#@',/\r/g).replace("\t",'');
				//console.info("result:" + result);
				 document.getElementById("sourceCode").value=result;
			}
		});
	};

	$(document).ready(function() {
		$(".datagrid-toolbar").parent().css("width", "auto");
	});
	//初始化下标
	function resetTrNum(tableId) {
		$tbody = $("#" + tableId + "");
		$tbody
				.find('>tr')
				.each(
						function(i) {
							$(':input, select', this).each(
											function() {
												var $this = $(this), name = $this
														.attr('name'), val = $this
														.val();
												if (name != null) {
													if (name.indexOf("#index#") >= 0) {
														$this.attr("name",name.replace('#index#',i));
													} else {
														var s = name.indexOf("[");
														var e = name.indexOf("]");
														var new_name = name.substring(s + 1,e);
														$this.attr("name",name.replace(new_name,i));
													}
												}
											});
							$(this).find('div[name=\'xh\']').html(i + 1);
						});
	}
</script>
</head>
<body style="overflow-y: hidden" scroll="no" onload="change('${bizObjPage.prodId}','${bizObjPage.pckgId}')"> 
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" tiptype="1" action="bizObjController.do?save">
		<input id="id" name="id" type="hidden" value="${bizObjPage.id }">
		<table cellpadding="0" cellspacing="1" class="formtable">
			<tr>
				<td align="right"><label class="Validform_label">决策方案:</label></td>
				<td class="value"><input class="inputxt" id="prodName" name="prodName"
					 datatype="*"> <span
					class="Validform_checktip"></span>
				</td>
				<td class="value" hidden="true"><input nullmsg="请填写产品ID" errormsg="产品ID格式不对"
					class="inputxt" id="prodId" name="prodId"
					 datatype="*" > <span
					class="Validform_checktip"></span>
				</td>
				<td align="right"><label class="Validform_label">方案包:</label></td>
				<td class="value"><input class="inputxt" id="pckgName" name="pckgName" > <span
					class="Validform_checktip"></span>
			    </td>
				<td class="value" hidden="true"><input nullmsg="请填写包ID" errormsg="包ID格式不对"
					class="inputxt" id="pckgId" name="pckgId" > <span
					class="Validform_checktip"></span>
				</td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">名称:</label></td>
				<td class="value"><input nullmsg="请填写名称" errormsg="名称格式不对"
					class="inputxt" id="name" name="name" ignore="ignore"
					value="${bizObjPage.name}"> <span
					class="Validform_checktip"></span></td>
				<td align="right"><label class="Validform_label">描述:</label></td>
				<td class="value">
					<%-- <input nullmsg="请填写描述" errormsg="描述格式不对"
						class="inputxt" id="descp" name="descp" ignore="ignore"
						value="${bizObjPage.descp}"> <span
						class="Validform_checktip"></span> --%>
					<textarea nullmsg="请填写描述" errormsg="描述格式不对" id="descp" name="descp" ignore="ignore" rows="2" style="min-width:150px;">${bizObjPage.descp}</textarea>
				</td>
			</tr>
			<tr hidden="true">
				<td align="right"><label class="Validform_label">源码:</label></td>
				<td class="value"><input nullmsg="请填写源码" errormsg="源码格式不对"
					class="inputxt" id="sourceCode1" name="sourceCode1" ignore="ignore"> <span
					class="Validform_checktip"></span></td>
			</tr>
		</table>
		<div style="width: auto; height: 400px;">
			<%-- 增加一个div，用于调节页面大小，否则默认太小 --%>
			<div style="width: 690px; height: 1px;"></div>
			 <%-- 				<t:tabs id="tt" iframe="false" tabPosition="top" fit="false">
				 <t:tab href="*" icon="icon-search" title="对象属性" id="objProp"></t:tab>
				</t:tabs>  --%>



			<div style="padding: 3px; height: 25px; width: auto;"class="datagrid-toolbar">
			    <span style="float:left;">
				<a class="easyui-linkbutton l-btn l-btn-plain" icon="icon-add"  id="addObjPropBtn" href="#" onclick="addProp()">
				<span>添加</span>
				</a> 
				
				<a class="easyui-linkbutton l-btn l-btn-plain"   id="delObjPropBtn"  href="#" onclick="delProp()">删除</a>
				
				<a  class="easyui-linkbutton l-btn l-btn-plain" icon="icon-edit" id="createObjPropBtn" href="#" onclick="createProp()">
				<span>
				生成代码
				</span>
				</a>
			</span>
			</div>
			<!-- <div style="width: auto;height: 500px;overflow-y:auto;overflow-x:scroll;"> -->
			<div id="Container">
				<div style="width: auto; height: 500px;overflow-y:auto;overflow-x:scroll; float: left">
					<table border="0" cellpadding="2" cellspacing="0"
						id="objProp_table">
						<tr bgcolor="#E6E6E6">
							<td align="center" bgcolor="#EEEEEE">序号</td>
							<td align="center" bgcolor="#EEEEEE" style="width: 30px;">操作</td>
							<td align="left" bgcolor="#EEEEEE" style="width: 120px;">代码</td>
							<td align="left" bgcolor="#EEEEEE" style="width: 120px;">名称</td>
							<td align="left" bgcolor="#EEEEEE" style="width: 120px;">描述</td>
							<td align="left" bgcolor="#EEEEEE" style="width: 120px;">类型</td>
							<td align="left" bgcolor="#EEEEEE" style="width: 120px;">是否为List</td>
						</tr>
						<tbody id="add_objProp_table">
							<c:if test="${fn:length(objPropList)  <= 0 }">
								<tr>
									<td align="center"><div style="width: 25px;" name="xh">1</div></td>
									<td align="center"><input style="width: 20px;" 
										type="checkbox" name="ck" /></td>
									<td align="left"><input name="objPropList[0].propCode" value="id"
										maxlength="100" type="text" style="width: 120px;"></td>
									<td align="left"><input name="objPropList[0].name"
										maxlength="120" type="text" style="width: 120px;"></td>
									<td align="left"><input name="objPropList[0].descp"
										maxlength="300" type="text" style="width: 120px;"></td>
									<td align="left">
									    <select name="objPropList[0].type"
										maxlength="3" type="text" style="width: 120px;">
				                           <option value="int"  <c:if test="${poVal[5] eq 'int'}"> selected="selected"</c:if>>int</option>
				                            <option value="Integer"  <c:if test="${poVal[5] eq 'Integer'}"> selected="selected"</c:if>>Integer</option>
										   <option value="String"  <c:if test="${poVal[5] eq 'String'}"> selected="selected"</c:if>>String</option>
										   <option value="byte"  <c:if test="${poVal[5] eq 'byte'}"> selected="selected"</c:if>>byte</option>
										   <option value="Byte"  <c:if test="${poVal[5] eq 'Byte'}"> selected="selected"</c:if>>Byte</option>
										    <option value="char"  <c:if test="${poVal[5] eq 'char'}"> selected="selected"</c:if>>char</option>
										     <option value="Character"  <c:if test="${poVal[5] eq 'Character'}"> selected="selected"</c:if>>Character</option>
 						                   <option value="Double"  <c:if test="${poVal[5] eq 'Double'}"> selected="selected"</c:if>>Double</option>
 						                    <option value="double"  <c:if test="${poVal[5] eq 'double'}"> selected="selected"</c:if>>double</option>
 						                   <option value="boolean"  <c:if test="${poVal[5] eq 'boolean'}"> selected="selected"</c:if>>boolean</option>
 						                   <option value="Boolean"  <c:if test="${poVal[5] eq 'Boolean'}"> selected="selected"</c:if>>Boolean</option>
						                   <option value="Date"  <c:if test="${poVal[5] eq 'Date'}"> selected="selected"</c:if>>Date</option>
						                   <option value="float"  <c:if test="${poVal[5] eq 'float'}"> selected="selected"</c:if>>float</option>
						                      <option value="Float"  <c:if test="${poVal[5] eq 'Float'}"> selected="selected"</c:if>>Float</option>
						                    <option value="long"  <c:if test="${poVal[5] eq 'long'}"> selected="selected"</c:if>>long</option>
						                    <option value="Long"  <c:if test="${poVal[5] eq 'Long'}"> selected="selected"</c:if>>Long</option>
						                   <option value="BigDecimal"  <c:if test="${poVal[5] eq 'BigDecimal'}"> selected="selected"</c:if>>BigDecimal</option>
						                   </select>
									</td>
									<td align="left">
								  <select name="objPropList[0].isList"
					maxlength="200" type="text" style="width: 120px;">
					<option value="N" <c:if test="${poVal[6] eq 'N'}"> selected="selected"</c:if>>否</option>
				    <option value="Y" <c:if test="${poVal[6] eq 'Y'}"> selected="selected"</c:if>>是</option>
				    </select>
				    </td>
								</tr>
							</c:if>
							<c:if test="${fn:length(objPropList)  > 0 }">
								<c:forEach items="${objPropList}" var="poVal" varStatus="stuts">
									<tr>
										<td align="center"><div style="width: 25px;" name="xh">${stuts.index+1}</div></td>
										<td align="center"><input style="width: 20px;"
											type="checkbox" name="ck" /></td>
	 								  	<input name="objPropList[${stuts.index }].id"
											value="${poVal[0] }" type="hidden">   
										<td align="left">
										 <input name="objPropList[${stuts.index }].propCode" maxlength="100"
											value="${poVal[2] }" type="text" style="width: 120px;">  
											</td>
										<td align="left">
				 						<input name="objPropList[${stuts.index }].name" maxlength="120"
											value="${poVal[3] }" type="text" style="width: 120px;">
										</td> 
										<td align="left">
										 <input
											name="objPropList[${stuts.index }].descp" maxlength="300"
											value="${poVal[4] }" type="text" style="width: 120px;">
											 </td>
										<td align="left">
 										<select name="objPropList[${stuts.index }].type" value="${poVal[5] }"  style="width: 120px;">
						                   <option value="int"  <c:if test="${poVal[5] eq 'int'}"> selected="selected"</c:if>>int</option>
				                            <option value="Integer"  <c:if test="${poVal[5] eq 'Integer'}"> selected="selected"</c:if>>Integer</option>
										   <option value="String"  <c:if test="${poVal[5] eq 'String'}"> selected="selected"</c:if>>String</option>
										   <option value="byte"  <c:if test="${poVal[5] eq 'byte'}"> selected="selected"</c:if>>byte</option>
										   <option value="Byte"  <c:if test="${poVal[5] eq 'Byte'}"> selected="selected"</c:if>>Byte</option>
										    <option value="char"  <c:if test="${poVal[5] eq 'char'}"> selected="selected"</c:if>>char</option>
										     <option value="Character"  <c:if test="${poVal[5] eq 'Character'}"> selected="selected"</c:if>>Character</option>
 						                   <option value="Double"  <c:if test="${poVal[5] eq 'Double'}"> selected="selected"</c:if>>Double</option>
 						                    <option value="double"  <c:if test="${poVal[5] eq 'double'}"> selected="selected"</c:if>>double</option>
 						                   <option value="boolean"  <c:if test="${poVal[5] eq 'boolean'}"> selected="selected"</c:if>>boolean</option>
 						                   <option value="Boolean"  <c:if test="${poVal[5] eq 'Boolean'}"> selected="selected"</c:if>>Boolean</option>
						                   <option value="Date"  <c:if test="${poVal[5] eq 'Date'}"> selected="selected"</c:if>>Date</option>
						                   <option value="float"  <c:if test="${poVal[5] eq 'float'}"> selected="selected"</c:if>>float</option>
						                      <option value="Float"  <c:if test="${poVal[5] eq 'Float'}"> selected="selected"</c:if>>Float</option>
						                    <option value="long"  <c:if test="${poVal[5] eq 'long'}"> selected="selected"</c:if>>long</option>
						                    <option value="Long"  <c:if test="${poVal[5] eq 'Long'}"> selected="selected"</c:if>>Long</option>
						                   <option value="BigDecimal"  <c:if test="${poVal[5] eq 'BigDecimal'}"> selected="selected"</c:if>>BigDecimal</option>
						                   </select> 
						                </td>	
										<td align="left">
											 <select name="objPropList[${stuts.index }].isList"
					                                maxlength="200" value="${poVal[6] }"  type="text" style="width: 120px;">
					                  <option value="N" <c:if test="${poVal[6] eq 'N'}"> selected="selected"</c:if>>否</option>
				    <option value="Y" <c:if test="${poVal[6] eq 'Y'}"> selected="selected"</c:if>>是</option>
				                       </select>
											</td>
									</tr>
								</c:forEach>
					
							</c:if>
						</tbody>
					</table>
				</div>
				<div id="Content-right"
					style="width: 600px; height: 500px; float: left; margin: 20px;">
					<table border="2" cellpadding="2" cellspacing="0"
						id="objProp_table">
						<div id="Content-right" style="width:600px; height: 500px; float:left; margin:30px;">
			            <textarea id="sourceCode" name="sourceCode" cols="100" rows="30" style="overflow-x:scroll;overflow-y:scroll;" >${bizObjPage.sourceCode}</textarea> 
                  </div>
					</table>
				</div>
			</div>
		</div>


	</t:formvalid>
	<!-- 添加 明细 模版 -->

	<table style="display: none">
		<tbody id="add_objProp_table_template">
			<tr>
				<td align="center"><div style="width: 25px;" name="xh"></div></td>
				<td align="center"><input style="width: 20px;" type="checkbox"
					name="ck" /></td>
				<td align="left"><input name="objPropList[#index#].propCode"
					maxlength="100" type="text" style="width: 120px;"></td>
				<td align="left"><input name="objPropList[#index#].name"
					maxlength="120" type="text" style="width: 120px;"></td>
				<td align="left"><input name="objPropList[#index#].descp"
					maxlength="300" type="text" style="width: 120px;"></td>
				<td align="left">
				    <select name="objPropList[#index#].type"
					maxlength="3" type="text" style="width: 120px;">
				                          <option value="int">int</option>				    
				                            <option value="Integer" >Integer</option>
										   <option value="String" >String</option>
										   <option value="byte"  >byte</option>
										   <option value="Byte">Byte</option>
										    <option value="char"  >char</option>
										     <option value="Character">Character</option>
 						                   <option value="Double" >Double</option>
 						                    <option value="double"  >double</option>
 						                   <option value="boolean"  >boolean</option>
 						                   <option value="Boolean" >Boolean</option>
						                   <option value="Date"  >Date</option>
						                   <option value="float"  >float</option>
						                      <option value="Float"  >Float</option>
						                    <option value="long" >long</option>
						                    <option value="Long" >Long</option>
						                	            </select>	
				</td>
				<td align="left">
				    <select name="objPropList[#index#].isList"
					maxlength="200" type="text" style="width: 120px;">
					<option value="N">否</option>
				    <option value="Y">是</option>
				    </select>
				</td>
			</tr>
		</tbody>
	</table>
</body>

