package com.ifre.shared.brmsShared.impl;

import org.springframework.context.ApplicationContext;

import com.ifre.service.shared.BrmsSharedServiceI;
import com.ifre.shared.brmsShared.BrmsSharedI;

public class BrmsSharedImpl implements BrmsSharedI {
	
	/**
     * 共享服务样例
     * @author 王传圣
     * @param jsonData 业务订单信息json格式
     * @return String 返回信息
     */
	public String example(String jsonData) {
		
		String result = "";
		
		try {
			ApplicationContext context = org.springframework.web.context.ContextLoader.getCurrentWebApplicationContext();
			BrmsSharedServiceI brmsSharedService = (BrmsSharedServiceI)context.getBean("brmsSharedService");
			result = brmsSharedService.example(jsonData);			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return result;
	}
	
	/**
     * 共享服务样例
     * @author 王传圣
     * @param jsonData 业务订单信息json格式
     * @return String 返回信息
     */
	public String callModelFire(String jsonData) {
		
		String result = "";
		
		try {
			ApplicationContext context = org.springframework.web.context.ContextLoader.getCurrentWebApplicationContext();
			BrmsSharedServiceI brmsSharedService = (BrmsSharedServiceI)context.getBean("brmsSharedService");
			result = brmsSharedService.callModelFire(jsonData);			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return result;
	}
}