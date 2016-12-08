package com.ifre.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.ifre.form.api.ApiDemo;
import com.ifre.util.AesUtil;
import com.ifre.util.CommonUtil;
import com.ifre.util.HttpKit;
import com.ifre.util.SignUtil;

@Scope("prototype")
@Controller
@RequestMapping("/apiDemoController")
public class ApiDemoController {

	@RequestMapping(params = "apiDemo")
	public ModelAndView apiDemo(HttpServletRequest request) {
		return new ModelAndView("com/ifre/show/apiDemo");
	}
	
	@RequestMapping(params = "doRunModel")
    public  ModelAndView doRunModel(ApiDemo apiDemo,HttpServletRequest req){
		//参数校验，后台先不校验，前段简单校验
		if(apiDemo.getKey().length == apiDemo.getValue().length){
			try {
				req.setAttribute("SUCCESS", true);
				//解析接口参数
				String result = runModel(apiDemo);
				req.setAttribute("result", result);
				Map<String,Object> map = CommonUtil.jsonStringToMap(result);
				System.out.println("map:"+map);
				if("SUCCESS".equals(map.get("status"))){
					req.setAttribute("status", true);
					try {
						Map<String,Object> mapData = CommonUtil.jsonStringToMap(map.get("msg").toString());
						req.setAttribute("map", mapData);
					} catch (Exception e) {
						req.setAttribute("Msg", map.get("msg"));
					}
				}else{
					req.setAttribute("status", false);
					req.setAttribute("Msg", map.get("msg"));
				}
			} catch (Exception e) {
				req.setAttribute("SUCCESS", false);
				req.setAttribute("Msg", "模型接口调用异常" + e.getMessage());
			}
		}else{
			req.setAttribute("SUCCESS",false);
			req.setAttribute("Msg", "参数异常");
		}
    	return new ModelAndView("com/ifre/show/apiDemoResult");
    }
	
	
	private String runModel(ApiDemo apiDemo) throws Exception {
		/** 组装明文报文开始 */
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> mapData = new HashMap<String, Object>();
		map.put("sysSource", apiDemo.getSysSource());
		mapData.put("prodId", apiDemo.getProdIdShow());				//iFRE提供的反欺诈决策方案产品编码
		mapData.put("businessNum", apiDemo.getBusinessNum());		//业务编码，businessNum，Sting，必填，长度1-30
		int parametersNum = apiDemo.getKey().length;
		for (int i = 0; i < parametersNum; i++) {
			mapData.put(apiDemo.getKey()[i], apiDemo.getValue()[i]);
		}
		/** 组装明文报文结束 */
		//有两种调用方式（数据库为准），若使用加密，数据加密后保存到encryptData；若不使用加密直接保存map
		if(apiDemo.getEncryptKey() != null && !apiDemo.getEncryptKey().isEmpty()){
			/** 组装密文报文 开始*/
			String mapDataJson = JSONObject.toJSONString(mapData);
			String encryptData = AesUtil.base64EncryptCBC(mapDataJson, apiDemo.getEncryptKey());
			map.put("encryptData", encryptData);
			/** 组装密文报文结束 */
		}else{
			map.putAll(mapData);
		}
		/** 组装报文签名开始 */
		map.put("sysSign", SignUtil.md5Sign(map, apiDemo.getSignKey()));
		/** 组装报文签名结束 */
		
		return HttpKit.post(apiDemo.getUrl(), JSONObject.toJSONString(map), false);
	}
}
