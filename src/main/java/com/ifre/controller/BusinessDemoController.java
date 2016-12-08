package com.ifre.controller;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.util.ResourceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.ifre.entity.brms.RuleProdEntity;
import com.ifre.exception.IfreException;
import com.ifre.form.api.ApiDemo;
import com.ifre.form.api.ApiDemoGrxyd;
import com.ifre.form.api.ApiDemoPfk;
import com.ifre.form.api.ApiDemoYz16;
import com.ifre.form.api.ApiDemoYz17;
import com.ifre.form.api.FqzFrom;
import com.ifre.service.brms.RuleProdServiceI;
import com.ifre.util.AesUtil;
import com.ifre.util.CommonUtil;
import com.ifre.util.HttpKit;
import com.ifre.util.SignUtil;

@Scope("prototype")
@Controller
@RequestMapping("/businessDemoController")
public class BusinessDemoController {
	
	@Autowired
	private RuleProdServiceI ruleProdService;

	@RequestMapping(params = "businessDemo")
	public ModelAndView businessDemo(HttpServletRequest request) {
		try {
			String orgCode = ResourceUtil.getSessionUserName().getCurrentDepart().getOrgCode();
			//业务逻辑，admin也只能演示自己机构下的决策方案，添加强制机构过滤
			//舍弃强制机构过滤逻辑
			List<RuleProdEntity> prodList = ruleProdService.findRuleProdList(orgCode);
			request.setAttribute("prodList", prodList);
		} catch (IfreException e) {
			request.setAttribute("SUCCESS", false);
			request.setAttribute("Msg", e.getMessage());
		}
		return new ModelAndView("com/ifre/show/businessDemo");
	}

	@RequestMapping(params = "businessDemoAJax")
	@ResponseBody
	public AjaxJson businessDemoAJax(String prodId, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			j.setObj(ruleProdService.getModelByProdId(prodId));
			j.setSuccess(true);
		} catch (IfreException e) {
			j.setMsg(e.getMessage());
			j.setSuccess(false);
		}
		return j;
	}
	
	@RequestMapping(params = "businessDemoGrxyd")
	public ModelAndView businessDemoGrxyd(HttpServletRequest request) {
		return new ModelAndView("com/ifre/show/businessDemoGrxyd");
	}
	
	@RequestMapping(params = "businessDemoPfk")
	public ModelAndView businessDemoPfk(HttpServletRequest request) {
		return new ModelAndView("com/ifre/show/businessDemoPfk");
	}
	
	@RequestMapping(params = "businessDemoYz16")
	public ModelAndView businessDemoYz16(HttpServletRequest request) {
		return new ModelAndView("com/ifre/show/businessDemoYz16");
	}
	
	@RequestMapping(params = "businessDemoYz17")
	public ModelAndView businessDemoYz17(HttpServletRequest request) {
		return new ModelAndView("com/ifre/show/businessDemoYz17");
	}
	
	@RequestMapping(params = "fqzDemo")
	public ModelAndView fqzDemo(HttpServletRequest request) {
		return new ModelAndView("com/ifre/show/fqzDemo");
	}
	
	@RequestMapping(params = "doRunModel")
    public  ModelAndView doRunModel(ApiDemo apiDemo,HttpServletRequest req){
		try {
			req.setAttribute("SUCCESS", true);
			//if(apiDemo.getUrl() != null && apiDemo.getUrl().contains("callModelFqz")){
			//根据url中是否包含callModelFqz判断,解析接口参数
			String	result = runModel(apiDemo,null);
			req.setAttribute("result", result);
			Map<String,Object> map = CommonUtil.jsonStringToMap(result);
			if("SUCCESS".equals(map.get("status"))){
				req.setAttribute("status", true);
				try {
					Map<String,Object> mapData = CommonUtil.jsonStringToMap(map.get("msg").toString());
					req.setAttribute("map", mapData);
					//25展示违约模型结果；50准入规则结果；71反欺诈规则结果
					if("25".equals(apiDemo.getModelType())){
						return new ModelAndView("com/ifre/show/apiDemoResult");
					}else if("50".equals(apiDemo.getModelType())){
						return new ModelAndView("com/ifre/show/apiDemoResultZrgz");
					}else if("71".equals(apiDemo.getModelType())){
						return new ModelAndView("com/ifre/show/apiDemoResultFqz");
					}else{
						return new ModelAndView("com/ifre/show/apiDemoResultDefault");
					}
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
    	return new ModelAndView("com/ifre/show/apiDemoResultDefault");
    }
	
	@RequestMapping(params = "doRunModelGrxyd")
    public  ModelAndView doRunModelGrxyd(ApiDemo apiDemo,ApiDemoGrxyd apiDemoGrxyd,HttpServletRequest req){
		try {
			req.setAttribute("SUCCESS", true);
			//if(apiDemo.getUrl() != null && apiDemo.getUrl().contains("callModelFqz")){
			//根据url中是否包含callModelFqz判断,解析接口参数
			String	result = runModel(apiDemo,apiDemoGrxyd);
			req.setAttribute("result", result);
			Map<String,Object> map = CommonUtil.jsonStringToMap(result);
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
    	return new ModelAndView("com/ifre/show/apiDemoResult");
    }
	
	@RequestMapping(params = "doRunModelPfk")
    public  ModelAndView doRunModelPfk(ApiDemo apiDemo,ApiDemoPfk apiDemoPfk,HttpServletRequest req){
		try {
			req.setAttribute("SUCCESS", true);
			//根据url中是否包含callModelFqz判断,解析接口参数
			String	result = runModel(apiDemo,apiDemoPfk);
			req.setAttribute("result", result);
			Map<String,Object> map = CommonUtil.jsonStringToMap(result);
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
    	return new ModelAndView("com/ifre/show/apiDemoResult");
    }
	
	@RequestMapping(params = "doRunModelYz16")
    public  ModelAndView doRunModelYz16(ApiDemo apiDemo,ApiDemoYz16 apiDemoYz16,HttpServletRequest req){
		try {
			req.setAttribute("SUCCESS", true);
			//根据url中是否包含callModelFqz判断,解析接口参数
			String	result = runModel(apiDemo,apiDemoYz16);
			req.setAttribute("result", result);
			Map<String,Object> map = CommonUtil.jsonStringToMap(result);
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
    	return new ModelAndView("com/ifre/show/apiDemoResult");
    }
    
    @RequestMapping(params = "doRunModelYz17")
    public  ModelAndView doRunModelYz17(ApiDemo apiDemo,ApiDemoYz17 apiDemoYz17,HttpServletRequest req){
		try {
			req.setAttribute("SUCCESS", true);
			//根据url中是否包含callModelFqz判断,解析接口参数
			String	result = runModel(apiDemo,apiDemoYz17);
			req.setAttribute("result", result);
			Map<String,Object> map = CommonUtil.jsonStringToMap(result);
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
    	return new ModelAndView("com/ifre/show/apiDemoResult");
    }
	
	@RequestMapping(params = "doRunFqz")
    public  ModelAndView doRunFqzModel(ApiDemo apiDemo,FqzFrom fqzFrom,HttpServletRequest req){
		//参数校验，后台先不校验，前段简单校验
		try {
			req.setAttribute("SUCCESS", true);
			//解析接口参数
			String result = runModel(apiDemo,fqzFrom);
			req.setAttribute("result", result);
			Map<String,Object> map = CommonUtil.jsonStringToMap(result);
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
    	return new ModelAndView("com/ifre/show/apiDemoResultFqz");
    }
	
	/**
	 * 根据表单数据，组装成接口调用数据并调用接口返回结果
	 * 
	 * @param apiDemo
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	private String runModel(ApiDemo apiDemo,Object obj) throws Exception {
		/** 组装明文报文开始 */
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> mapData = new HashMap<String, Object>();
		map.put("sysSource", apiDemo.getSysSource());
		mapData.put("sysSource", apiDemo.getSysSource());
		mapData.put("prodId", apiDemo.getProdIdShow());				//iFRE提供的反欺诈决策方案产品编码
		mapData.put("businessNum", apiDemo.getBusinessNum());		//业务编码，businessNum，Sting，必填，长度1-30
		if(obj != null){
			mapData.putAll(transBean2Map(obj));
		}else{
			int num =  apiDemo.getKey().length;
			for(int i=0;i<num;i++){
				mapData.put(apiDemo.getKey()[i], apiDemo.getValue()[i]);
			}
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

	/**
	 * 对象转map
	 * 
	 * @param obj
	 * @return
	 */
	private Map<String, Object> transBean2Map(Object obj) {  
        if(obj == null){  
            return null;  
        }          
        Map<String, Object> map = new HashMap<String, Object>();  
        try {  
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());  
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
            for (PropertyDescriptor property : propertyDescriptors) {  
                String key = property.getName();  
  
                // 过滤class属性  
                if (!key.equals("class")) {  
                    // 得到property对应的getter方法  
                    Method getter = property.getReadMethod();  
                    Object value = getter.invoke(obj);  
                    map.put(key, value);  
                }  
            }  
        } catch (Exception e) {  
            System.out.println("transBean2Map Error " + e);  
        }  
        return map;  
    } 

}
