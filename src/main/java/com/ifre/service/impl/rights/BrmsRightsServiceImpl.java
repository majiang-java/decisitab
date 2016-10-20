package com.ifre.service.impl.rights;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.web.system.pojo.base.TSOperation;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ifre.service.exception.IfreExceptionServiceI;
import com.ifre.service.rights.BrmsRightsServiceI;


@Service("brmsRightsService")
public class BrmsRightsServiceImpl implements BrmsRightsServiceI {
	
	@Autowired
	private IfreExceptionServiceI ifreExceptionService;
	
	@Autowired
	private SystemService systemService;
	
	/**
     * 页面标签权限
     * @author 王传圣
     * @param labelName 标签名称
     * @param request HttpServletRequest
     * @return Boolean 返回信息(true:有权限;false:无权限)
     */
	public Boolean labelRights(String labelName,HttpServletRequest request) {
		
		//返回信息	
		Boolean result = true;

		try {
			if(!ResourceUtil.getSessionUserName().getUserName().equals("admin") && Globals.BUTTON_AUTHORITY_CHECK){
				Set<String> operationCodes = (Set<String>) request.getAttribute(Globals.OPERATIONCODES);
				if (null != operationCodes) {
					for (String MyoperationCode : operationCodes) {
						if (oConvertUtils.isEmpty(MyoperationCode))
							break;
						TSOperation operation = systemService.getEntity(TSOperation.class, MyoperationCode);
						if(null != operation){
							String operationcode = operation.getOperationcode();
							if (operationcode.equals(labelName)){
								result = false;
								break;
							}
						}
					}
				}	
			}
		}catch(Exception ex){
			ex.printStackTrace();
			ifreExceptionService.resolveException(ex,Globals.Log_Leavel_exception); 
		}
		
		return result;
	}
}