package com.ifre.controller.ruleengin;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ifre.entity.ruleengin.BrmsRuleProdEntity;
import com.ifre.service.ruleengin.BrmsRuleProdServiceI;

/**   
 * @Title: Controller
 * @Description: 规则属性
 * @author zhangdaihao
 * @date 2016-05-19 11:23:22
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/brmsRuleProdController")
public class BrmsRuleProdController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(BrmsRuleProdController.class);

	@Autowired
	private BrmsRuleProdServiceI brmsRuleProdService;
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
	 * 规则属性列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/ifre/ruleEngin/brmsRuleProdList");
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
	public void datagrid(BrmsRuleProdEntity brmsRuleProd,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(BrmsRuleProdEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, brmsRuleProd, request.getParameterMap());
		this.brmsRuleProdService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除规则属性
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(BrmsRuleProdEntity brmsRuleProd, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		brmsRuleProd = systemService.getEntity(BrmsRuleProdEntity.class, brmsRuleProd.getId());
		message = "规则属性删除成功";
		brmsRuleProdService.delete(brmsRuleProd);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加规则属性
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(BrmsRuleProdEntity brmsRuleProd, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(brmsRuleProd.getId())) {
			message = "规则属性更新成功";
			BrmsRuleProdEntity t = brmsRuleProdService.get(BrmsRuleProdEntity.class, brmsRuleProd.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(brmsRuleProd, t);
				brmsRuleProdService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "规则属性更新失败";
			}
		} else {
			message = "规则属性添加成功";
			brmsRuleProdService.save(brmsRuleProd);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 规则属性列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(BrmsRuleProdEntity brmsRuleProd, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(brmsRuleProd.getId())) {
			brmsRuleProd = brmsRuleProdService.getEntity(BrmsRuleProdEntity.class, brmsRuleProd.getId());
			req.setAttribute("brmsRuleProdPage", brmsRuleProd);
		}
		return new ModelAndView("com/ifre/ruleEngin/brmsRuleProd");
	}
}
