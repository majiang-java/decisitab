package com.ifre.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import com.ifre.service.DepartServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.web.system.pojo.base.TSDepart;

import com.ifre.exception.IfreException;

@Service("departService")
@Transactional
public class DepartServiceImpl extends CommonServiceImpl implements DepartServiceI {

	@Override
	public List<TSDepart> getAllTSDepart() throws IfreException {
		String hql = "from TSDepart where orgType=1 and status=1 and startDate  < NOW() and endDate  > NOW()";
		// 使用findHql()，查询异常，原因未知
		// List<TSDepart> list = this.findHql(hql,null);
		List<TSDepart> list = this.findByQueryString(hql);
		if (list != null) {
			return list;
		} else {
			throw new IfreException("机构查询失败");
		}
	}

}