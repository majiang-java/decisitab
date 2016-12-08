package com.ifre.service.brms;

import java.util.List;

import org.jeecgframework.core.common.service.CommonService;

import com.ifre.entity.brms.RuleProdEntity;
import com.ifre.exception.IfreException;
import com.ifre.form.api.Model;

public interface RuleProdServiceI extends CommonService{
	
	/**
	 * 获取产品列表信息-常规机构过滤
	 * 
	 * @param prodId
	 * @return
	 * @throws IfreException
	 */
	public List<RuleProdEntity> findRuleProdList(String orgCode) throws IfreException;
	
	/**
	 * 获取产品列表信息-强制机构过滤（admin也只能看自己机构）（舍弃20161207）
	 * 
	 * @param prodId
	 * @return
	 * @throws IfreException
	 */
	public List<RuleProdEntity> findRuleProdList(String orgCode,boolean isDuressFilter) throws IfreException;
	
	/**
	 * 根据prodId获取对应产品的决策表信息-变量集合、截断、结果枚举
	 * 
	 * @param prodId
	 * @return
	 * @throws IfreException
	 */
	public Model getModelByProdId(String prodId) throws IfreException;
	
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
