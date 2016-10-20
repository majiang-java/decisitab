package com.ifre.service.impl.shared;

import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.ifre.entity.shared.InterfaceLogEntity;
import com.ifre.form.shared.InterfaceLogForm;
import com.ifre.form.shared.SharedReturn;
import com.ifre.service.shared.InterfaceLogServiceI;

@Service("interfaceLogService")
@Transactional
public class InterfaceLogServiceImpl extends CommonServiceImpl implements InterfaceLogServiceI {
	
	/**
     * 新增通讯日志(异步)
     * @author 王传圣 
     * @param  logForm 通讯日志信息
     * @param  sharedReturn 共享服务返回信息
     * @return void
     */  
	@Async
	public void addInterfaceLog(InterfaceLogForm logForm,SharedReturn sharedReturn){
		
		InterfaceLogEntity logEntity = new InterfaceLogEntity();
		
		try {
			//复制对象属性
			BeanUtils.copyProperties(logEntity,logForm);
			//发送数据
			Object sendObj = logForm.getSendObj();
			if(sendObj != null){
				logEntity.setSendMsg(JSONObject.toJSONString(sendObj));
			}
			//处理结果
			String status = sharedReturn.getStatus();
			logEntity.setHandleStatus(status);
			logEntity.setResult(JSONObject.toJSONString(sharedReturn));
			//创建时间
			logEntity.setCreateDate(new Date());
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}finally{
			try{
				save(logEntity);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
}