package com.ifre.service.impl.brms;

import java.util.ArrayList;
import java.util.List;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ifre.entity.brms.KnwldgLibEntity;
import com.ifre.entity.ruleengin.BrmsKnwldgLibEntity;
import com.ifre.exception.IfreException;
import com.ifre.service.brms.KnwldgLibServiceI;

@Service("knwldgLibService")
@Transactional
public class KnwldgLibServiceImpl extends CommonServiceImpl implements KnwldgLibServiceI {
	
	@Override
	public List<BrmsKnwldgLibEntity> findByOrgId(String orjId) throws IfreException {
		List<BrmsKnwldgLibEntity> list = new ArrayList<BrmsKnwldgLibEntity>();
		String hql = "from BrmsKnwldgLibEntity where orgId=? ";
		try {
			list =  this.findHql(hql, orjId);
		} catch (Exception e) {
			throw new IfreException(100000, "根据机构id查询--失败！", e);
		}
		return list;
	}
	
	/**
     * 根据组织ID查询知识库信息
     * @author 王传圣
     * @param  String orgId 组织ID
     * @return List<KnwldgLibEntity> 知识库信息
     * @throws IfreException 
     */  
	public List<KnwldgLibEntity> findKnwldgLibEntityByOrgId(String orgId) throws IfreException {
		
		List<KnwldgLibEntity> result = null;
		
		try {
			StringBuffer hql = new StringBuffer();
			hql.append(" from KnwldgLibEntity where orgId = ? ");
			result = this.findHql(hql.toString(),new Object[]{orgId});
		} catch (Exception ex) {
			throw new IfreException(100000,"根据组织ID查询知识库信息异常",ex); 
		}
		
		return result;
	}
}