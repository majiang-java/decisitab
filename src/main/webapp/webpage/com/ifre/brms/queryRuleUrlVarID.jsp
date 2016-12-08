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
<div>接口调用代码类RuleCallTest：</div>
<textarea name="sourceCode" rows="80" style="min-width:1000px;">
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.alibaba.fastjson.JSONObject;
import com.ifre.util.AesUtil;
import com.ifre.util.HttpKit;
import com.ifre.util.SignUtil;

public class RuleCallTest {

	public static void main(String[] args) throws Exception {

		try {
			String jsonData = null;

			/** 组装明文报文开始 */
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("prodId", "${ruleProd.id }");								//产品编码,以此为准
			map.put("businessNum", "2016104004");								//业务编码，businessNum，Sting，必填，长度1-30
			map.put("sysSource", "zdcf");										//系统来源,由iFRE提供
			
			map.put("A0101","A0101");											//此处按“变量代码列表”所列，输入合适值(具体码值参考模型管理--规则管理里的具体决策表)

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

			String url = "${Url}";						//接口Url，,以此为准
			jsonData = JSONObject.toJSONString(map);				// 使用该行代码，发送明文报文（适用于无加密秘钥场景）
//			 jsonData = JSONObject.toJSONString(encryptMap);		// 使用该行代码，发送密文报文（适用于有加密秘钥场景）
			System.out.println("url = " + url);
			System.out.println("jsonData = " + jsonData);
			String result = HttpKit.post(url, jsonData, false);		//调用接口，发送报文
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
