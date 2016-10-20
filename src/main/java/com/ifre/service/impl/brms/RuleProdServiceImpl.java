package com.ifre.service.impl.brms;

import java.util.List;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ifre.entity.brms.RuleProdEntity;
import com.ifre.exception.IfreException;
import com.ifre.service.brms.RuleProdServiceI;

@Service("ruleProdService")
@Transactional
public class RuleProdServiceImpl extends CommonServiceImpl implements RuleProdServiceI {
	
	/**
     * 根据组织ID查询决策产品
     * @author 王传圣
     * @param  String orgId 组织ID
     * @return List<RuleProdEntity> 决策产品
     * @throws IfreException 
     */  
	public List<RuleProdEntity> findRuleProdEntityByOrgId(String orgId) throws IfreException {
		
		List<RuleProdEntity> result = null;
		
		try {
			StringBuffer hql = new StringBuffer();
			hql.append(" from RuleProdEntity where orgId = ? ");
			result = this.findHql(hql.toString(),new Object[]{orgId});
		} catch (Exception ex) {
			throw new IfreException(100000,"根据组织ID查询决策产品异常",ex); 
		}
		
		return result;
	}

	@Override
	public void updateProductStatus(String prodId, String status) {
		
		String sql = "update brms_rule_prod set status = ? where id = ?";
		executeSql(sql, new Object[]{status,prodId});
	}
	
}
