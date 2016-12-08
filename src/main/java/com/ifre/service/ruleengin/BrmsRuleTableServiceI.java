package com.ifre.service.ruleengin;

import org.jeecgframework.core.common.service.CommonService;

public interface BrmsRuleTableServiceI extends CommonService{

	boolean isExist(String departId,String knowId, String prodId);

}
