<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<div class="easyui-layout" fit="true">
<div region="center"  style="padding:0px;border:0px">
<div>决策变量：</div>
<c:forEach items="${varTypegroupList}" var="varTypegroup">
	<div>${varTypegroup.varTypegroupname }：${varTypegroup.varTypegroupcode }</div>
</c:forEach>
<br>
<div>接口调用代码类ModelCallTest：</div>
<textarea name="sourceCode" rows="80" style="min-width:1000px;">
import java.util.Map;
import java.util.TreeMap;

import com.alibaba.fastjson.JSONObject;
import com.ifre.util.AesUtil;
import com.ifre.util.SignUtil;
import com.ifre.util.HttpKit;


public class ModelCallTest {

	public static void main(String[] args) throws Exception {

		try {
			String jsonData = null;
			/** 组装明文报文 开始*/
			Map<String, Object> map = new TreeMap<String, Object>();
			map.put("prodId", "${ruleProd.id }");	        					//产品编码,以此为准
			map.put("businessNum", "2016102001");								//业务编码，businessNum，Sting，必填，长度1-30
			map.put("sysSource", "zdcf");										//系统来源,由iFRE提供
			/**
			 * 输入决策变量码值通用约定规范如下：
			 * 1. 码值优先使用和接入方约定的数据字典码值。
			 * 数据字典约定外的码值，约定规范如下：
			 * 2. 省市码值，请使用国家标准码值
			 * 3. 是否类码值，是约定为"1"，否约定"0"。男为"1"，女为"0"
			 * 4. 缺失值统一设置为"-99"。对于其它项值，已有规范的按具体规范设置，无具体规范的默认设置为"-99"
			 * 5. 上述通用约定规范以外的码值，请以授权决策表中设置的码值为准。特殊问题请联系具体技术同事。
			**/
		    map.put("age", "28");												//变量名参照“变量代码列表”，或模型管理--模型管理里的具体决策表
			jsonData = JSONObject.toJSONString(map);
			/** 组装明文报文结束 */
			
			
			/** 组装密文报文 开始*/
			String encryptKey = "sadf234232411231134111";						//加密秘钥,由iFRE提供
			String encryptData = AesUtil.base64EncryptCBC(jsonData, encryptKey);
			Map<String, Object> encryptMap = new TreeMap<String, Object>();
			encryptMap.put("encryptData", encryptData);
			encryptMap.put("sysSource", "zdcf");								//系统来源,由iFRE提供
			/** 组装密文报文结束 */

			/** 组装报文签名开始 */
			String signKey = "23sdf223r23";										//签名秘钥,由iFRE提供
			map.put("sysSign", SignUtil.md5Sign(map, signKey));					//明文报文签名
			encryptMap.put("sysSign", SignUtil.md5Sign(encryptMap, signKey));	//密文报文签名
			/** 组装报文签名结束 */
			
			String url = "${Url}";												//接口Url，,以此为准
			jsonData = JSONObject.toJSONString(map);							// 使用该行代码，发送明文报文（适用于无加密秘钥场景）
//			 jsonData = JSONObject.toJSONString(encryptMap);					// 使用该行代码，发送密文报文（适用于有加密秘钥场景）
			System.out.println("url = " + url);
			System.out.println("jsonData = " + jsonData);
			String result = HttpKit.post(url, jsonData, false);					//调用接口，发送报文
			System.out.println("返回报文:"+result);			

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
</textarea>
</div>

<script type="text/javascript">

$(function(){
	var url = window.location.host;
	document.getElementById('url').innerHTML = url;
});
</script>
