package com.ifre.service.brms;

import java.util.List;

import org.jeecgframework.core.common.service.CommonService;

import com.ifre.entity.brms.ModelResult;
import com.ifre.entity.brms.ModelResultEntity;
import com.ifre.exception.IfreException;

public interface BiServiceI extends CommonService {

	/**
	 * 获取模型执行指标-未实现
	 * 
	 */
	public List<ModelResultEntity> getApiResultList() throws IfreException;
	
	/**
	 * 获取模型执行指标-按天分组
	 * 
	 */
	public List<ModelResult> getApiResultListByDay(String orgCode) throws IfreException;
	
	/**
	 * 获取模型执行指标-按月分组
	 * 
	 */
	public List<ModelResult> getApiResultListByMonth(String orgCode) throws IfreException;
	
	/**
	 * 获取模型执行指标-按月分组
	 * 
	 */
	public List<ModelResult> getApiResultListByMonthAndLevel(String orgCode) throws IfreException;
	
	/**
	 * 获取模型执行指标-近三月-按月分组
	 * 
	 */
	public List<ModelResult> getApiResultListBy3MonthAndLevel(String orgCode) throws IfreException;
	
	/**
	 * 获取模型执行指标-按周分组
	 * 
	 */
	public List<ModelResult> getApiResultListByWeek(String orgCode) throws IfreException;
	
}
