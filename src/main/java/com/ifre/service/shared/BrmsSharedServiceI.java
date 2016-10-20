package com.ifre.service.shared;



public interface BrmsSharedServiceI{
	
	/**
     * 共享服务样例
     * @author 王传圣
     * @param jsonData 业务订单信息json格式
     * @return String 返回信息
     */
	public String example(String jsonData);
	
	/**
     * 决策引擎接口
     * @author 王传圣
     * @param jsonData json信息
     * @return String 返回信息
     */
	public String callModelFire(String jsonData);
}