package com.ifre.service.impl.ruleengin;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ifre.service.ruleengin.BrmsConditionDetailServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;

@Service("brmsConditionDetailService")
@Transactional
public class BrmsConditionDetailServiceImpl extends CommonServiceImpl implements BrmsConditionDetailServiceI {
	
}