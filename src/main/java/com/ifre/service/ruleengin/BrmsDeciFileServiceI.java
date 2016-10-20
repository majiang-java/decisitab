package com.ifre.service.ruleengin;

import java.util.List;

import org.jeecgframework.core.common.service.CommonService;

import com.ifre.entity.ruleengin.BrmsDeciFileEntity;

public interface BrmsDeciFileServiceI extends CommonService{
	
	
	public List<BrmsDeciFileEntity> findList(String fileName,String pkgname);

}
