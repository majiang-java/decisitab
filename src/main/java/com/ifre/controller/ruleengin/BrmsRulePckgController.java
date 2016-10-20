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

import com.ifre.entity.ruleengin.BrmsRulePckgEntity;
import com.ifre.service.ruleengin.BrmsRulePckgServiceI;

/**   
 * @Title: Controller
 * @Description: 规则包
 * @author zhangdaihao
 * @date 2016-05-19 11:22:52
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/brmsRulePckgController")
public class BrmsRulePckgController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(BrmsRulePckgController.class);

	@Autowired
	private BrmsRulePckgServiceI brmsRulePckgService;
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
	 * 规则包列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/ifre/ruleEngin/brmsRulePckgList");
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
	public void datagrid(BrmsRulePckgEntity brmsRulePckg,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(BrmsRulePckgEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, brmsRulePckg, request.getParameterMap());
		this.brmsRulePckgService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除规则包
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(BrmsRulePckgEntity brmsRulePckg, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		brmsRulePckg = systemService.getEntity(BrmsRulePckgEntity.class, brmsRulePckg.getId());
		message = "规则包删除成功";
		brmsRulePckgService.delete(brmsRulePckg);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加规则包
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(BrmsRulePckgEntity brmsRulePckg, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(brmsRulePckg.getId())) {
			message = "规则包更新成功";
			BrmsRulePckgEntity t = brmsRulePckgService.get(BrmsRulePckgEntity.class, brmsRulePckg.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(brmsRulePckg, t);
				brmsRulePckgService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "规则包更新失败";
			}
		} else {
			message = "规则包添加成功";
			brmsRulePckgService.save(brmsRulePckg);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 规则包列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(BrmsRulePckgEntity brmsRulePckg, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(brmsRulePckg.getId())) {
			brmsRulePckg = brmsRulePckgService.getEntity(BrmsRulePckgEntity.class, brmsRulePckg.getId());
			req.setAttribute("brmsRulePckgPage", brmsRulePckg);
		}
		return new ModelAndView("com/ifre/ruleEngin/brmsRulePckg");
	}
}
