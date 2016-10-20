package com.ifre.service.impl.ruleengin;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ifre.service.ruleengin.BrmsObjPropServiceI;

@Service("brmsObjPropService")
@Transactional
public class BrmsObjPropServiceImpl extends CommonServiceImpl implements BrmsObjPropServiceI {
	
}