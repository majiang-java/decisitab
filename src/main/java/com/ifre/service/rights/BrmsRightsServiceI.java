package com.ifre.service.rights;

import javax.servlet.http.HttpServletRequest;



public interface BrmsRightsServiceI{
	
	/**
     * 页面标签权限
     * @author 王传圣
     * @param labelName 标签名称
     * @param request HttpServletRequest
     * @return Boolean 返回信息(true:有权限;false:无权限)
     */
	public Boolean labelRights(String labelName,HttpServletRequest request);
	
}