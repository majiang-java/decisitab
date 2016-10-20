package com.ifre.service.brms;

import java.util.Set;

import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.system.pojo.base.TSFunction;
import org.jeecgframework.web.system.pojo.base.TSIcon;
import org.jeecgframework.web.system.pojo.base.TSUser;

import com.ifre.entity.brms.VarType;
import com.ifre.entity.brms.VarTypegroup;

/**
 *
 * @author  caojianmiao
 *
 */
public interface ModelVarServiceI extends CommonService{

	/**
	 * 日志添加
	 * @param LogContent 内容
	 * @param loglevel 级别
	 * @param operatetype 类型
	 * @param TUser 操作人
	 */
	public void addLog(String LogContent,Short operatetype, Short loglevel);
	
	/**
	 * 日志添加
	 * @param LogContent 内容
	 * @param loglevel 级别
	 * @param operatetype 操作类型
	 * @param businesstype 业务类型
	 */
	public void addIfreLog(String LogContent, Short loglevel,Short operatetype,Short businesstype);
	
	/**
	 * 日志添加
	 * @param LogContent 内容
	 * @param loglevel 级别
	 * @param operatetype 操作类型
	 */
	public void addIfreLog(String LogContent, Short loglevel,Short operatetype);
	

	/**
	 * 刷新字典分组缓存
	 */
	public void refleshVarTypegroupCach();

	/**
	 * @param type
	 */
	public void refleshTypesCach(VarType type);


}
