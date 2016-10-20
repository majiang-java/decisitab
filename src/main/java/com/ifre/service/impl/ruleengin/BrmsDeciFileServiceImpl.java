package com.ifre.service.impl.ruleengin;

import java.util.List;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ifre.entity.ruleengin.BrmsDeciFileEntity;
import com.ifre.service.ruleengin.BrmsDeciFileServiceI;

@Service("brmsDeciFileService")
@Transactional
public class BrmsDeciFileServiceImpl extends CommonServiceImpl implements BrmsDeciFileServiceI {

	@Override
	public List<BrmsDeciFileEntity> findList(String fileName,String pkgname) {
		String hql = "from BrmsDeciFileEntity where fileName =? and pkgName = ?";
		return findHql(hql, new Object[]{fileName, pkgname});
	}

}