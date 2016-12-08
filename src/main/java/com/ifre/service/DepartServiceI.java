package com.ifre.service;

import java.util.List;

import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.system.pojo.base.TSDepart;

import com.ifre.exception.IfreException;

public interface DepartServiceI extends CommonService {

	/**
	 * 获取机构列表
	 * 
	 */
	public List<TSDepart> getAllTSDepart() throws IfreException;

}
