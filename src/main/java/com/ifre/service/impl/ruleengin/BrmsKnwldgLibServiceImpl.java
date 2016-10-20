package com.ifre.service.impl.ruleengin;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ifre.service.ruleengin.BrmsKnwldgLibServiceI;

@Service("brmsKnwldgLibService")
@Transactional
public class BrmsKnwldgLibServiceImpl extends CommonServiceImpl implements BrmsKnwldgLibServiceI {
	
}