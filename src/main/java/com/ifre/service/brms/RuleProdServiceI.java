package com.ifre.service.brms;

import java.util.List;

import org.jeecgframework.core.common.service.CommonService;

import com.ifre.entity.brms.RuleProdEntity;
import com.ifre.exception.IfreException;

public interface RuleProdServiceI extends CommonService{
	
	public void updateProductStatus(String prodId,String status);

	/**
     * 根据组织ID查询决策产品
     * @author 王传圣
     * @param  String orgId 组织ID
     * @return List<RuleProdEntity> 决策产品
     * @throws IfreException 
     */  
	public List<RuleProdEntity> findRuleProdEntityByOrgId(String orgId) throws IfreException;
}
