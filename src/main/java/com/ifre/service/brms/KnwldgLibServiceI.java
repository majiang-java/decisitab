package com.ifre.service.brms;

import java.util.List;

import org.jeecgframework.core.common.service.CommonService;

import com.ifre.entity.brms.KnwldgLibEntity;
import com.ifre.entity.ruleengin.BrmsKnwldgLibEntity;
import com.ifre.exception.IfreException;
public interface KnwldgLibServiceI extends CommonService{

	public List<BrmsKnwldgLibEntity> findByOrgId(String orjId) throws IfreException;
	
	/**
     * 根据组织ID查询知识库信息
     * @author 王传圣
     * @param  String orgId 组织ID
     * @return List<KnwldgLibEntity> 知识库信息
     * @throws IfreException 
     */  
	public List<KnwldgLibEntity> findKnwldgLibEntityByOrgId(String orgId) throws IfreException;
}
