package com.ifre.service.shared;

import org.jeecgframework.core.common.service.CommonService;

import com.ifre.form.shared.InterfaceLogForm;
import com.ifre.form.shared.SharedReturn;

public interface InterfaceLogServiceI extends CommonService{

	/**
     * 新增通讯日志(异步)
     * @author 王传圣 
     * @param  logForm 通讯日志信息
     * @param  sharedReturn 共享服务返回信息
     * @return void
     */  
	public void addInterfaceLog(InterfaceLogForm logForm,SharedReturn sharedReturn);
}
