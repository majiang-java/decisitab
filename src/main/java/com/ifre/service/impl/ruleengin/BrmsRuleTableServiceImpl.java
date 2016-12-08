package com.ifre.service.impl.ruleengin;

import java.util.List;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ifre.entity.ruleengin.BrmsRuleTableEntity;
import com.ifre.service.ruleengin.BrmsRuleTableServiceI;

@Service("brmsRuleTableService")
@Transactional
public class BrmsRuleTableServiceImpl extends CommonServiceImpl implements BrmsRuleTableServiceI {

	@Override
	public boolean isExist(String departId,String knowId, String prodId) {
		List<BrmsRuleTableEntity> list = findHql("from BrmsRuleTableEntity where  orgId = ? and knowId= ? and prodId = ? ",departId,knowId,prodId);
		if(list == null|| list!=null && list.isEmpty()){
			return true;
		}else{
			return false;
		}
	}
	
}