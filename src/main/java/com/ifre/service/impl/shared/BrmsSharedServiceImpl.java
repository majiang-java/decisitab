package com.ifre.service.impl.shared;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.jeecgframework.core.constant.Globals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.ifre.entity.shared.BusiSysInfoEntity;
import com.ifre.exception.IfreException;
import com.ifre.form.shared.InterfaceLogForm;
import com.ifre.form.shared.SharedReturn;
import com.ifre.service.brms.IntfCallModelServiceI;
import com.ifre.service.exception.IfreExceptionServiceI;
import com.ifre.service.shared.BrmsSharedServiceI;
import com.ifre.service.shared.BusiSysInfoServiceI;
import com.ifre.service.shared.InterfaceLogServiceI;
import com.ifre.util.AesUtil;
import com.ifre.util.CommonUtil;


@Service("brmsSharedService")
public class BrmsSharedServiceImpl implements BrmsSharedServiceI {
	
	@Autowired
	private InterfaceLogServiceI interfaceLogService;
	
	@Autowired
	private BusiSysInfoServiceI busiSysInfoService;
	
	@Autowired
	private IfreExceptionServiceI ifreExceptionService;
	
	@Autowired
	private IntfCallModelServiceI intfCallModelService;
	
	/**
     * 共享服务样例
     * @author 王传圣
     * @param jsonData 业务订单信息json格式
     * @return String 返回信息
     */
	public String example(String jsonData) {
		
		//返回信息	
		String result = "";
		Object msg = "";
		SharedReturn sharedReturn = new SharedReturn();
		InterfaceLogForm logForm = new InterfaceLogForm("example");
		Map<String,Object> map = new TreeMap<String, Object>();	

		try {
			map = this.commonResolve(jsonData, logForm);
			
//			Example1 example1 = JSON.parseObject(jsonData, Example1.class);
//			List<Example2> test2List = JSONArray.parseArray(example1.getList(), Example2.class);
//			Example example = JSON.parseObject(jsonData, Example.class);
			
			//业务处理逻辑
			map.put("key", "value");
			logForm.setSendObj(map);
			//返回结果
			msg = map;
			sharedReturn.setStatus("SUCCESS");
		}catch(IfreException ex){
			msg = ex.getMessage();
			ifreExceptionService.resolveIfreException(ex,Globals.Log_Leavel_exception); 	
		}catch(Exception ex){
			msg = ex.getMessage();
			ifreExceptionService.resolveException(ex,Globals.Log_Leavel_exception); 
		}finally{
			//设置返回信息
			sharedReturn.setMsg(msg);	
			result = commonEncrypt(map.get("encryptKey"), sharedReturn);
			//记录日志
			interfaceLogService.addInterfaceLog(logForm,sharedReturn);
		}
		
		return result;
	}
	
	/**
     * 决策引擎接口
     * @author 王传圣
     * @param jsonData 业务订单信息json格式
     * @return String 返回信息
     */
	public String callModelFire(String jsonData) {
		
		//返回信息	
		String result = "";
		Object msg = "";
		SharedReturn sharedReturn = new SharedReturn();
		InterfaceLogForm logForm = new InterfaceLogForm("callModelFire");
		Map<String,Object> map = new TreeMap<String, Object>();	

		try {
			map = this.commonResolve(jsonData, logForm);
			
			//业务处理逻辑
			Map<String,String> businessMap = resolveCallModelFire(map);
			logForm.setSendObj(businessMap);
			msg = intfCallModelService.callModelFire(businessMap);
			sharedReturn.setStatus("SUCCESS");
		}catch(IfreException ex){
			msg = ex.getMessage();
			ifreExceptionService.resolveIfreException(ex,Globals.Log_Leavel_exception); 	
		}catch(Exception ex){
			msg = ex.getMessage();
			ifreExceptionService.resolveException(ex,Globals.Log_Leavel_exception); 
		}finally{
			//设置返回信息
			sharedReturn.setMsg(msg);	
			result = commonEncrypt(map.get("encryptKey"), sharedReturn);
			//记录日志
			interfaceLogService.addInterfaceLog(logForm,sharedReturn);
		}
		
		return result;
	}
	
	/**
     * 决策引擎接口信息处理
     * @author 王传圣
     * @param jsonData 业务订单信息json格式
     * @return String 返回信息
     */
	public Map<String,String> resolveCallModelFire(Map<String,Object> map) throws IfreException{
		
		Map<String,String> resultMap = new HashMap<String, String>();
		
		try {
			map.remove("sysSource");
			Set<String> set = map.keySet();
			Iterator<String> it = set.iterator();
			while (it.hasNext()) {						
				String key = it.next();				
				Object value = map.get(key);
				if(null != value){
					resultMap.put(key, value.toString());
				}			  
			}
		}catch(Exception ex){
			throw new IfreException(100000,"智能决策接口信息处理异常",ex); 
		}
		
		return resultMap;
	}
	
	/**
     * 通用方法
     * @author 王传圣
     * @param jsonData 业务订单信息json格式
     * @param logForm 日志信息
     * @return Map<String,Object> 报文信息
     */
	public Map<String,Object> commonResolve(String jsonData, InterfaceLogForm logForm) throws IfreException,Exception {
		
		Map<String,Object> map = null;
		
		try {
			//解析接口参数
			map = CommonUtil.jsonStringToMap(jsonData);
			logForm.setReceiveMsg(jsonData);
			
			//渠道来源
			Object sysSource = map.get("sysSource");
			if(null == sysSource){
				throw new Exception("无法获取渠道来源");
			}
			logForm.setSysSource(sysSource.toString());
			BusiSysInfoEntity busiSysInfoEntity = busiSysInfoService.findBusiSysInfo(sysSource.toString());			
			if(null == busiSysInfoEntity){
				throw new Exception("无权限");   
			}	
			//渠道名称
			logForm.setSysDesc(busiSysInfoEntity.getSysDesc());
			//签名验证
			busiSysInfoService.signVerify(busiSysInfoEntity.getSignKey(),map);
			
			String encryptKey = busiSysInfoEntity.getEncryptKey();
			//解密
			map = busiSysInfoService.decrypt(encryptKey,map);
			if(null == map){
				throw new Exception("报文信息错误");
			}
			map.put("encryptKey", encryptKey);
			
			//业务编码
			Object businessNum = map.get("bizNo");
			if(null == businessNum){
				throw new Exception("无法获取业务编码");
			}
			logForm.setBusinessNum(businessNum.toString());
		}catch(IfreException ex){
			throw ex;
		}catch(Exception ex){
			throw ex;
		}
		
		return map;
	}
	
	/**
     * 通用加密方法
     * @author 王传圣
     * @param encryptKey 加密秘钥
     * @param sharedReturn 共享服务返回信息
     * @return SharedReturn
     */
	public String commonEncrypt(Object encryptKey,SharedReturn sharedReturn){
		
		String result = "";
		
		try {
			result = JSONObject.toJSONString(sharedReturn);
			if(null != encryptKey && !"".equals(encryptKey.toString().trim())){
				result = AesUtil.base64EncryptCBC(result, encryptKey.toString()); 
				sharedReturn.setEncryptMsg(result);
			}
		}catch(Exception ex){
			ifreExceptionService.resolveException(ex,Globals.Log_Leavel_exception);
		}
		
		return result;
	}
}