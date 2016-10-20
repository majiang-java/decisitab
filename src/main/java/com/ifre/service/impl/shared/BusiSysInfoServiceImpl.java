package com.ifre.service.impl.shared;

import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ifre.entity.shared.BusiSysInfoEntity;
import com.ifre.exception.IfreException;
import com.ifre.service.shared.BusiSysInfoServiceI;
import com.ifre.util.AesUtil;
import com.ifre.util.CommonUtil;
import com.ifre.util.SignUtil;

@Service("busiSysInfoService")
@Transactional
public class BusiSysInfoServiceImpl extends CommonServiceImpl implements BusiSysInfoServiceI {
	
	/**
     * 查询渠道来源信息
     * @author 王传圣
     * @param  String sysSource 渠道来源
     * @return BusiSysInfoEntity 渠道来源信息
     * @throws IfreException 
     */  
	public BusiSysInfoEntity findBusiSysInfo(String sysSource) throws IfreException {
		
		BusiSysInfoEntity result = null;
		
		try {
			StringBuffer hql = new StringBuffer();
			hql.append(" from BusiSysInfoEntity where status = 0 and sysSource = ? ");
			List<BusiSysInfoEntity> list = this.findHql(hql.toString(),new Object[]{sysSource});
			if(null != list && list.size() > 0){
				result = list.get(0);
			}
		} catch (Exception ex) {
			throw new IfreException(100000,"查询渠道来源信息异常",ex); 
		}
		
		return result;
	}
	
	/**
     * 渠道来源信息签名验证
     * @author 王传圣 
     * @param  sysKey 签名秘钥
     * @param  map 接口信息
     * @return void
     * @throws Exception 
     */  
	public void signVerify(String signKey,Map<String,Object> map) throws Exception{
	
		if(null != signKey && !"".equals(signKey.trim())){
			Object sysSign = map.get("sysSign");
			if(null == sysSign){
				throw new Exception("无法获取签名"); 
			}
			map.remove("sysSign");
			Object repayPlanList = map.get("repayPlanList");
			map.remove("repayPlanList");
			//获得签名
			String sign = SignUtil.md5Sign(map, signKey);			
			if(null == sysSign || !sign.equals(sysSign.toString())){
				throw new Exception("系统来源签名验证失败");   
			}
			if(null != repayPlanList){
				map.put("repayPlanList",repayPlanList);
			}
		}
	}
	
	/**
     * 报文解密
     * @author 王传圣 
     * @param  key 加密秘钥
     * @param  map 报文信息
     * @return void
     * @throws Exception 
     */  
	public Map<String,Object> decrypt(String key,Map<String,Object> map) throws Exception{
	
		if(null != key && !"".equals(key.trim())){
			Object encryptData = map.get("encryptData");
			if(null == encryptData){
				throw new Exception("无法获取密文报文");
			}
			//解密
			String decryptData = AesUtil.decryptCBC(encryptData.toString(), key);  
			map = CommonUtil.jsonStringToMap(decryptData);
		}
		
		return map;
	}
}