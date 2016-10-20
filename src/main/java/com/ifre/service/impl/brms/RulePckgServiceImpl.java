package com.ifre.service.impl.brms;

import java.util.List;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ifre.entity.brms.RulePckgEntity;
import com.ifre.exception.IfreException;
import com.ifre.service.brms.RulePckgServiceI;

@Service("rulePckgService")
@Transactional
public class RulePckgServiceImpl extends CommonServiceImpl implements RulePckgServiceI {
	
	/**
     * 根据组织ID查询决策包
     * @author 王传圣
     * @param  String orgId 组织ID
     * @return List<RulePckgEntity> 决策包
     * @throws IfreException 
     */  
	public List<RulePckgEntity> findRulePckgEntityByOrgId(String orgId) throws IfreException {
		
		List<RulePckgEntity> result = null;
		
		try {
			StringBuffer hql = new StringBuffer();
			hql.append(" from RulePckgEntity where orgId = ? ");
			result = this.findHql(hql.toString(),new Object[]{orgId});
		} catch (Exception ex) {
			throw new IfreException(100000,"根据组织ID查询决策包异常",ex); 
		}
		
		return result;
	}
	
}