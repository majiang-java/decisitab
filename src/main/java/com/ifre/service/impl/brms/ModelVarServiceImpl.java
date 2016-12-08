package com.ifre.service.impl.brms;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.BrowserUtils;
import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.web.system.pojo.base.TSDatalogEntity;
import org.jeecgframework.web.system.pojo.base.TSFunction;
import org.jeecgframework.web.system.pojo.base.TSIcon;
import org.jeecgframework.web.system.pojo.base.TSLog;
import org.jeecgframework.web.system.pojo.base.TSRole;
import org.jeecgframework.web.system.pojo.base.TSRoleFunction;
import org.jeecgframework.web.system.pojo.base.TSRoleUser;
import org.jeecgframework.web.system.pojo.base.TSTypegroup;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ifre.entity.brms.VarType;
import com.ifre.entity.brms.VarTypegroup;
import com.ifre.service.brms.ModelVarServiceI;
import com.ifre.util.ResoureceUtilBrms;

@Service("modelVarService")
@Transactional
public class ModelVarServiceImpl extends CommonServiceImpl implements ModelVarServiceI {


	/**
	 * 添加日志
	 */
	public void addLog(String logcontent, Short operatetype, Short loglevel) {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		String broswer = BrowserUtils.checkBrowse(request);
		TSLog log = new TSLog();
		log.setLogcontent(logcontent);
		log.setLoglevel(loglevel);
		log.setOperatetype(operatetype);
		log.setNote(oConvertUtils.getIp());
		log.setBroswer(broswer);
		log.setOperatetime(DateUtils.gettimestamp());
		log.setTSUser(ResoureceUtilBrms.getSessionUserName());
		commonDao.save(log);
	}

	/**
	 * 添加日志
	 */
	@Async
	public void addIfreLog(String logcontent, Short loglevel, Short operatetype,Short businesstype) {
		TSLog log = new TSLog();
		log.setLogcontent(logcontent);
		log.setLoglevel(loglevel);
		log.setOperatetype(operatetype);
		log.setBusinesstype(businesstype);
		log.setNote(oConvertUtils.getIp());
		log.setOperatetime(DateUtils.gettimestamp());
		commonDao.save(log);
	}
	
	/**
	 * 添加日志
	 */
	@Async
	public void addIfreLog(String logcontent, Short loglevel, Short operatetype) {
		TSLog log = new TSLog();
		log.setLogcontent(logcontent);
		log.setLoglevel(loglevel);
		log.setOperatetype(operatetype);
		log.setNote(oConvertUtils.getIp());
		log.setOperatetime(DateUtils.gettimestamp());
		commonDao.save(log);
	}
	
	/**
	 * 根据类型名称获取Type,如果为空则创建一个
	 *
	 * @param typename
	 * @return
	 */
	public VarType getType(String typename, VarTypegroup varTypegroup) {
		List<VarType> ls = commonDao.findHql("from VarType where typename = ? and typegroupid = ?",typename,varTypegroup.getId());
		VarType actType = null;
		if (ls == null || ls.size()==0) {
			actType = new VarType();
			actType.setTypename(typename);
			actType.setVarTypegroup(varTypegroup);
			commonDao.save(actType);
		}else{
			actType = ls.get(0);
		}
		return actType;

	}

	/**
	 * 根据类型分组编码和名称获取VarTypegroup,如果为空则创建一个
	 *
	 * @param typecode
	 * @param typename
	 * @return
	 */
	public VarTypegroup getVarTypegroup(String varTypegroupcode, String typgroupename,String typecode) {
		VarTypegroup varTypegroup = commonDao.findUniqueByProperty(VarTypegroup.class, "varTypegroupcode", varTypegroupcode);
		if (varTypegroup == null) {
			varTypegroup = new VarTypegroup();
			varTypegroup.setVarTypegroupcode(varTypegroupcode);
			varTypegroup.setVarTypegroupname(typgroupename);
			commonDao.save(varTypegroup);
		}
		return varTypegroup;
	}


	public VarTypegroup getVarTypegroupByCode(String varTypegroupcode) {
		VarTypegroup varTypegroup = commonDao.findUniqueByProperty(VarTypegroup.class, "varTypegroupcode", varTypegroupcode);
		return varTypegroup;
	}


	public void initAllVarTypegroups() {
		List<VarTypegroup> typeGroups = this.commonDao.loadAll(VarTypegroup.class);
		for (VarTypegroup varTypegroup : typeGroups) {
			ResoureceUtilBrms.allVarTypegroups.put(varTypegroup.getVarTypegroupcode().toLowerCase(), varTypegroup);
			List<VarType> types = this.commonDao.findByProperty(VarType.class, "VarTypegroup.id", varTypegroup.getId());
			ResoureceUtilBrms.allVarTypes.put(varTypegroup.getVarTypegroupcode().toLowerCase(), types);
		}
	}


	public void refleshTypesCach(VarType type) {
		VarTypegroup varTypegroup = type.getVarTypegroup();
		VarTypegroup typeGroupEntity = this.commonDao.get(VarTypegroup.class, varTypegroup.getId());
		List<VarType> types = this.commonDao.findByProperty(VarType.class, "varTypegroup.id", varTypegroup.getId());
		ResoureceUtilBrms.allVarTypes.put(typeGroupEntity.getVarTypegroupcode().toLowerCase(), types);
	}


	public void refleshVarTypegroupCach() {
		ResoureceUtilBrms.allVarTypegroups.clear();
		List<VarTypegroup> typeGroups = this.commonDao.loadAll(VarTypegroup.class);
		for (VarTypegroup varTypegroup : typeGroups) {
			ResoureceUtilBrms.allVarTypegroups.put(varTypegroup.getVarTypegroupcode().toLowerCase(), varTypegroup);
		}
	}

}
