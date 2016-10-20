package com.ifre.service.shared;

import java.util.Map;

import org.jeecgframework.core.common.service.CommonService;

import com.ifre.entity.shared.BusiSysInfoEntity;
import com.ifre.exception.IfreException;

public interface BusiSysInfoServiceI extends CommonService{

	/**
     * 查询渠道来源信息
     * @author wcsheng
     * @param  String sysSource 渠道来源
     * @return BusiSysInfoEntity 渠道来源信息
     * @throws IfreException 
     */  
	public BusiSysInfoEntity findBusiSysInfo(String sysSource) throws IfreException;
	
	/**
     * 渠道来源信息签名验证
     * @author 王传圣 
     * @param  signKey 签名秘钥
     * @param  map 接口信息
     * @return void
     * @throws Exception 
     */  
	public void signVerify(String signKey,Map<String,Object> map) throws Exception;
	
	/**
     * 报文解密
     * @author 王传圣 
     * @param  key 加密秘钥
     * @param  map 报文信息
     * @return void
     * @throws Exception 
     */  
	public Map<String,Object> decrypt(String key,Map<String,Object> map) throws Exception;
}
