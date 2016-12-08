package com.ifre.controller.shared;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ifre.entity.shared.BusiSysInfoEntity;
import com.ifre.exception.IfreException;
import com.ifre.service.DepartServiceI;
import com.ifre.service.shared.BusiSysInfoServiceI;

/**   
 * @Title: Controller
 * @Description: 渠道来源信息
 * @author zhangdaihao
 * @date 2016-04-14 17:49:50
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/busiSysInfoController")
public class BusiSysInfoController extends BaseController {
	/**
	 * Logger for this class
	 */
//	private static final Logger logger = Logger.getLogger(BusiSysInfoController.class);

	@Autowired
	private BusiSysInfoServiceI busiSysInfoService;
	@Autowired
	private DepartServiceI departService;
	
	@Autowired
	private SystemService systemService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 渠道来源信息列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/ifre/shared/busiSysInfoList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(BusiSysInfoEntity busiSysInfo,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(BusiSysInfoEntity.class, dataGrid);
		//机构过滤-后台实现
		TSUser tSUser = ResourceUtil.getSessionUserName();
		if(!"A01".equals(tSUser.getCurrentDepart().getOrgCode())){
			cq.eq("orgCode", tSUser.getCurrentDepart().getOrgCode());
		} 
		cq.add();
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, busiSysInfo);
		this.busiSysInfoService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除渠道来源信息
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(BusiSysInfoEntity busiSysInfo, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		busiSysInfo = systemService.getEntity(BusiSysInfoEntity.class, busiSysInfo.getId());
		message = "渠道来源信息删除成功";
		busiSysInfoService.delete(busiSysInfo);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加渠道来源信息
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(BusiSysInfoEntity busiSysInfo, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(busiSysInfo.getId())) {
			message = "渠道来源信息更新成功";
			BusiSysInfoEntity t = busiSysInfoService.get(BusiSysInfoEntity.class, busiSysInfo.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(busiSysInfo, t);
				t.setUpdateBy("system");
				t.setUpdateDate(new Date());
				busiSysInfoService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "渠道来源信息更新失败";
			}
		} else{
			TSUser tSUser = ResourceUtil.getSessionUserName();
			if(!"A01".equals(tSUser.getCurrentDepart().getOrgCode()) && !tSUser.getCurrentDepart().getOrgCode().equals(busiSysInfo.getOrgCode())){
				message = "渠道来源信息添加失败，机构选择不合法";
				systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			}else{
				message = "渠道来源信息添加成功";
				busiSysInfo.setStatus(0);
				busiSysInfo.setSorts(0);
				busiSysInfo.setCreateBy("system");
				busiSysInfo.setCreateDate(new Date());
				busiSysInfo.setUpdateBy("system");
				busiSysInfo.setUpdateDate(new Date());
				busiSysInfoService.save(busiSysInfo);
				systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			} 
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 渠道来源信息列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(BusiSysInfoEntity busiSysInfo, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(busiSysInfo.getId())) {
			//此处默认一条规则，编辑渠道信息时，绑定的机构不可以修改
			busiSysInfo = busiSysInfoService.getEntity(BusiSysInfoEntity.class, busiSysInfo.getId());
			req.setAttribute("busiSysInfoPage", busiSysInfo);
		}else{
			TSUser tSUser = ResourceUtil.getSessionUserName();
			req.setAttribute("orgCode", tSUser.getCurrentDepart().getOrgCode());
			req.setAttribute("departname", tSUser.getCurrentDepart().getDepartname());
			if("A01".equals(tSUser.getCurrentDepart().getOrgCode())){
				try {
					List<TSDepart> list = departService.getAllTSDepart();
					req.setAttribute("departList", list);
				} catch (IfreException e) {
					systemService.addLog(e.getMessage(), Globals.Log_Type_OTHER, Globals.Log_Leavel_ERROR);
				}
			}
		}
		return new ModelAndView("com/ifre/shared/busiSysInfo");
	}
}
