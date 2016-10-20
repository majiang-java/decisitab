package com.ifre.service.impl.ruleengin;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ifre.service.ruleengin.BrmsRuleTableServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;

@Service("brmsRuleTableService")
@Transactional
public class BrmsRuleTableServiceImpl extends CommonServiceImpl implements BrmsRuleTableServiceI {
	
}