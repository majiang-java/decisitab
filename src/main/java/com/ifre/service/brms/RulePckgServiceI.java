package com.ifre.service.brms;

import java.util.List;

import org.jeecgframework.core.common.service.CommonService;

import com.ifre.entity.brms.RulePckgEntity;
import com.ifre.exception.IfreException;

public interface RulePckgServiceI extends CommonService{

	/**
     * 根据组织ID查询决策包
     * @author 王传圣
     * @param  String orgId 组织ID
     * @return List<RulePckgEntity> 决策包
     * @throws IfreException 
     */  
	public List<RulePckgEntity> findRulePckgEntityByOrgId(String orgId) throws IfreException;
}
